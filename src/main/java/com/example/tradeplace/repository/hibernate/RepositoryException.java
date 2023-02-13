package com.example.tradeplace.repository.hibernate;


import lombok.Getter;

@Getter
public class RepositoryException extends RuntimeException {

    String repositoryName;
    String methodName;
    String methodParams;

    public RepositoryException(String repositoryName, String methodName, String methodParams, Exception e) {
        super("Exception in "+repositoryName+" repository "+methodName+//
                " method, with params:("+methodParams+")", e);
        this.repositoryName = repositoryName;
        this.methodName = methodName;
        this.methodParams = methodParams;
    }

}