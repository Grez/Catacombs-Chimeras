package cz.muni.fi.dao;

import cz.muni.fi.entity.Role;

import java.util.List;

/**
 * @author Ondřej Benkovský
 */
public interface RoleDao {
    /**
     * creates new role
     * @param role
     */
    void create(final Role role);

    /**
     * delete role
     * @param role
     */
    void delete(final Role role);

    /**
     * find role by ID
     * @param id
     * @return role if found, else null
     */
    Role findById(final Long id);

    /**
     * find role by name
     * @param name
     * @return role if found, else null
     */
    Role findByName(final String name);

    /**
     * find all roles
     * @return
     */
    List<Role> findAll();

    /**
     * update role
     * @param role
     */
    void update(final Role role);
}
