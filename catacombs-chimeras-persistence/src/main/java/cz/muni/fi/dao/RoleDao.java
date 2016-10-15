package cz.muni.fi.dao;

import cz.muni.fi.entity.Role;

import java.util.List;

/**
 * @author Ondřej Benkovský
 */
public interface RoleDao {
    void create(final Role role);
    void delete(final Role role);
    Role findById(final Long id);
    Role findByName(final String name);
    List<Role> findAll();
    void update(final Role role);
}
