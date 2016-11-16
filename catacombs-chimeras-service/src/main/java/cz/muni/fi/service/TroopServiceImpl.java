/**
 * @author Tom Bartoň
 */
package cz.muni.fi.service;

import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.dao.TroopDao;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Troop troop = troopDao.findById(id);
        if (troop == null) {
            throw new NotFoundException("Troop with ID " + id + " not found");
        }
        return troop;
    }

    @Override
    public Troop findTroopByName(final String name) {
        notNull(name);
        Troop troop = troopDao.findByName(name);
        if (troop == null) {
            throw new NotFoundException("Troop with name " + name + " not found");
        }
        return troop;
    }

    @Override
    public List<Troop> findAllTroops() {
        return troopDao.findAll();
    }

    @Override
    public void createTroop(final Troop troop) {
        notNull(troop);
        troopDao.create(troop);
    }

    @Override
    public void updateTroop(final Troop troop) {
        notNull(troop);
        troopDao.update(troop);
    }

    @Override
    public void removeTroop(final Long id) {
        Troop troop = troopDao.findById(id);
        if (troop == null) {
            throw new NotFoundException("Troop with ID " + id + " not found");
        }
        troopDao.delete(troop);
    }

    @Override
    public List<Hero> getTroopHeroes(final Long id) {
        notNull(id);
        final Troop troop = findTroopById(id);
        if (troop == null) {
            throw new NotFoundException("Troop with ID " + id + " not found");
        }
        return new ArrayList<Hero>(troop.getHeroes());
    }
}
