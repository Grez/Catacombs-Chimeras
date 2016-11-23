/**
 * @author Tom Bartoň
 */
package cz.muni.fi.facade;

import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.TroopCreateDTO;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.dto.TroopsAvgExpReportDTO;

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
     * add hero to troop
     * @param troopId ID of troop
     * @param heroId ID of hero
     */
    void addTroopHero(final Long troopId, final Long heroId);

    /**
     * get all heroes associated with troop
     * @param troopId of troopId
     * @return list of troop heroes
     */
    List<HeroDTO> getTroopHeroes(final Long troopId);

    /**
     * report based on all reports, it counts average experience level of heroes in each troop, where
     * if troop has more than two heroes, heroes with highest and lowest experience are
     * not used for counting this average. Also orders troops based on average of each troop
     *
     * @return report based on average experience level of heroes in each troop
     */
    TroopsAvgExpReportDTO countTroopsAverageExperienceReport();
}
