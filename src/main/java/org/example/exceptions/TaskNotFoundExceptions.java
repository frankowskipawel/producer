package org.example.exceptions;

public class TaskNotFoundExceptions extends RuntimeException{

    @Override
    public String getMessage(){return "Could not find searched Task";}


}
