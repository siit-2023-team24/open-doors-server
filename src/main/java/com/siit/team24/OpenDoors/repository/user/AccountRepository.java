package com.siit.team24.OpenDoors.repository.user;

import com.siit.team24.OpenDoors.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
