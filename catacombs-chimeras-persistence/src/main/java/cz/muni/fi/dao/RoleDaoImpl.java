/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dao;

import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    public void create(final Role role) {
        notNull(role, "role should not be null");
        entityManager.persist(role);
    }

    public void delete(final Role role) {
        notNull(role, "role should not be null");
        entityManager.remove(role);
    }

    public Role findById(final Long id) {
        notNull(id, "id should not be null");
        return entityManager.find(Role.class, id);
    }

    public Role findByName(final String name) {
        notNull(name, "name should not be null");
        try {
            return entityManager
                    .createQuery("select c from Role c where name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<Role> findAll() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    public void update(final Role role) {
        notNull(role, "role should not be null");
        entityManager.merge(role);
    }
}
