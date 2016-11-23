/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.service;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.dao.HeroDao;
import cz.muni.fi.dao.RoleDao;
import cz.muni.fi.dao.TroopDao;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroDao heroDao;
    private final RoleDao roleDao;
    private final TroopDao troopDao;

    @Autowired
    public HeroServiceImpl(final HeroDao heroDao, final RoleDao roleDao, final TroopDao troopDao) {
        notNull(heroDao);
        notNull(roleDao);
        notNull(troopDao);
        this.heroDao = heroDao;
        this.roleDao = roleDao;
        this.troopDao = troopDao;
    }

    @Override
    public Hero findHeroById(final Long id) {
        notNull(id);
        Hero hero = heroDao.findById(id);
        if (hero == null) {
            throw new NotFoundException("Hero with ID: " + id + " not found");
        }
        return hero;
    }

    @Override
    public Hero findHeroByName(final String name) {
        notEmpty(name);
        Hero hero = heroDao.findByName(name);
        if (hero == null) {
            throw new NotFoundException("Hero with name: " + name + " not found");
        }
        return hero;
    }

    @Override
    public List<Hero> findAllHeroes() {
        return heroDao.findAll();
    }

    @Override
    public Hero createHero(final Hero hero) {
        notNull(hero);
        heroDao.create(hero);
        return hero;
    }

    @Override
    public void updateHero(final Hero hero) {
        notNull(hero);
        heroDao.update(hero);
    }

    @Override
    public void removeHero(final Long id) {
        notNull(id);
        Hero hero = heroDao.findById(id);
        if (hero == null) {
            throw new NotFoundException("Hero with ID: " + id + " not found");
        }
        heroDao.delete(hero);
    }

    @Override
    public void addHeroRole(final Long heroId, final Long roleId) {
        notNull(heroId);
        notNull(roleId);
        final Hero hero = heroDao.findById(heroId);
        if (hero == null) {
            throw new NotFoundException("Hero with ID " + heroId + " not found");
        }
        final Role role = roleDao.findById(roleId);
        if (role == null) {
            throw new NotFoundException("Role with ID: " + roleId + " not found");
        }
        hero.addRole(role);
        heroDao.update(hero);
        roleDao.update(role);
    }

    @Override
    public List<Role> getHeroRoles(final Long heroId) {
        notNull(heroId);
        final Hero hero = heroDao.findById(heroId);
        if (hero == null) {
            throw new NotFoundException("Hero with ID: " + heroId + " not found");
        }
        return new ArrayList<>(hero.getRoles());
    }
}
