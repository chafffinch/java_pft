package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.ContactData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {

        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Set<ContactData> contacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List contacts = session.createQuery("from ContactData where enabled = 1 and username <> 'administrator'").list();
        session.getTransaction().commit();
        session.close();
        return new HashSet<>(contacts);
    }
}




