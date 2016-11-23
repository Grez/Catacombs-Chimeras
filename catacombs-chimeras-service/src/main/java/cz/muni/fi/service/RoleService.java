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
     * @throws cz.muni.fi.exceptions.NotFoundException when role with id not found
     */
    Role findRoleById(final Long id);

    /**
     * find role by name
     * @param name of role
     * @return role
     * @throws cz.muni.fi.exceptions.NotFoundException when role with name not found
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
     * @return created role
     */
    Role createRole(final Role role);

    /**
     * update role
     * @param role to update
     */
    void updateRole(final Role role);

    /**
     * remove role specified by id
     * @param id of role to delete
     * @throws cz.muni.fi.exceptions.NotFoundException when role with id not found
     */
    void removeRole(final Long id);
}
