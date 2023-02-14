package com.example.tradeplace.service.implementation;

import com.example.tradeplace.entity.Company;
import com.example.tradeplace.repository.CompanyRepository;
import com.example.tradeplace.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public long add(Company company) {
        return companyRepository.add(company);
    }

    @Override
    public void update(Company company) {
        companyRepository.update(company);
    }

    @Override
    public void delete(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company getById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<Company> getAll(int pageSize, int pageNum) {
        return companyRepository.findAll(pageSize, pageNum);
    }

    @Override
    public List<Company> getByNameAndEmail(String name, String email, int pageSize, int pageNum) {
        return companyRepository.findByNameAndEmail(name, email, pageSize, pageNum);
    }

    @Override
    public long getCount() {
        return companyRepository.allCount();
    }
}
