package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.tracker.model.Item;

import java.util.ArrayList;
import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            int count = session.createQuery(
                            "UPDATE Item SET name = :fName, created = :fCreated WHERE id = :fId")
                    .setParameter("fName", item.getName())
                    .setParameter("fCreated", item.getCreated())
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            result = count > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            int count = session.createQuery(
                            "DELETE Item WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            result = count > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        List items = new ArrayList();
        try {
            session.beginTransaction();
            items = session.createQuery(
                    "from Item").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        List items = new ArrayList();
        try {
            session.beginTransaction();
            items = session.createQuery(
                            "from Item where name = :fName")
                    .setParameter("fName", key)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return items;
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        Item item = new Item();
        try {
            session.beginTransaction();
            item = (Item) session.createQuery(
                            "from Item WHERE id = :fId")
                    .setParameter("fId", id).uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return item;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
