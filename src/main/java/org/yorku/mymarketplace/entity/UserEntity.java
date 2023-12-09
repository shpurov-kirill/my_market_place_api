package org.yorku.mymarketplace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String name;
    String address;
    String password;
    String role;
}
