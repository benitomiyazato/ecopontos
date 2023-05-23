package com.benitomiyazato.userservice.service;

import com.benitomiyazato.userservice.dto.UserRequest;
import com.benitomiyazato.userservice.dto.UserResponse;
import com.benitomiyazato.userservice.model.UserModel;
import com.benitomiyazato.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(UserRequest userRequest) {
        if (userRepository.findByPhoneOrEmailOrCpf(userRequest.getPhone(), userRequest.getEmail(), userRequest.getCpf()).isPresent()) {
            // TODO: throw exception
            throw new IllegalArgumentException("TUDO ERRADO");
        }
        UserModel userToSave = new UserModel();
        BeanUtils.copyProperties(userRequest, userToSave);
        userToSave.setCreatedAt(LocalDateTime.now());
        userRepository.save(userToSave);
    }

    public void deleteUser(UUID uuid) {
        userRepository.deleteById(uuid);
    }

    public List<UserResponse> findAllUsers() {
        List<UserModel> userModelList = userRepository.findAll();
        return userModelList.stream().map(userModel -> UserResponse
                .builder()
                .userId(userModel.getUserId())
                .email(userModel.getEmail())
                .fullName(userModel.getFullName())
                .cpf(userModel.getCpf())
                .phone(userModel.getPhone())
                .address(userModel.getAddress())
                .cep(userModel.getCep())
                .build()).toList();
    }

    public UserResponse findUser(UUID uuid) {
        // TODO: exception handling
        UserModel userModel = userRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Id inválido"));

        return UserResponse.builder()
                .userId(userModel.getUserId())
                .email(userModel.getEmail())
                .fullName(userModel.getFullName())
                .cpf(userModel.getCpf())
                .phone(userModel.getPhone())
                .address(userModel.getAddress())
                .cep(userModel.getCep())
                .build();
    }
}
