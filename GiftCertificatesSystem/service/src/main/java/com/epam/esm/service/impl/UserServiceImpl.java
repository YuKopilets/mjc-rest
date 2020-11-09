package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.LoginIsNotValidServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public List<Order> getUserOrders(String login) {
        if (loginIsValid(login)) {
            return userDao.findOrdersByLogin(login);
        } else {
            throw new LoginIsNotValidServiceException(login + " is not valid. Required login size not less 4 chars " +
                    "and not more then 50");
        }
    }

    private boolean loginIsValid(String login) {
        int length = login.length();
        return length >= 4 && length <= 50;
    }
}
