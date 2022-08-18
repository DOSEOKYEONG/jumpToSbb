package com.ll.exam.sbb.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {

        }
        return user;
    }

    public SiteUser findByUsername(String username) {
        SiteUser siteUser = userRepository.findByUsername(username);

        if (siteUser == null) {
            return null;
        }

        return siteUser;
    }

    public SiteUser findByEmail(String email) {
        SiteUser siteUser = userRepository.findByEmail(email);

        if (siteUser == null) {
            return null;
        }

        return siteUser;
    }
}
