/**
 * @author David Osička
 */
package cz.muni.fi.facade;

import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleCreateDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.entity.Role;
import cz.muni.fi.service.MappingService;
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
    private final MappingService mappingService;
    
    @Autowired
    public RoleFacadeImpl(final RoleService roleService, final MappingService mappingService) {
        notNull(roleService);
        notNull(mappingService);
        this.roleService = roleService;
        this.mappingService = mappingService;
    }

    @Override
    public RoleDTO findRoleById(final Long roleId) {
        notNull(roleId);
        final Role role = roleService.findRoleById(roleId);
        return mappingService.convertToDTO(role);
    }

    @Override
    public RoleDTO findRoleByName(final String name) {
        notEmpty(name);
        final Role role = roleService.findRoleByName(name);
        return mappingService.convertToDTO(role);
    }

    @Override
    public List<RoleDTO> findAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        return roles.stream().map(mappingService::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDTO createRole(final RoleCreateDTO role) {
        notNull(role);
        final Role roleEntity = mappingService.convertToEntity(role);
        return mappingService.convertToDTO(roleService.createRole(roleEntity));
    }

    @Override
    public void updateRole(final RoleDTO role) {
        notNull(role);
        final Role roleEntity = mappingService.convertToEntity(role);
        List<HeroDTO> heroesWithRole = findHeroesWithRole(role.getId());
        heroesWithRole.forEach(hero -> roleEntity.addHero(mappingService.convertToEntity(hero)));

        roleService.updateRole(roleEntity);
    }

    @Override
    public void removeRole(final Long id) {
        notNull(id);
        roleService.removeRole(id);
    }

    @Override
    public List<HeroDTO> findHeroesWithRole(final Long id) {
        return roleService.findHeroesWithRole(id).stream().map(mappingService::convertToDTO).collect(Collectors.toList());
    }
}
