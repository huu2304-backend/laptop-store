package com.laptoppstore.entity;

import com.laptoppstore.enums.Role;
import com.laptoppstore.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldSaveUserSuccessfully() {
        User user = new User();
        user.setFullName("Nguyen Van A");
        user.setEmail("test@gmail.com");
        user.setPassword("hashedPassword123");
        user.setRole(Role.ROLE_CUSTOMER);
        user.setAddress("Ha Noi");  // thêm dòng này

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("test@gmail.com", saved.getEmail());
        assertTrue(saved.isEnable());          // mặc định true
        assertEquals(Role.ROLE_CUSTOMER, saved.getRole()); // mặc định ROLE_CUSTOMER
    }

    @Test
    void shouldFailWhenEmailIsNull() {
        User user = new User();
        user.setFullName("Nguyen Van A");
        user.setPassword("hashedPassword123");
        user.setRole(Role.ROLE_CUSTOMER);
        user.setAddress("Ha Noi");  // thêm dòng này

        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    @Test
    void shouldFailWhenEmailIsDuplicate() {
        User user1 = new User();
        user1.setFullName("Nguyen Van A");
        user1.setEmail("test@gmail.com");
        user1.setPassword("hashedPassword123");
        user1.setRole(Role.ROLE_CUSTOMER);
        user1.setAddress("Ha Noi");  // thêm dòng này

        userRepository.save(user1);

        User user2 = new User();
        user2.setFullName("Nguyen Van B");
        user2.setEmail("test@gmail.com"); // email trùng
        user2.setPassword("hashedPassword456");
        user2.setRole(Role.ROLE_CUSTOMER);
        user2.setAddress("Ha Noi");  // thêm dòng này
        assertThrows(Exception.class, () -> userRepository.save(user2));
    }

    @Test
    void shouldDefaultRoleBeCustomer() {
        User user = new User();
        user.setFullName("Nguyen Van A");
        user.setEmail("test@gmail.com");
        user.setPassword("hashedPassword123");
        user.setAddress("Ha Noi");  // thêm dòng này

        User saved = userRepository.save(user);

        assertEquals(Role.ROLE_CUSTOMER, saved.getRole());
    }
}