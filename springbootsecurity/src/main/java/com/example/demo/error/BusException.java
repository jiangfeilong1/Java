package com.example.demo.error;

/**
 * @author Administrator
 */
public class BusException extends RuntimeException{

    public BusException(){};

    public BusException(String message){
        super(message);
    }
}
