package com.example.tradeplace.repository.exceptions;

public class DBFoundException extends RepositoryException {

    public DBFoundException(String repositoryName, String methodName, String methodParams, Exception e) {
        super(repositoryName, methodName, methodParams, e);
    }
    public DBFoundException(RepositoryException e) {
        super(e);
    }

}
