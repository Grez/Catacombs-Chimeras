/**
 * @author David Osicka
 */
package cz.muni.fi.service;

import cz.muni.fi.dao.RoleDao;
import cz.muni.fi.entity.Role;
import cz.muni.fi.exceptions.NotFoundException;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;
import org.springframework.beans.factory.annotation.Autowired;


public class RoleServiceImpl implements RoleService {
    
    private final RoleDao roleDao;
    
    @Autowired
    public RoleServiceImpl(final RoleDao roleDao){
        notNull(roleDao);
        this.roleDao = roleDao;
    }
    
    @Override
    public Role findRoleById(final Long id){
        notNull(id);
        Role role = roleDao.findById(id);
        if(role == null){
            throw new NotFoundException("Role with ID: " + id + " not found");
        }
        return role;
    }

    @Override
    public Role findRoleByName(final String name){
        notNull(name);
        Role role = roleDao.findByName(name);
        if(role == null){
            throw new NotFoundException("Role with name: " + name + " not found");
        }
        return role;
    }

    @Override
    public List<Role> findAllRoles(){
        return roleDao.findAll();
    }

    @Override
    public void createRole(final Role role){
        notNull(role);
        roleDao.create(role);
    }

    @Override
    public void updateRole(final Role role){
        notNull(role);
        roleDao.update(role);
    }

    @Override
    public void removeRole(final Long id){
        notNull(id);
        Role role = roleDao.findById(id);
        if(role == null){
            throw new NotFoundException("Role with ID: " + id + " not found");
        }
        roleDao.delete(role);
    }
}
