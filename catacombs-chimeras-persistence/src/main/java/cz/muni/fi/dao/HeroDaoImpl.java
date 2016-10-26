/**
 * @author Tom Bartoň
 */
package cz.muni.fi.dao;

import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.entity.Hero;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class HeroDaoImpl implements HeroDao{

    @PersistenceContext
    private EntityManager entityManager;

    public void create(final Hero hero) {
        notNull(hero, "hero should not be null");
        entityManager.persist(hero);
    }

    public void delete(final Hero hero) {
        notNull(hero, "hero should not be null");
        entityManager.remove(hero);
    }

    public Hero findById(final Long id) {
        notNull(id, "id should not be null");
        return entityManager.find(Hero.class, id);
    }

    public Hero findByName(final String name) {
        notNull(name, "name should not be null");
        try {
            return entityManager
                    .createQuery("select h from Hero h where name = :name", Hero.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Hero> findAll() {
        return entityManager.createQuery("select r from Hero r", Hero.class).getResultList();
    }

    public void update(final Hero hero) {
        notNull(hero, "hero should not be null");
        entityManager.merge(hero);
    }
}
