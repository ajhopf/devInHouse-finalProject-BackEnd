package com.example.labmedical.exceptions;

public class RegisterDataAlreadyExist extends GeneralException{
    public RegisterDataAlreadyExist(){super("O e-mail ou CPF fornecido já está em uso.");}
}
