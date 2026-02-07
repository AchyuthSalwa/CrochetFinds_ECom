package com.ecommerceapp.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public User registerUser(String email, String password, String firstName, String lastName) {
        if (emailExists(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // In production, this should be hashed
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsAdmin(false);
        
        return userRepository.save(user);
    }

    public Optional<User> authenticate(String email, String password) {
        Optional<User> user = findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }

    @Transactional
    public User makeAdmin(String email) {
        User user = findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
        user.setIsAdmin(true);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }
}
