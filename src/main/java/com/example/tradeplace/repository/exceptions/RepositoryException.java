package com.example.tradeplace.repository.exceptions;


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

    public RepositoryException(RepositoryException e) {
        super(e);
        this.repositoryName = e.getRepositoryName();
        this.methodName = e.getMethodName();
        this.methodParams = e.getMethodParams();
    }

}