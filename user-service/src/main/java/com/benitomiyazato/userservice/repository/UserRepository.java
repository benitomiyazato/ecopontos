package com.benitomiyazato.userservice.repository;

import com.benitomiyazato.userservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByPhoneOrEmailOrCpf(String phone, String email, String cpf);
}
