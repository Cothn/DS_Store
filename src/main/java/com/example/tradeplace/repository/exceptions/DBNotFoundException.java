package com.example.tradeplace.repository.exceptions;

public class DBNotFoundException extends RepositoryException {

    public DBNotFoundException(String repositoryName, String methodName, String methodParams, Exception e) {
        super(repositoryName, methodName, methodParams, e);
    }
    public DBNotFoundException(RepositoryException e){
        super(e);
    }

}
