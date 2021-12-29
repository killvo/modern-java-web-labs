package com.example.java_labs.controller.util;

import com.example.java_labs.controller.exception.NotAllowedException;
import com.example.java_labs.controller.exception.UserNotFoundException;
import com.example.java_labs.domain.constants.RoleEnum;
import com.example.java_labs.domain.User;

public class ValidationUtils {

    public static void verifyUserNotNull(User user) {
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

    public static boolean areUserHasRole(User user, RoleEnum role) {
        String roleName = user.getRole().getName();
        return roleName.equals(role.toString());
    }

    public static void verifyUserHasRole(User user, RoleEnum role) {
        if (!areUserHasRole(user, role)) {
            throw new NotAllowedException();
        }
    }
}
