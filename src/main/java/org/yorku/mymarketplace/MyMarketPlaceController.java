package org.yorku.mymarketplace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@Slf4j
public class MyMarketPlaceController {

    private final CatalogRepository catalogRepository;
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public class UnAuthorizedException extends RuntimeException {

        public UnAuthorizedException(String message) {
            super(message);
            log.warn(message);
        }

    }

    private void checkIfAdmin(String token) {
        Optional<UserEntity> byId = userRepository.findById(Long.valueOf(token));
        Optional<UserEntity> userEntity = byId.filter(u -> "admin".equalsIgnoreCase(u.getRole()));
        if (userEntity.isEmpty()) {
            throw new UnAuthorizedException("Not authorized");
        }
    }

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
    public String addNewCatalogItem(@RequestBody CatalogItemEntity entity,
                                    @RequestHeader("authentication") String token) {
        checkIfAdmin(token);
        catalogRepository.save(entity);
        return "{}";
    }

    @GetMapping("/api/catalog-item/{id}")
    public CatalogItemEntity getCatalogItem(@PathVariable("id") Long id) {

        return catalogRepository.findById(id).get();
    }

    @DeleteMapping("/api/catalog-item/{id}")
    public String deleteNewCatalogItem(@PathVariable("id") Long id,
                                       @RequestHeader("authentication") String token) {
        checkIfAdmin(token);
        catalogRepository.deleteById(id);
        return "{}";
    }


    @GetMapping("/api/users")
    public List<UserEntity> users(@RequestHeader("authentication") String token) {
        checkIfAdmin(token);
        List<UserEntity> all = StreamSupport //
                .stream(userRepository.findAll().spliterator(), false) //
                .collect(Collectors.toList());
        return all;
    }

    @PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity login(@RequestBody UserEntity entity) {
        log.info("login: {}", entity);
        Optional<UserEntity> byName = userRepository.findByName(entity.getName());
        UserEntity user = byName.orElseThrow(() -> new IllegalStateException("user could not be found"));
        if (!entity.getPassword().equals(user.getPassword())) {
            throw new IllegalStateException("password dont match");
        }

        return user;
    }

    @PutMapping(value = "/api/add-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserEntity createNewAppUser(@RequestBody UserEntity entity,
                                       @RequestHeader("authentication") String token) {
        log.info("/api/add-user, {}", entity);
//      TODO: add security check
//        if (entity.getId() != null) {
//            checkIfAdmin(token);
//        }
        List<UserEntity> byName = userRepository.checkNames(entity.getName());
        if (entity.getId() == null && byName.size() > 0) {
            throw new IllegalArgumentException("user already exists");
        }
        UserEntity saved = userRepository.save(entity);
        entity.setId(saved.getId());
        return entity;
    }

    @DeleteMapping("/api/user/{id}")
    public String deleteUser(@PathVariable("id") Long id,
                             @RequestHeader("authentication") String token) {
        checkIfAdmin(token);
        userRepository.deleteById(id);
        return "{}";
    }

    @GetMapping("/api/user/{id}")
    public UserEntity selectUser(@PathVariable("id") Long id,
                                 @RequestHeader("authentication") String token) {
        //checkIfAdmin(token);
        // TODO, check for self + admin

        return userRepository.findById(id).get();
    }
}
