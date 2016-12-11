/**
 * @author David Osicka
 */
package cz.muni.fi.service;

import cz.muni.fi.dao.HeroDao;
import cz.muni.fi.dao.RoleDao;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
import cz.muni.fi.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    
    private final RoleDao roleDao;
    private final HeroDao heroDao;

    @Autowired
    public RoleServiceImpl(final RoleDao roleDao, final HeroDao heroDao) {
        notNull(roleDao);
        notNull(heroDao);
        this.roleDao = roleDao;
        this.heroDao = heroDao;
    }
    
    @Override
    public Role findRoleById(final Long id){
        notNull(id);
        Role role = roleDao.findById(id);
        if(role == null){
            throw new NotFoundException("Role with ID \"" + id + "\" not found");
        }
        return role;
    }

    @Override
    public Role findRoleByName(final String name){
        notNull(name);
        Role role = roleDao.findByName(name);
        if(role == null){
            throw new NotFoundException("Role with name \"" + name + "\" not found");
        }
        return role;
    }

    @Override
    public List<Role> findAllRoles(){
        return roleDao.findAll();
    }

    @Override
    public Role createRole(final Role role){
        notNull(role);
        roleDao.create(role);
        return role;
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
        if (role == null) {
            throw new NotFoundException("Role with ID \"" + id + "\" not found");
        }
        final Set<Hero> heroes = new HashSet<>(role.getHeroes());
        heroes.stream().forEach(hero -> { //remove all references from heroes
            hero.removeRole(role);
            heroDao.update(hero);
        });
        roleDao.delete(role);
    }

    @Override
    public List<Hero> findHeroesWithRole(final Long id) {
        notNull(id);
        final Role role = roleDao.findById(id);
        if (role == null) {
            throw new NotFoundException("Role with ID \"" + id + "\" not found");
        }
        return new ArrayList<>(role.getHeroes());
    }
}
