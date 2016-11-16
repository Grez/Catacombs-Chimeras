/**
 * @author David Osička
 */
package cz.muni.fi.service;

import cz.muni.fi.entity.Role;

import java.util.List;

public interface RoleService {

    /**
     * find role by id
     * @param id ID of role
     * @return role
     */
    Role findRoleById(final Long id);

    /**
     * find role by name
     * @param name of role
     * @return role
     */
    Role findRoleByName(final String name);

    /**
     * find all roles
     * @return list of all roles
     */
    List<Role> findAllRoles();

    /**
     * create new role
     * @param role to create
     */
    void createRole(final Role role);

    /**
     * update role
     * @param role to update
     */
    void updateRole(final Role role);

    /**
     * remove role specified by id
     * @param id of role to delete
     */
    void removeRole(final Long id);
}
