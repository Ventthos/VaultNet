package com.ventthos.Vaultnet.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private final Code code;

    public ApiException(Code code){
        super(code.getDefaultMessage());
        this.code = code;
    }

    public ApiException(Code code, String customMessage){
        super(customMessage);
        this.code = code;
    }

    public int getHttpCode(){
        return code.getHttpStatus().value();
    }

}
