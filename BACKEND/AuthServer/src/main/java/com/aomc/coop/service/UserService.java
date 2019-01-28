package com.aomc.coop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aomc.coop.dao.UserDao;
import com.aomc.coop.model.User;

@Service // 비지니스 메소드를 별도의 Service 객체에서 구현하도록 하고 Controller는 Service 객체를 사용하도록 합니다.
         // 그리고 이제 Service 레이어가 Repository 레이어에 접근
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getUser(String userId) {
        return userDao.getUser(userId);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public boolean insertUser(User user) { return userDao.insertUser(user); }

    public boolean deleteUser(String userId) { return userDao.deleteUser(userId); }

    public boolean updateAccess_date(User user) { return userDao.updateAccess_date(user); }
// *** parameter를 String userId로 통일하자
}