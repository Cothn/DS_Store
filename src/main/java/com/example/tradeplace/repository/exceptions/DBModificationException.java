package com.example.tradeplace.repository.exceptions;

public class DBModificationException extends RepositoryException {

    public DBModificationException(String repositoryName, String methodType, String methodParams, Exception e) {
        super(repositoryName, methodType, methodParams, e);
    }
    public DBModificationException(RepositoryException e) {
        super(e);
    }

}
