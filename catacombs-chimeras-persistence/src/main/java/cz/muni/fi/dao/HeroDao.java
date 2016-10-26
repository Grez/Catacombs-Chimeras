/**
 * @author Tom Bartoň
 */
package cz.muni.fi.dao;

import cz.muni.fi.entity.Hero;

import java.util.List;

public interface HeroDao {
    /**
     * creates new hero
     * @param hero
     */
    void create(final Hero hero);

    /**
     * delete hero
     * @param hero
     */
    void delete(final Hero hero);

    /**
     * find hero by ID
     * @param id
     * @return hero if found, else null
     */
    Hero findById(final Long id);

    /**
     * find hero by name
     * @param name
     * @return hero if found, else null
     */
    Hero findByName(final String name);

    /**
     * find all heros
     * @return list of all heros
     */
    List<Hero> findAll();

    /**
     * update hero
     * @param hero
     */
    void update(final Hero hero);
}
