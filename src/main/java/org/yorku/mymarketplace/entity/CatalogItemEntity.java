package org.yorku.mymarketplace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class CatalogItemEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String title;
    String subTitle;
    String image;
    Long price;
    String text;
}
