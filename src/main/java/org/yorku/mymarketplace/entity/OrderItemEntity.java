package org.yorku.mymarketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    CatalogItemEntity item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    Long orderId;
    LocalDateTime created;
}
