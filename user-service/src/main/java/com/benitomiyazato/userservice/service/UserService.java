package com.benitomiyazato.userservice.service;

import com.benitomiyazato.userservice.dto.UserRequest;
import com.benitomiyazato.userservice.dto.UserResponse;
import com.benitomiyazato.userservice.model.UserModel;
import com.benitomiyazato.userservice.repository.UserRepository;
import com.benitomiyazato.userservice.utils.CopyPropertiesNotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public UserResponse updateUser(UUID uuid, UserRequest userRequest) {
        UserModel userToUpdate = userRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Id inválido"));

        CopyPropertiesNotNull.copyProperties(userRequest, userToUpdate);
        UserModel updatedUser = userRepository.save(userToUpdate);

        return UserResponse.builder()
                .userId(updatedUser.getUserId())
                .email(updatedUser.getEmail())
                .fullName(updatedUser.getFullName())
                .cpf(updatedUser.getCpf())
                .phone(updatedUser.getPhone())
                .address(updatedUser.getAddress())
                .cep(updatedUser.getCep())
                .build();
    }
}
