package com.bekzodkeldiyarov.collectionstore.service;

import com.bekzodkeldiyarov.collectionstore.commands.UserCommand;
import com.bekzodkeldiyarov.collectionstore.converters.UserCommandToUser;
import com.bekzodkeldiyarov.collectionstore.converters.UserToUserCommand;
import com.bekzodkeldiyarov.collectionstore.model.User;
import com.bekzodkeldiyarov.collectionstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
class UserServiceImplTest {
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserCommandToUser userCommandToUser;
    @Mock
    UserToUserCommand userToUserCommand;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserSession userSession;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, userCommandToUser, userToUserCommand, passwordEncoder, userSession);
    }

    @Test
    void findById() throws Exception {
        User user = new User();
        user.setId(1L);
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById(anyLong())).thenReturn(userOptional);
        User userReturned = userService.findById(1L);
        assertNotNull(userReturned);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, never()).findAll();
    }

    @Test
    void findUserCommandById() {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(1L);

        Optional optionalUserCommand = Optional.of(userCommand);
        when(userRepository.findById(anyLong())).thenReturn(optionalUserCommand);

//        UserCommand returnedUserCommand = userService.findById(anyLong())
    }

    @Test
    void findByUsername() {
    }

    @Test
    void save() {
    }

    @Test
    void registerUserCommand() {
    }

    @Test
    void findAll() {
    }

    @Test
    void saveUserCommand() {
    }
}