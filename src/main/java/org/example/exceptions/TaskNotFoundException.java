package org.example.exceptions;

public class TaskNotFoundException extends RuntimeException{

    @Override
    public String getMessage(){return "Could not find searched Task";}


}
