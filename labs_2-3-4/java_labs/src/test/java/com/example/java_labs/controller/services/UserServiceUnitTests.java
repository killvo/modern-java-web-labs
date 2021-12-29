package com.example.java_labs.controller.services;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    private final static String EXAMPLE_USERNAME = "example_username";
    private final static String EXAMPLE_PASSWORD = "123456";
    private final static UUID EXAMPLE_USER_ID = UUID.fromString("24541111-2222-33333-4444-55555555");
    private final static UUID EXAMPLE_USER_ROLE_ID = UUID.fromString("11111111-2222-33333-4444-55555555");
    private final static UUID EXAMPLE_ADMIN_ROLE_ID = UUID.fromString("22222222-2222-33333-4444-55555555");


    @InjectMocks
    private UserService userService;

    @Mock
    private RoleDAO roleDAO;
    @Mock
    private UserDAO userDAO;

    @Captor
    private ArgumentCaptor<User> userCaptor;


    @BeforeEach
    private void beforeEach() {
        roleDAO = mock(RoleDaoImpl.class);
        userDAO = mock(UserDaoImpl.class);
    }

    /**
     * User factory
     */

    private User getExampleUser(Role role) {
        User user = new User();
        user.setId(EXAMPLE_USER_ID);
        user.setUsername(EXAMPLE_USERNAME);
        return user;
    }

    /**
     * Role factory
     */

    private Role getExampleUserRole() {
        Role role = new Role();
        role.setId(EXAMPLE_USER_ROLE_ID);
        role.setName("USER");
        return role;
    }

    private Role getExampleAdminRole() {
        Role role = new Role();
        role.setId(EXAMPLE_ADMIN_ROLE_ID);
        role.setName("ADMIN");
        return role;
    }


    @Test
    void createUserAccount_whenValidData_shouldSucceed() {
        Role role = getExampleUserRole();
        User user = getExampleUser(role);
        when(userDAO.findByUsername(any())).thenReturn(null);
        when(roleDAO.findByName(any())).thenReturn(role);
        when(userDAO.create(any())).thenReturn(user);

        userService.createUserAccount(EXAMPLE_USERNAME, EXAMPLE_PASSWORD);

        verify(userDAO).create(userCaptor.capture());
        User createdUser = userCaptor.getValue();
        assertEquals(EXAMPLE_USER_ID, createdUser.getId());
        assertEquals(EXAMPLE_USERNAME, createdUser.getUsername());
        assertEquals(EXAMPLE_USER_ROLE_ID, createdUser.getRole().getId());
    }
}
