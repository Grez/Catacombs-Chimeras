/**
 * @author David Osička
 */
package cz.muni.fi.facade;

import cz.muni.fi.dto.RoleCreateDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.entity.Role;
import cz.muni.fi.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleFacadeImpl implements RoleFacade {
    
    private final RoleService roleService;
    
    @Autowired
    public RoleFacadeImpl(final RoleService roleService) {
        notNull(roleService);
        this.roleService = roleService;
    }

    @Override
    public RoleDTO findRoleById(final Long roleId) {
        notNull(roleId);
        final Role role = roleService.findRoleById(roleId);
        return convertToDTO(role);
    }

    @Override
    public RoleDTO findRoleByName(final String name) {
        notEmpty(name);
        final Role role = roleService.findRoleByName(name);
        return convertToDTO(role);
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        return roles.stream().map(RoleFacadeImpl::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDTO createRole(final RoleCreateDTO role) {
        notNull(role);
        final Role roleEntity = convertToEntity(role);
        return convertToDTO(roleService.createRole(roleEntity));
    }

    @Override
    public void updateRole(final RoleDTO role) {
        notNull(role);
        final Role roleEntity = convertToEntity(role);
        roleService.updateRole(roleEntity);
    }

    @Override
    public void removeRole(final Long id) {
        notNull(id);
        roleService.removeRole(id);
    }

    static Role convertToEntity(final RoleDTO roleDTO) {
        final Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        return role;
    }

    static Role convertToEntity(final RoleCreateDTO roleCreateDTO) {
        final Role role = new Role();
        role.setName(roleCreateDTO.getName());
        role.setDescription(roleCreateDTO.getDescription());
        return role;
    }

    static RoleDTO convertToDTO(final Role role) {
        return new RoleDTO(role.getId(), role.getName(), role.getDescription());
    }
}
