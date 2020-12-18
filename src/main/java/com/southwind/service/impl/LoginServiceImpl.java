package com.southwind.service.impl;

import com.southwind.repository.AdminRepository;
import com.southwind.repository.ReaderRepository;
import com.southwind.repository.impl.AdminRepositoryImpl;
import com.southwind.repository.impl.ReaderRepositoryImpl;
import com.southwind.service.LoginService;

public class LoginServiceImpl implements LoginService {

    private AdminRepository adminRepository = new AdminRepositoryImpl();
    private ReaderRepository readerRepository = new ReaderRepositoryImpl();

    public Object login(String username, String password, String type) {
        Object object = null;
        switch (type){
            case "admin":
                object = adminRepository.login(username, password);
                break;
            case "reader":
                object = readerRepository.login(username, password);
                break;
        }
        return object;
    }
}
