package cz.muni.fi.service;

import cz.muni.fi.entity.Troop;

public interface TroopService {

    /**
     * find troop by ID
     * @param id of troop
     * @return troop
     */
    Troop findTroopById(final Long id);
}
