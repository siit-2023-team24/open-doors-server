package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.NotificationType;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import jakarta.persistence.Entity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.List;

@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
@Entity
public class Host extends User {
    public Host(){

    }

    public Host(Long id, String email, String password, Timestamp lastPasswordResetDate, UserRole role, String firstName, String lastName, String phone, @Nullable Image image, Address address, boolean enabled, List<NotificationType> disabledTypes) {
        super(id, email, password, lastPasswordResetDate, role, firstName, lastName, phone, image, address, enabled, disabledTypes);
    }

}
