package org.yorku.mymarketplace.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yorku.mymarketplace.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);

    @Query("FROM UserEntity where name = ?1")
    List<UserEntity> checkNames(String name);
}
