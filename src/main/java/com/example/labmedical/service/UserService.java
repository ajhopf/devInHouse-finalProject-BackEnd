package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.AuthenticationRequest;
import com.example.labmedical.controller.dtos.request.AuthenticationResponse;
import com.example.labmedical.controller.dtos.request.UserListResponse;
import com.example.labmedical.controller.dtos.request.UserRegisterRequest;
import com.example.labmedical.exceptions.EmptyUserListException;
import com.example.labmedical.exceptions.RegisterAlreadyExistExcepetion;
import com.example.labmedical.exceptions.WrongCredentialsException;
import com.example.labmedical.repository.UserRepository;
import com.example.labmedical.repository.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private TokenService tokenService;

    public AuthenticationResponse loginUser(AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();

        User user = this.findUserByEmailAndPassword(email, password);

        String token = Jwts.builder()
                .setSubject(user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        AuthenticationResponse response = AuthenticationResponse.builder()
                .name(user.getName())
                .photoUrl(user.getPhotoUrl())
                .role(user.getRole())
                .token(token)
                .build();

        String logDescription = "O(a) " + response.getRole().toString().substring(5) + " " + response.getName() + " efetuou login no sistema.";

        tokenService.save(token, user);

        logService.success(logDescription);

        return response;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(WrongCredentialsException::new);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String saveUser(UserRegisterRequest request) {
        Boolean userExist = checkIfUserExist(request);
        if(userExist){
            throw new RegisterAlreadyExistExcepetion();
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
        return "Usuário criado com sucesso";
    }

    public Boolean checkIfUserExist(UserRegisterRequest request){
        Boolean register = userRepository.existsByEmailOrCpf(request.getEmail(), request.getCpf());
        return register;
    }

    public List<UserListResponse> getListUsers() {
        List<User> users = userRepository.findAll();
        if(users.size() == 0){
            throw new EmptyUserListException();
        }
        List<UserListResponse> usersListResponse = users.stream().map(user -> {
            UserListResponse userResponse = UserListResponse.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            return  userResponse;
        }).collect(Collectors.toList());
        logService.success("Lista de usuários enviada");
        return usersListResponse;
    }
}
