package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.ResetUserPasswordRequest;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.controller.dtos.response.UserIdByEmailResponse;
import com.example.labmedical.exceptions.RegisterDataAlreadyExist;
import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogService logService;

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new WrongCredentialsException("Email ou senha informados não conferem ou não existem."));
    }

    public UserIdByEmailResponse getUserIdByEmail(String email) {
        User user = findUserByEmail(email);

        return UserIdByEmailResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
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
            throw new RegisterDataAlreadyExist();
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
}
