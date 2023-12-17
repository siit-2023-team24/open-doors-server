package com.siit.team24.OpenDoors.repository.user;

import com.siit.team24.OpenDoors.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
