/**
 * @author Tom Bartoň
 */
package cz.muni.fi.facade;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.TroopCreateDTO;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.dto.TroopsAvgExpReportDTO;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.exceptions.NotFoundException;
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

    @Autowired
    public TroopFacadeImpl(final TroopService troopService) {
        notNull(troopService);
        this.troopService = troopService;
    }

    @Override
    public TroopDTO findTroopById(final Long troopId) {
        notNull(troopId);
        final Troop troop = troopService.findTroopById(troopId);
        return convertToDTO(troop);
    }

    @Override
    public TroopDTO findTroopByName(final String name) {
        notEmpty(name);
        final Troop troop = troopService.findTroopByName(name);
        return convertToDTO(troop);
    }

    @Override
    public List<TroopDTO> findAllTroops() {
        List<Troop> troops = troopService.findAllTroops();
        return troops.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TroopDTO createTroop(final TroopCreateDTO troop) {
        notNull(troop);
        final Troop troopEntity = convertToEntity(troop);
        return convertToDTO(troopService.createTroop(troopEntity));
    }

    @Override
    public void updateTroop(final TroopDTO troop) {
        notNull(troop);
        final Troop troopEntity = convertToEntity(troop);
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
        return troopService.getTroopHeroes(troopId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public TroopsAvgExpReportDTO countTroopsAverageExperienceReport() {
        return troopService.countTroopsAverageExperienceReport();
    }

    private Troop convertToEntity(final TroopDTO troopDTO) {
        final Troop troop = new Troop();
        troop.setId(troopDTO.getId());
        troop.setName(troopDTO.getName());
        troop.setMission(troopDTO.getMission());
        troop.setAmountOfMoney(troopDTO.getAmountOfMoney());
        return troop;
    }

    private Troop convertToEntity(final TroopCreateDTO troopCreateDTO) {
        final Troop troop = new Troop();
        troop.setMission(troopCreateDTO.getMission());
        troop.setName(troopCreateDTO.getName());
        troop.setAmountOfMoney(troopCreateDTO.getAmountOfMoney());
        return troop;
    }

    private TroopDTO convertToDTO(final Troop troop) {
        return new TroopDTO(
                troop.getId(),
                troop.getName(),
                troop.getMission(),
                troop.getAmountOfMoney()
        );
    }

    private HeroDTO convertToDTO(final Hero hero) {
        return new HeroDTO(
                hero.getId(),
                hero.getName(),
                hero.getExperience(),
                (hero.getTroop() != null) ? hero.getTroop().getId() : null
        );
    }
}
