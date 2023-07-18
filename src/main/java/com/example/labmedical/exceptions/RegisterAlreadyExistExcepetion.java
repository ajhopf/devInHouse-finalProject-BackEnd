package com.example.labmedical.exceptions;

public class RegisterAlreadyExistExcepetion extends GeneralException{
    public RegisterAlreadyExistExcepetion(){super("O e-mail ou CPF fornecido já está em uso.");}
}
