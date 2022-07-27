package org.example.pojo.exception;

public class InvalidTimeException extends RuntimeException{

    public InvalidTimeException(String message){
        super(message);
    }
}
