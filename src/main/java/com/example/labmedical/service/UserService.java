package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ResetUserPasswordRequest;
import com.example.labmedical.controller.dtos.response.UserResponse;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.controller.dtos.response.UserIdByEmailResponse;

import com.example.labmedical.controller.mapper.UserMapper;
import com.example.labmedical.controller.mapper.UserResponseMapper;
import com.example.labmedical.enums.Role;
import com.example.labmedical.exceptions.UserException;

import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.User;
import jakarta.persistence.EntityManager;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserResponseMapper userResponseMapper;

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
        User user = userMapper.map(request);
        userRepository.save(user);
        logService.success(String.format("Usuário id: %d cadastrado", user.getId()));
        return user;
    }

    public Boolean checkIfUserExist(UserRegisterRequest request) {
        return userRepository.existsByEmailOrCpf(request.getEmail(), request.getCpf());
    }

    public List<UserResponse> getListUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            throw new UserException("Lista de usários vazia");
        }
        List<UserResponse> usersListResponse = users.stream().map(user -> {
            UserResponse userResponse = userResponseMapper.map(user);
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
        User newUser = userMapper.map(request);
        newUser.setId(user.getId());
        newUser.setCpf(user.getCpf());
        userRepository.save(newUser);
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

    public UserResponse userSearch(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("Usuário não encontrado"));
        logService.success(String.format("Usuário ID: %d encontrado", user.getId()));
        UserResponse response = userResponseMapper.map(user);
        return response;
    }

    public String deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("Usuário não encontrado"));
        userRepository.deleteById(user.getId());
        logService.success(String.format("Usuário ID: %d removido", user.getId()));
        return "Usuário removido com sucesso";
    }
}
