package org.yorku.mymarketplace;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.yorku.mymarketplace.entity.CatalogItemEntity;
import org.yorku.mymarketplace.entity.UserEntity;
import org.yorku.mymarketplace.repository.CatalogRepository;
import org.yorku.mymarketplace.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController()
public class MyMarketPlaceController {

    private final CatalogRepository catalogRepository;
    private final UserRepository userRepository;

    public MyMarketPlaceController(CatalogRepository catalogRepository, UserRepository userRepository) {
        this.catalogRepository = catalogRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/health")
    public String index() {
        return "ok";
    }

    @GetMapping("/api/catalog-items")
    public List<CatalogItemEntity> catalogItems() {
        List<CatalogItemEntity> all = StreamSupport //
                .stream(catalogRepository.findAll().spliterator(), false) //
                .collect(Collectors.toList());
        return all;
    }

    @PutMapping(value = "/api/catalog-item", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addNewCatalogItem(@RequestBody CatalogItemEntity entity) {
        catalogRepository.save(entity);
        return "{}";
    }

    @DeleteMapping("/api/catalog-item/{id}")
    public String deleteNewCatalogItem(@PathVariable("id") Long id) {
        catalogRepository.deleteById(id);
        return "{}";
    }

    @GetMapping("/api/users")
    public List<UserEntity> users() {
        List<UserEntity> all = StreamSupport //
                .stream(userRepository.findAll().spliterator(), false) //
                .collect(Collectors.toList());
        return all;
    }

    @PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity login(@RequestBody UserEntity entity) {
        Optional<UserEntity> byName = userRepository.findByName(entity.getName());
        UserEntity user = byName.orElseThrow(() -> new IllegalStateException("user could not be found"));
        if (!entity.getPassword().equals(user.getPassword())) {
            throw new IllegalStateException("password dont match");
        }

        return user;
    }

    @PutMapping(value = "/api/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody UserEntity entity) {
        userRepository.save(entity);
        return "{}";
    }

    @DeleteMapping("/api/user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "{}";
    }
}
