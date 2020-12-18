package com.southwind.service;

import com.southwind.entity.Book;
import com.southwind.entity.Borrow;

import java.util.List;

public interface BookService {
    List<Book> findAll(int page);
    int getPages();
    void addBorrow(Integer bookID, Integer readerID);
    List<Borrow> findAllByReaderID(Integer readerID, Integer page);
    int getBorrowPages(Integer readerID);
    List<Borrow> findAllBorrowByState(Integer state, Integer page);
    int getBorrowPagesByState(Integer state);
    void handleBorrowState(Integer borrowID, Integer state, Integer adminID);

}
