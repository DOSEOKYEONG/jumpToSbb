package com.ll.exam.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

    SiteUser findByEmail(String email);

    SiteUser findByUsername(String username);

    SiteUser findByUsernameAAndEmail(String username, String email);
}
