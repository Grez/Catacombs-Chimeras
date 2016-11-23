/**
 * @author Tom Bartoň
 */
package cz.muni.fi.service;

import cz.muni.fi.dto.TroopsAvgExpReportDTO;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Troop;

import java.util.List;

public interface TroopService {

    /**
     * find troop by ID
     * @param id of troop
     * @return troop
     * @throws cz.muni.fi.exceptions.NotFoundException when troop with id not found
     */
    Troop findTroopById(final Long id);

    /**
     * find troop by name
     * @param name of troop
     * @return troop
     * @throws cz.muni.fi.exceptions.NotFoundException when troop with name not found
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
     * @return created troop
     */
    Troop createTroop(final Troop troop);

    /**
     * update troop
     * @param troop to update
     */
    void updateTroop(final Troop troop);

    /**
     * remove troop specified by id
     * @param id of troop to delete
     * @throws cz.muni.fi.exceptions.NotFoundException when troop with id not found
     */
    void removeTroop(final Long id);

    /**
     * add hero to troop
     * @param troopId ID of troop
     * @param heroId ID of hero
     * @throws cz.muni.fi.exceptions.NotFoundException when troop or hero not found
     */
    void addTroopHero(final Long troopId, final Long heroId);

    /**
     * get all heroes associated with troop
     * @param id of troopId
     * @return list of troop heroes
     * @throws cz.muni.fi.exceptions.NotFoundException when troop with id not found
     */
    List<Hero> getTroopHeroes(final Long id);


    /**
     * report based on all reports, it counts average experience level of heroes in each troop, where
     * if troop has more than two heroes, heroes with highest and lowest experience are
     * not used for counting this average. Also orders troops based on average of each troop
     *
     * @return report based on average experience level of heroes in each troop
     */
    TroopsAvgExpReportDTO countTroopsAverageExperienceReport();
}
