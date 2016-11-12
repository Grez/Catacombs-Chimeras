package cz.muni.fi.service;

import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.dao.TroopDao;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TroopServiceImpl implements TroopService {

    final TroopDao troopDao;

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
}
