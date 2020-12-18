package com.southwind.service.impl;

import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.repository.BookRepository;
import com.southwind.repository.BorrowRepository;
import com.southwind.repository.impl.BookRepositoryImpl;
import com.southwind.repository.impl.BorrowRepositoryImpl;
import com.southwind.service.BookService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {
    BookRepository bookRepository = new BookRepositoryImpl();
    BorrowRepository borrowRepository = new BorrowRepositoryImpl();
    private final static int LIMIT = 5;

    /**
     * 查询某页的图书数据
     * @param page
     * @return
     */

    @Override
    public List<Book> findAll(int page) {
        int index =  (page - 1) * LIMIT;

        return bookRepository.findAll(index, LIMIT);
    }

    /**
     * 获取图书页面的页数：
     * 如果图书条目数能整除每页条目数，那么页数等于图书条目数整除每页条目数
     * 否则页数等于图书条目数整除每页条目数+1
     * @return
     */
    @Override
    public int getPages() {
        int count = bookRepository.count();
        if(count / LIMIT == 0){
            return count / LIMIT;
        }else{
            return count / LIMIT + 1;
        }
    }

    @Override
    public int getBorrowPages(Integer readerID){
        int count = borrowRepository.count(readerID);
        if(count / LIMIT == 0){
            return count / LIMIT;
        }else{
            return count / LIMIT + 1;
        }
    }

    @Override
    public List<Borrow> findAllBorrowByState(Integer state, Integer page) {
        int index =  (page - 1) * LIMIT;

        return borrowRepository.findAllByState(state, index, LIMIT);
    }

    @Override
    public int getBorrowPagesByState(Integer state) {
        int count = borrowRepository.countByState(state);
        if(count / LIMIT == 0){
            return count / LIMIT;
        }else{
            return count / LIMIT + 1;
        }
    }

    @Override
    public void handleBorrowState(Integer borrowID, Integer state, Integer adminID) {
        borrowRepository.handleBorrowState(borrowID, state, adminID);
    }


    @Override
    public void addBorrow(Integer bookID, Integer readerID) {
        //借阅日期
        Date borrowTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String borrowTimeStr = simpleDateFormat.format(borrowTime);
        //应该还书的日期 = 借阅日期 + 15天
        Calendar calendar = Calendar.getInstance();
        int dayNumbers = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, dayNumbers + 15);
        Date returnTime = calendar.getTime();
        String returnTimeStr = simpleDateFormat.format(returnTime);

        borrowRepository.addBorrow(bookID, readerID, borrowTimeStr, returnTimeStr, null, 1);
    }

    @Override
    public List<Borrow> findAllByReaderID(Integer readerID, Integer page) {
        int index =  (page - 1) * LIMIT;
        return borrowRepository.findAllByReaderID(readerID, index, LIMIT);
    }
}
