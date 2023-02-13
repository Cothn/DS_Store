package com.example.tradeplace.service;

import com.example.tradeplace.entity.Company;

import java.util.List;

public interface CompanyService {
    long add(Company company);

    void update(Company company);

    void delete(long id);

    Company getById(long id);

    List<Company> getAll(int pageSize, int pageNum);

    List<Company> getByNameAndEmail(String name, String email, int pageSize, int pageNum);

    long getCount();
}
