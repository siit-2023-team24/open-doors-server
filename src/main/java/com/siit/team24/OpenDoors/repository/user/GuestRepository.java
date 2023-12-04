package com.siit.team24.OpenDoors.repository.user;

import com.siit.team24.OpenDoors.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
