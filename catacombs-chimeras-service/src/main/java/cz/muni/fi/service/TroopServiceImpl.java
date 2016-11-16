/**
 * @author Tom Bartoň
 */
package cz.muni.fi.service;

import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.dao.TroopDao;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TroopServiceImpl implements TroopService {

    private final TroopDao troopDao;

    @Autowired
    public TroopServiceImpl(final TroopDao troopDao) {
        notNull(troopDao);
        this.troopDao = troopDao;
    }

    @Override
    public Troop findTroopById(final Long id) {
        notNull(id);
        Troop byId = troopDao.findById(id);
        if (byId == null) {
            throw new NotFoundException("Troop with ID: " + id + " not found");
        }
        return byId;
    }

    @Override
    public Troop findTroopByName(final String name) {
        //TODO
        return null;
    }

    @Override
    public List<Troop> findAllTroops() {
        //TODO
        return null;
    }

    @Override
    public void createTroop(final Troop troop) {
        //TODO
    }

    @Override
    public void updateTroop(final Troop troop) {
        //TODO
    }

    @Override
    public void removeTroop(final Long id) {
        //TODO
    }

    @Override
    public List<TroopDTO> getTroopHeroes(final Long troopId) {
        //TODO
        return null;
    }
}
