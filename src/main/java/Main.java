import models.Admin;
import models.Department;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("main-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Create Department
        Department department = new Department("Admin");
        em.persist(department);

        Department department2 = new Department("Admin2");
        em.persist(department);

        // Create and persist Admin
        Admin admin = new Admin();
        admin.setName("Jane Doe");
        admin.setEmail("jane@example.com");
        admin.setPhone("555-5678");
        admin.setPost("Admin");
        admin.setDepartment(department);
        em.persist(admin);

        admin = new Admin();
        admin.setName("Jane Doe2");
        admin.setEmail("jane@example.com");
        admin.setPhone("555-5678");
        admin.setPost("Admin");
        admin.setDepartment(department);
        em.persist(admin);

        System.out.println(em.find(Admin.class, UUID.fromString("090741b2-a68a-437c-b616-2d26029c1811")).getName());

        // Commit transaction
        em.getTransaction().commit();
    }
}
