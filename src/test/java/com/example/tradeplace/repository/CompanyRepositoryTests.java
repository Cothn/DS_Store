package com.example.tradeplace.repository;

import com.example.tradeplace.entity.Company;
import com.example.tradeplace.repository.hibernate.RepositoryException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class CompanyRepositoryTests {

    @Autowired
    private CompanyRepository companyRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void setUpBeforeAll()  {
        postgres.start();
    }

    @AfterAll
    static void tearDownAfterAll()  {
        postgres.stop();
    }

    @AfterEach
    void tearDownAfterEach()  {
        companyRepository.findAll().forEach(company -> companyRepository.deleteById(company.getId()));
    }
    

    @Test
    void add_addAbsolutelyNewCompany_companyAdded() {
        //Given
        long oldCount = companyRepository.allCount();

        //When
        long id = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));

        //Then
        assertThat(companyRepository.findById(id)).isNotNull();
        assertThat(companyRepository.allCount()).isEqualTo(oldCount+1);
        
    }

    @Test
    void add_addAnExistCompany_notAddedAndThrowRepositoryException () {
        //Given
        companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        long oldCount = companyRepository.allCount();

        //When
        RepositoryException thrown = Assertions.assertThrows(RepositoryException.class, () ->
                companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription")));

        //Then
        assertThat(companyRepository.allCount()).isEqualTo(oldCount);
        assertThat(thrown).isNotNull();
        assertThat(thrown.getMethodName()).isEqualTo("add");
        
    }

    @Test
    void add_addCompanyWithNullableAttributes_notAddedAndThrowRepositoryException () {
        //Given
        long oldCount = companyRepository.allCount();

        //When
        RepositoryException thrown = Assertions.assertThrows(RepositoryException.class, () ->
                companyRepository.add(
                new Company(null,//
                        null,//
                        null,//
                        null,//
                        null)));

        //Then
        assertThat(companyRepository.allCount()).isEqualTo(oldCount);
        assertThat(thrown).isNotNull();
        assertThat(thrown.getMethodName()).isEqualTo("add");
    }

    @Test
    void add_addCompanyWithExistId_addedNewCompanyWithOtherId() {
        //Given
        long id1 = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        long oldCount = companyRepository.allCount();

        //When
        long id2 = companyRepository.add(
                new Company(id1,//
                        "newCompany2",//
                        "newCompany2@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));

        //Then
        assertThat(id2).isNotEqualTo(id1);
        assertThat(companyRepository.findById(id2)).isNotNull();
        assertThat(companyRepository.allCount()).isEqualTo(oldCount+1);
        
    }

    @Test
    void update_updateAllChangeableAttributes_allCompanyAttributesChanged() {
        //Given
        long id = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        Company company = companyRepository.findById(id);
        company.setName("newCompanyUpdate");
        company.setEmail("newCompanyUpdate@mail.com");
        company.setDescription("newCompanyDescription Update");
        List<Company> companies = companyRepository.findAll();

        //When
        companyRepository.update(company);

        //Then
        assertThat(companyRepository.findById(id)).isEqualTo(company);
        assertThat(companies).isNotEqualTo(companyRepository.findAll());
    }

    @Test
    void update_updateNotExistCompany_companiesListNotChangedAndThrowRepositoryException() {
        //Given
        long id = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        companyRepository.deleteById(id);
        List<Company> companies = companyRepository.findAll();

        //When
        RepositoryException thrown = Assertions.assertThrows(RepositoryException.class, () ->
                companyRepository.update(
                new Company(id,//
                        "newCompanyUpdate",//
                        "newCompanyUpdate@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription Update")));

        //Then
        assertThat(companies).isEqualTo(companyRepository.findAll());
        assertThat(thrown).isNotNull();
        assertThat(thrown.getMethodName()).isEqualTo("update");
    }

    @Test
    void deleteById_deleteExistCompany_companyDeleted() {
        //Given
        long id;
        id = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        long oldCount = companyRepository.allCount();

        //When
        companyRepository.deleteById(id);

        //Then
        assertThat(companyRepository.findById(id)).isNull();
        assertThat(companyRepository.allCount()).isEqualTo(oldCount-1);
    }

    @Test
    void deleteById_deleteNotExistCompany_countNotChangedAndThrowRepositoryException() {
        //Given
        long id;
        id = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        companyRepository.deleteById(id);
        long oldCount = companyRepository.allCount();

        //When
        RepositoryException thrown = Assertions.assertThrows(RepositoryException.class, () ->
                companyRepository.deleteById(id));

        //Then
        assertThat(companyRepository.allCount()).isEqualTo(oldCount);
        assertThat(thrown).isNotNull();
        assertThat(thrown.getMethodName()).isEqualTo("deleteById");
    }


    @Test
    void findById_findCompany_companyFainted() {
        //Given
        long id = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));

        //When
        Company company = companyRepository.findById(id);

        //Then
        assertThat(company).isNotNull();
        assertThat(company.getName()).isEqualTo("newCompany");
    }

    @Test
    void findById_findNotExistedCompany_notFainted() {
        //Given
        long id = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        companyRepository.deleteById(id);

        //When
        Company company = companyRepository.findById(id);

        //Then
        assertThat(company).isNull();
    }

    @Test
    void findByNameAndEmail_findCompanyByName_faintedCompanyWithEqualsName() {
        //Given
        companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        companyRepository.add(
                new Company(null,//
                        "Company1",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company1 Description"));
        companyRepository.add(
                new Company(null,//
                        "Company2",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company Description"));

        //When
        Company company = companyRepository.
                findByNameAndEmail("Company1", null, 1, 1).get(0);

        //Then
        assertThat(company.getName()).isEqualTo("Company1");
        assertThat(company.getEmail()).isNotNull();
    }

    @Test
    void findByNameAndEmail_findCompaniesByEmail_faintedCompaniesWithEqualsEmail() {
        //Given
        long id1 = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        long id2 = companyRepository.add(
                new Company(null,//
                        "Company1",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company1 Description"));
        long id3 = companyRepository.add(
                new Company(null,//
                        "Company2",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company Description"));

        //When
        List<Company> companies = companyRepository.
                findByNameAndEmail(null, "Company@mail.com", 10, 1);

        //Then
        assertThat(companies.size()).isEqualTo(2);
        assertThat(companies).doesNotContain(companyRepository.findById(id1));
        assertThat(companies).contains(companyRepository.findById(id2),
                companyRepository.findById(id3));
    }

    @Test
    void findByNameAndEmail_findCompaniesByNameAndEmail_faintedCompanyWithEqualsNameAndEmail() {
        //Given
        long id1 = companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        long id2 = companyRepository.add(
                new Company(null,//
                        "Company1",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company1 Description"));
        long id3 = companyRepository.add(
                new Company(null,//
                        "Company2",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company Description"));

        //When
        List<Company> companies = companyRepository.
                findByNameAndEmail("Company1", "Company@mail.com", 10, 1);

        //Then
        assertThat(companies.size()).isEqualTo(1);
        assertThat(companies).doesNotContain(companyRepository.findById(id1),
                companyRepository.findById(id3));
        assertThat(companies).contains(companyRepository.findById(id2));
    }

    @Test
    void findByNameAndEmail_findCompaniesByNotExistNameAndExistEmail_nothingFainted() {
        //Given
        companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        companyRepository.add(
                new Company(null,//
                        "Company1",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company1 Description"));
        companyRepository.add(
                new Company(null,//
                        "Company2",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company Description"));

        //When
        List<Company> companies = companyRepository.
                findByNameAndEmail("q", "Company@mail.com", 10, 1);

        //Then
        assertThat(companies.size()).isEqualTo(0);
    }

    @Test
    void findByNameAndEmail_findCompaniesByExistNameAndNotExistEmail_nothingFainted() {
        //Given
        companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        companyRepository.add(
                new Company(null,//
                        "Company1",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company1 Description"));
        companyRepository.add(
                new Company(null,//
                        "Company2",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company Description"));

        //When
        List<Company> companies = companyRepository.
                findByNameAndEmail("Company2", "q", 10, 1);

        //Then
        assertThat(companies.size()).isEqualTo(0);
    }

    @Test
    void findByNameAndEmail_findWithPageSizeMoreThanCompaniesCont_listSizeEqualsCompaniesCount() {
        //Given
        companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        companyRepository.add(
                new Company(null,//
                        "Company1",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company1 Description"));
        companyRepository.add(
                new Company(null,//
                        "Company2",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company Description"));

        //When
        List<Company> companies = companyRepository.
                findByNameAndEmail(null, null, (int)companyRepository.allCount()+1, 1);

        //Then
        assertThat(companies.size()).isEqualTo(companyRepository.allCount());
    }

    @Test
    void findByNameAndEmail_findWithPageMoreThanRealPageExist_emptyList() {
        //Given
        companyRepository.add(
                new Company(null,//
                        "newCompany",//
                        "newCompany@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "newCompanyDescription"));
        companyRepository.add(
                new Company(null,//
                        "Company1",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company1 Description"));
        companyRepository.add(
                new Company(null,//
                        "Company2",//
                        "Company@mail.com",//
                        new Timestamp(System.currentTimeMillis()),//
                        "Company Description"));

        //When
        List<Company> companies = companyRepository.
                findByNameAndEmail(null, null, (int)companyRepository.allCount()+1, 2);

        //Then
        assertThat(companies.size()).isEqualTo(0);
    }
}
