/**
 * @author Tom Bartoň
 */
package cz.muni.fi.service;

import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Troop;

import java.util.List;

public interface TroopService {

    /**
     * find troop by ID
     * @param id of troop
     * @return troop
     */
    Troop findTroopById(final Long id);

    /**
     * find troop by name
     * @param name of troop
     * @return troop
     */
    Troop findTroopByName(final String name);

    /**
     * find all troops
     * @return list of all troops
     */
    List<Troop> findAllTroops();

    /**
     * create new troop
     * @param troop to create
     */
    void createTroop(final Troop troop);

    /**
     * update troop
     * @param troop to update
     */
    void updateTroop(final Troop troop);

    /**
     * remove troop specified by id
     * @param id of troop to delete
     */
    void removeTroop(final Long id);

    /**
     * get all heroes associated with troop
     * @param id of troopId
     * @return list of troop heroes
     */
    List<Hero> getTroopHeroes(final Long id);
}
