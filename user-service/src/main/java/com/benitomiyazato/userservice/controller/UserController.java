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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody @Valid UserRequest userRequest) {
        userService.saveUser(userRequest);
    }

    @DeleteMapping("/{uuid}")
    public void deleteUser(@PathVariable UUID uuid) {
        userService.deleteUser(uuid);
    }

    @GetMapping
    public List<UserResponse> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{uuid}")
    public UserResponse findUser(@PathVariable UUID uuid) {
        return userService.findUser(uuid);
    }
}
