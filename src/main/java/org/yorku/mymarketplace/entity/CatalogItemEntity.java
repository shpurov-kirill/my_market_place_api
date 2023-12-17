package org.yorku.mymarketplace.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CatalogItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String model;
    String category;
    String brand;
    Long quantity;
    Long price;
    String text;
    @Lob
    @Column(length = 1024 * 1024)
    String image;

}
