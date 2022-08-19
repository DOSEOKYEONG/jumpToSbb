package com.ll.exam.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

    Optional<SiteUser> findByEmail(String email);

    Optional<SiteUser> findByUsername(String username);
}
