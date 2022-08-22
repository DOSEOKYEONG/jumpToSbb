package com.ll.exam.sbb.user;

import com.ll.exam.sbb.DataNotFoundException;
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
        Optional<SiteUser> siteUser = userRepository.findByUsername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("해당 유저가 존재하지않습니다.");
        }
    }

    public SiteUser findByEmail(String email) {
        Optional<SiteUser> siteUser = userRepository.findByEmail(email);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            return null;
        }
    }
}
