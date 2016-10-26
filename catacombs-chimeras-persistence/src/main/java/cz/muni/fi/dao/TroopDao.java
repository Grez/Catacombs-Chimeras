/**
 *
 * @author David Osička
 */
package cz.muni.fi.dao;

import cz.muni.fi.entity.Troop;

import java.util.List;


public interface TroopDao {
    /**
     * Creates new entity of Troop in data layer
     * @param instance is saved to data layer
     */
    void create(final Troop instance);
    
    /**
     * Updates already existing entity of Troop
     * @param instance is updated in data layer
     */
    void update(final Troop instance);
    
    /**
     * Deletes already existing entity of Troop
     * @param instance is deleted from data layer
     */
    void delete(final Troop instance);
    
    /**
     * Finds entity of Troop by its ID
     * @param id ID to find the entity
     * @return entity(instance) of Troop found by this method
     */
    Troop findById(final Long id);
    
    /**
     * Finds entity of Troop by its name
     * @param name to find the entity
     * @return entity(instance) of Troop found by this method
     */
    Troop findByName(final String name);
    
    /**
     * Finds all entities of Troop
     * @return list of all troops
     */
    List<Troop> findAll();
}
