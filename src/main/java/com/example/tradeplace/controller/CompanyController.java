package com.example.tradeplace.controller;

import com.example.tradeplace.entity.Company;
import com.example.tradeplace.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {

    private static final int MAX_PAGE_SIZE = 20;

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Company company){
        companyService.add(company);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Company>> read(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {

        pageSize = pageSize < MAX_PAGE_SIZE ? pageSize : MAX_PAGE_SIZE;
        final List<Company> companies = companyService.getByNameAndEmail(name, email, pageSize, pageNum);

        return  !companies.isEmpty()
                ? new ResponseEntity<>(companies, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> read(@PathVariable(name = "id") int id) {
        final Company company = companyService.getById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Company company){
        companyService.update(company);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id){
        companyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
