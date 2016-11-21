/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.facade;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleDTO;

import java.util.List;

public interface HeroFacade {

    /**
     * find hero by id
     * @param heroId ID of hero
     * @return hero
     */
    HeroDTO findHeroById(final Long heroId);

    /**
     * find hero by name
     * @param name of hero
     * @return hero
     */
    HeroDTO findHeroByName(final String name);

    /**
     * find all heroes
     * @return list of all heroes
     */
    List<HeroDTO> findAllHeroes();

    /**
     * create new hero
     * @param hero to create
     * @return created hero
     */
    HeroDTO createHero(final HeroCreateDTO hero);

    /**
     * update hero
     * @param hero to update
     */
    void updateHero(final HeroDTO hero);

    /**
     * remove hero specified by id
     * @param id of hero to delete
     */
    void removeHero(final Long id);

    /**
     * add role to hero
     * @param heroId ID of hero
     * @param roleId ID of role
     */
    void addHeroRole(final Long heroId, final Long roleId);

    /**
     * get all roles associated with hero
     * @param heroId  of hero
     * @return list of hero roles
     */
    List<RoleDTO> getHeroRoles(final Long heroId);
}
