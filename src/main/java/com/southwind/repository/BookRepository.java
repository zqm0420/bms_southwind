package com.southwind.repository;

import com.southwind.entity.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll(int index, int limit);
    int count();
}
