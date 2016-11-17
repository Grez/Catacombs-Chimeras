/**
 * @author David Osička
 */
package cz.muni.fi.facade;

import cz.muni.fi.dto.RoleCreateDTO;
import cz.muni.fi.dto.RoleDTO;

import java.util.List;

public interface RoleFacade {

    /**
     * find role by id
     * @param id ID of role
     * @return role
     */
    RoleDTO findRoleById(final Long id);

    /**
     * find role by name
     * @param name of role
     * @return role
     */
    RoleDTO findRoleByName(final String name);

    /**
     * find all roles
     * @return list of all roles
     */
    List<RoleDTO> findAllRoles();

    /**
     * create new role
     * @param role to create
     * @return created role
     */
    RoleDTO createRole(final RoleCreateDTO role);

    /**
     * update role
     * @param role to update
     */
    void updateRole(final RoleDTO role);

    /**
     * remove role specified by id
     * @param id of role to delete
     */
    void removeRole(final Long id);
}
