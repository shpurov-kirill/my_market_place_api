package org.yorku.mymarketplace.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yorku.mymarketplace.entity.CatalogItemEntity;

@Repository
public interface CatalogRepository extends CrudRepository<CatalogItemEntity, Long> {
}
