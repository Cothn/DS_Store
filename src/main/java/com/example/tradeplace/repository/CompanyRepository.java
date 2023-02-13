package com.example.tradeplace.repository;

import com.example.tradeplace.entity.Company;
import com.example.tradeplace.repository.hibernate.RepositoryException;

import java.util.List;

public interface CompanyRepository {

    long add(Company company);

    void update(Company company);

    void deleteById(long id);

    Company findById(long id);

    List<Company> findAll();

    List<Company> findAll(int pageSize, int pageNum);

    List<Company> findByNameAndEmail(String name, String email, int pageSize, int pageNum);

    long allCount();
}
