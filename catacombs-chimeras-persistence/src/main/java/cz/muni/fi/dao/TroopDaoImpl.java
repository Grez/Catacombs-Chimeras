/**
 *
 * @author David Osička
 */
package cz.muni.fi.dao;

import cz.muni.fi.entity.Troop;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang3.Validate;


@Repository
public class TroopDaoImpl implements TroopDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    
    public void create(final Troop instance) {
        Validate.notNull(instance, "instance must not be null");
        entityManager.persist(instance);
    }
    
    public void update(final Troop instance) {
        Validate.notNull(instance, "instance must not be null");
        entityManager.merge(instance);
    }
    
    public void delete(final Troop instance) {
        Validate.notNull(instance, "instance must not be null");
        entityManager.remove(instance);
    }
    
    public Troop findById(final Long id) {
        Validate.notNull(id, "id must not be null");
        return entityManager.find(Troop.class, id);
    }
    
    public Troop findByName(final String name) {
        Validate.notNull(name, "name must not be null");
        try {
            return entityManager
                    .createQuery("SELECT t FROM Troop t WHERE name = :name", Troop.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }
    
    public List<Troop> findAll() {
        return entityManager.createQuery("SELECT t FROM Troop t", Troop.class).getResultList();
    }

}
