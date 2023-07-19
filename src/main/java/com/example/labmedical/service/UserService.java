package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ResetUserPasswordRequest;
import com.example.labmedical.controller.dtos.request.UserListResponse;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.controller.dtos.response.UserIdByEmailResponse;

import com.example.labmedical.enums.Role;
import com.example.labmedical.exceptions.UserException;

import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogService logService;

    public UserIdByEmailResponse getUserIdByEmail(String email) {
        User user = findUserByEmail(email);

        return UserIdByEmailResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new WrongCredentialsException("Email ou senha informados não conferem ou não existem."));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new WrongCredentialsException("Email informado não encontrado."));
    }

    public void updateUserPassword(ResetUserPasswordRequest resetUserPasswordRequest) {
        User user = userRepository.findById(resetUserPasswordRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id informado"));

        user.setPassword(resetUserPasswordRequest.getPassword());
        userRepository.save(user);

        String logDescription = "O(a) " + user.getRole().toString().substring(5) + " " + user.getName() + " resetou a senha.";
        logService.success(logDescription);
    }


    public User saveUser(UserRegisterRequest request) {
        Boolean userExist = checkIfUserExist(request);

        if (userExist) {
            throw new UserException("O e-mail ou CPF fornecido já está em uso");

        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .password(request.getPassword())
                .gender(request.getGender())
                .cpf(request.getCpf())
                .telephone(request.getTelephone())
                .build();
        userRepository.save(user);
        logService.success(String.format("Usuário id: %d cadastrado", user.getId()));
        return user;
    }

    public Boolean checkIfUserExist(UserRegisterRequest request) {
        return userRepository.existsByEmailOrCpf(request.getEmail(), request.getCpf());
    }

    public List<UserListResponse> getListUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            throw new UserException("Lista de usários vazia");
        }
        List<UserListResponse> usersListResponse = users.stream().map(user -> {
            UserListResponse userResponse = UserListResponse.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            return userResponse;
        }).collect(Collectors.toList());
        logService.success("Lista de usuários enviada");
        return usersListResponse;

    }

    public String updateUser(Long id, UserRegisterRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("Usuário não encontrado"));
        if (!Objects.equals(user.getCpf(), request.getCpf())) {
            throw new UserException("CPF não pode ser modificado");
        }
        Boolean canUpdateRole = checkRoleUpdate(user.getRole(), request.getRole());
        if (!canUpdateRole) {
            throw new UserException("Não é possível modificar para esse tipo de usuário");
        }
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setPassword(request.getPassword());
        user.setTelephone(request.getTelephone());
        userRepository.save(user);
        logService.success(String.format("Usuário id: %d atualizado", user.getId()));
        return "Usuário atualizado com sucesso";
    }

    Boolean checkRoleUpdate(Role currentRole, Role newRole) {
        if (!Objects.equals(newRole, Role.ROLE_DOCTOR) && !Objects.equals(newRole, Role.ROLE_NURSE)) {
            return false;
        }
        if (!Objects.equals(currentRole, Role.ROLE_DOCTOR) && !Objects.equals(currentRole, Role.ROLE_NURSE)) {
            return false;
        } else {
            return true;
        }
    }
}
