/**
 * @author Tom Bartoň
 */
package cz.muni.fi.facade;

import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.TroopCreateDTO;
import cz.muni.fi.dto.TroopDTO;

import java.util.List;

public interface TroopFacade {
    /**
     * find troop by id
     * @param id ID of troop
     * @return troop
     */
    TroopDTO findTroopById(final Long id);

    /**
     * find troop by name
     * @param name of troop
     * @return troop
     */
    TroopDTO findTroopByName(final String name);

    /**
     * find all troops
     * @return list of all troops
     */
    List<TroopDTO> findAllTroops();

    /**
     * create new troop
     * @param troop to create
     * @return created troop
     */
    TroopDTO createTroop(final TroopCreateDTO troop);

    /**
     * update troop
     * @param troop to update
     */
    void updateTroop(final TroopDTO troop);

    /**
     * remove troop specified by id
     * @param troopId of troop to delete
     */
    void removeTroop(final Long troopId);

    /**
     * get all heroes associated with troop
     * @param troopId of troopId
     * @return list of troop heroes
     */
    List<HeroDTO> getTroopHeroes(final Long troopId);
}
