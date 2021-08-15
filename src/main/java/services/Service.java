package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface Service{

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unitT");
    EntityManager entityManager = entityManagerFactory.createEntityManager();


}
