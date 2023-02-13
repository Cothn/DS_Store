package com.example.tradeplace.repository.hibernate;

import com.example.tradeplace.entity.Company;
import com.example.tradeplace.repository.CompanyRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepositoryImpl  implements CompanyRepository {


    private final SessionFactory sessionFactory;

    @Autowired
    public CompanyRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long add(Company company){
        long result = 0;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            result = (long) session.save(company);
            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Company", "add", company.toString(), e);
        }
        return result;
    }

    @Override
    public void update(Company company){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(company);
            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Company", "update", company.toString(), e);
        }
    }

    @Override
    public void deleteById(long id){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(Company.class, id));
            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Company", "deleteById", Long.toString(id), e);
        }

    }

    @Override
    public Company findById(long id){
        Company result;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            result = session.get(Company.class, id);
            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Company", "findById", Long.toString(id), e);
        }
        return result;
    }

    @Override
    public List<Company> findAll(){
        List<Company> companies = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Company> cq = cb.createQuery(Company.class);
            Root<Company> root = cq.from(Company.class);
            cq.select(root);

            Transaction transaction = session.beginTransaction();

            companies = session.createQuery(cq).getResultList();

            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Company", "findAll", "", e);
        }
        return companies;

    }

    @Override
    public List<Company> findAll(int pageSize, int pageNum){
        List<Company> companies = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Company> cq = cb.createQuery(Company.class);
            Root<Company> root = cq.from(Company.class);
            cq.select(root);

            Transaction transaction = session.beginTransaction();

            companies = session.createQuery(cq)//
                    .setFirstResult((pageNum-1)*pageSize)//
                    .setMaxResults(pageSize)//
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            String params = "pageNum: "+Long.toString(pageNum)+"; "+//
                    "pageSize: "+Long.toString(pageSize);
            throw new RepositoryException("Company", "findAll", params, e);
        }
        return companies;

    }

    @Override
    public List<Company> findByNameAndEmail(String name, String email, int pageSize, int pageNum){
        List<Company> companies = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Company> cq = cb.createQuery(Company.class);
            Root<Company> root = cq.from(Company.class);
            cq.select(root);

            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.equal(root.get("name"), name));
            }
            if (email != null) {
                predicates.add(cb.equal(root.get("email"), email));
            }
            cq.where(predicates.toArray(new Predicate[0]));

            Transaction transaction = session.beginTransaction();

            companies = session.createQuery(cq)//
                    .setFirstResult((pageNum-1)*pageSize)//
                    .setMaxResults(pageSize)//
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            String params = "";
            if (name != null) {
                params +="name: "+name+"; ";
            }
            if (email != null) {
                params +="email: "+email+"; ";
            }
            params +="pageNum: "+Long.toString(pageNum)+"; "+//
                    "pageSize: "+Long.toString(pageSize);
            throw new RepositoryException("Company", "findByNameAndEmail", params, e);
        }
        return companies;
    }

    @Override
    public long allCount(){
        long result;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Company> root = cq.from(Company.class);
            cq.select(cb.count(root));

            Transaction transaction = session.beginTransaction();

            result = session.createQuery(cq).getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            throw new RepositoryException("Company", "allCount", "", e);
        }
        return result;
    }

}
