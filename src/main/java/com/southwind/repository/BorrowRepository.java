package com.southwind.repository;

import com.southwind.entity.Borrow;

import java.util.List;

public interface BorrowRepository {
    void addBorrow(Integer bookID, Integer readerID, String borrowTime, String returnTime, Integer adminID, Integer state);
    List<Borrow> findAllByReaderID(Integer readerID, int index, int limit);
    int count(Integer readerID);
    List<Borrow> findAllByState(Integer state, int index, int limit);
    int countByState(Integer state);
    void handleBorrowState(Integer borrowID, Integer state, Integer adminID);
}
