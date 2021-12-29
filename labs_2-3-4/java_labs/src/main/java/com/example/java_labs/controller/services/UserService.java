package com.example.java_labs.controller.services;

import com.example.java_labs.controller.exception.IncorrectPasswordException;
import com.example.java_labs.controller.exception.UserAlreadyExistsException;
import com.example.java_labs.controller.exception.UserNotFoundException;
import com.example.java_labs.controller.util.EncryptionUtils;
import com.example.java_labs.domain.Role;
import com.example.java_labs.domain.User;
import com.example.java_labs.domain.constants.RoleEnum;
import com.example.java_labs.model.dao.RoleDAO;
import com.example.java_labs.model.dao.UserDAO;
import com.example.java_labs.model.dao.factory.DaoFactory;
import com.example.java_labs.model.dao.factory.enums.DaoEnum;
import com.example.java_labs.model.dao.factory.impl.DaoFactoryImpl;
import com.example.java_labs.model.dao.impl.RoleDaoImpl;
import com.example.java_labs.model.dao.impl.UserDaoImpl;

import java.util.UUID;

import static com.example.java_labs.controller.util.ValidationUtils.verifyUserHasRole;
import static com.example.java_labs.controller.util.ValidationUtils.verifyUserNotNull;

public class UserService {
    private static UserService userService;
    private final RoleDAO roleDao;
    private final UserDAO userDao;

    private UserService() {
        DaoFactory daoFactory = DaoFactoryImpl.getInstance();
        this.roleDao = (RoleDaoImpl) daoFactory.getDaoByName(DaoEnum.ROLE_DAO);
        this.userDao = (UserDaoImpl) daoFactory.getDaoByName(DaoEnum.USER_DAO);
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public User createUserAccount(String username, String password) {
        User user = userDao.findByUsername(username);
        throwIfExists(user);

        String salt = EncryptionUtils.generateSalt();
        String hash = EncryptionUtils.hashFromString(password, salt);

        Role role = roleDao.findByName(RoleEnum.USER.toString());
        User userToSave = new User();
        userToSave.setUsername(username);
        userToSave.setSalt(salt);
        userToSave.setHash(hash);
        userToSave.setRole(role);

        return userDao.create(userToSave);
    }

    private void throwIfExists(User user) {
        if (user != null) {
            throw new UserAlreadyExistsException();
        }
    }

    public User authorizeUser(String username, String password) {
        User user = userDao.findByUsername(username);
        throwIfNotFound(user);

        verifyPassword(user, password);

        return user;
    }

    private void throwIfNotFound(User user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

    private void verifyPassword(User user, String requestedPassword) {
        String saltFromDb = user.getSalt();
        String hashFromDb = user.getHash();
        String generatedHash = EncryptionUtils.hashFromString(saltFromDb, requestedPassword);
        if (!hashFromDb.equals(generatedHash)) {
            throw new IncorrectPasswordException();
        }
    }

    public void verifyUserRole(UUID userId, RoleEnum roleName) {
        User user = userDao.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, roleName);
    }
}
