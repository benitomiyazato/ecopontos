package com.benitomiyazato.userservice.controller;

import com.benitomiyazato.userservice.dto.UserRequest;
import com.benitomiyazato.userservice.dto.UserResponse;
import com.benitomiyazato.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    Logger logger = LogManager.getLogger(UserController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody @Valid UserRequest userRequest) {
        logger.info("Hit Save user Endpoint");
        userService.saveUser(userRequest);
    }

    @DeleteMapping("/{uuid}")
    public void deleteUser(@PathVariable UUID uuid) {
        logger.info("Hit Delete user Endpoint with ID = {}", uuid);
        userService.deleteUser(uuid);
    }

    @PutMapping("/{uuid}")
    public UserResponse updateUser(@PathVariable UUID uuid, @RequestBody UserRequest userRequest) {
        logger.info("Hit Delete User with ID = {}", uuid);
        return userService.updateUser(uuid, userRequest);
    }

    @GetMapping
    public List<UserResponse> findAllUsers() {
        logger.info("Hit Find all users Endpoint");
        return userService.findAllUsers();
    }

    @GetMapping("/{uuid}")
    public UserResponse findUser(@PathVariable UUID uuid) {
        logger.info("Hit Find User with ID = {}", uuid);
        return userService.findUser(uuid);
    }
}
