package org.yorku.mymarketplace.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yorku.mymarketplace.entity.OrderItemEntity;

import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findByUserId(Long valueOf);
}
