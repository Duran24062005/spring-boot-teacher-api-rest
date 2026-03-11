package com.s1.gestion_profesion.exception;

public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String mensaje){
        super(mensaje);
    }
}
