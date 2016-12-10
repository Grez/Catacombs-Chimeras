/**
 * @author Tom Bartoň
 */
package cz.muni.fi.facade;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.TroopCreateDTO;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.dto.TroopWealthDTO;
import cz.muni.fi.dto.TroopsAvgExpReportDTO;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.service.MappingService;
import cz.muni.fi.service.TroopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TroopFacadeImpl implements TroopFacade {

    private final TroopService troopService;
    private final MappingService mappingService;

    @Autowired
    public TroopFacadeImpl(final TroopService troopService, final MappingService mappingService) {
        notNull(troopService);
        notNull(mappingService);
        this.troopService = troopService;
        this.mappingService = mappingService;
    }

    @Override
    public TroopDTO findTroopById(final Long troopId) {
        notNull(troopId);
        final Troop troop = troopService.findTroopById(troopId);
        return mappingService.convertToDTO(troop);
    }

    @Override
    public TroopDTO findTroopByName(final String name) {
        notEmpty(name);
        final Troop troop = troopService.findTroopByName(name);
        return mappingService.convertToDTO(troop);
    }

    @Override
    public List<TroopDTO> findAllTroops() {
        List<Troop> troops = troopService.findAllTroops();
        return troops.stream().map(mappingService::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TroopDTO createTroop(final TroopCreateDTO troop) {
        notNull(troop);
        final Troop troopEntity = mappingService.convertToEntity(troop);
        return mappingService.convertToDTO(troopService.createTroop(troopEntity));
    }

    @Override
    public void updateTroop(final TroopDTO troop) {
        notNull(troop);
        final Troop troopEntity = mappingService.convertToEntity(troop);
        List<HeroDTO> heroes = getTroopHeroes(troopEntity.getId());
        heroes.forEach(hero -> troopEntity.addHero(mappingService.convertToEntity(hero)));
        troopService.updateTroop(troopEntity);
    }

    @Override
    public void removeTroop(final Long id) {
        notNull(id);
        troopService.removeTroop(id);
    }

    @Override
    public void addTroopHero(final Long troopId, final Long heroId) {
        notNull(troopId);
        notNull(heroId);
        troopService.addTroopHero(troopId, heroId);
    }

    @Override
    public List<HeroDTO> getTroopHeroes(final Long troopId) {
        notNull(troopId);
        return troopService.getTroopHeroes(troopId).stream().map(mappingService::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TroopsAvgExpReportDTO countTroopsAverageExperienceReport() {
        return troopService.countTroopsAverageExperienceReport();
    }

    @Override
    public TroopWealthDTO getMoneyPerHeroList() {
        return troopService.getMoneyPerHeroList();
    }


}
