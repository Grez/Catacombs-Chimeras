/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.service;

import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;

import java.util.List;

public interface HeroService {

    /**
     * find hero by ID
     * @param id of hero
     * @return Hero
     * @throws cz.muni.fi.exceptions.NotFoundException when hero not found
     */
    Hero findHeroById(final Long id);

    /**
     * find hero by name
     * @param name of hero
     * @return hero
     * @throws cz.muni.fi.exceptions.NotFoundException when hero not found
     */
    Hero findHeroByName(final String name);

    /**
     * find all heroes
     * @return list of heroes
     */
    List<Hero> findAllHeroes();

    /**
     * create hero
     * @param hero to create
     * @return created hero
     */
    Hero createHero(final Hero hero);

    /**
     * update hero
     * @param hero to update
     */
    void updateHero(final Hero hero);

    /**
     * remove hero by ID
     * @param id of hero
     * @throws cz.muni.fi.exceptions.NotFoundException when hero not found
     */
    void removeHero(final Long id);

    /**
     * add role to hero
     * @param heroId ID of hero
     * @param roleId ID of role
     * @throws cz.muni.fi.exceptions.NotFoundException when hero or role not found
     */
    void addHeroRole(final Long heroId, final Long roleId);

    /**
     * remove hero role,
     * does not delete role entity, only removes association, between two concrete instances of hero and role
     *
     * @param heroId ID of hero
     * @param roleId ID of role
     * @throws cz.muni.fi.exceptions.NotFoundException when hero or role not found
     */
    void removeHeroRole(final Long heroId, final Long roleId);

    /**
     * get all roles associated with hero
     * @param heroId ID of hero
     * @throws cz.muni.fi.exceptions.NotFoundException when hero not found
     */
    List<Role> getHeroRoles(final Long heroId);
}
