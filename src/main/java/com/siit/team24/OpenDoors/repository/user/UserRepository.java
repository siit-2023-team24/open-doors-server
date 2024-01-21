package com.siit.team24.OpenDoors.repository.user;

import com.siit.team24.OpenDoors.dto.userManagement.UserSummaryDTO;
import com.siit.team24.OpenDoors.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select u.username from User u where u.id in ?1")
    List<String> findUsernamesByIds(List<Long> ids);

    @Query("select new com.siit.team24.OpenDoors.dto.userManagement.UserSummaryDTO(u) from User u where u.blocked = true and u.deleted=false")
    List<UserSummaryDTO> getBlockedDTOs();
}
