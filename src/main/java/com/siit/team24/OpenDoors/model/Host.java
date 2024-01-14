package com.siit.team24.OpenDoors.model;

import jakarta.persistence.Entity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
@Entity
public class Host extends User {
    public Host(){

    }

}
