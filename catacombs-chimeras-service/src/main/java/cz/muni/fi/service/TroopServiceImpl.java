/**
 * @author Tom Bartoň
 */
package cz.muni.fi.service;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.dao.HeroDao;
import cz.muni.fi.dao.TroopDao;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.dto.TroopWealthDTO;
import cz.muni.fi.dto.TroopWealthItemDTO;
import cz.muni.fi.dto.TroopsAvgExpReportDTO;
import cz.muni.fi.dto.TroopsAvgExpReportItemDTO;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TroopServiceImpl implements TroopService {

    private final TroopDao troopDao;

    private final HeroDao heroDao;

    @Autowired
    public TroopServiceImpl(final TroopDao troopDao, final HeroDao heroDao) {
        notNull(troopDao);
        this.troopDao = troopDao;
        this.heroDao = heroDao;
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
        notEmpty(name);
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
    public Troop createTroop(final Troop troop) {
        notNull(troop);
        troopDao.create(troop);
        return troop;
    }

    @Override
    public void updateTroop(final Troop troop) {
        notNull(troop);
        troopDao.update(troop);
    }

    @Override
    public void removeTroop(final Long id) {
        notNull(id);
        Troop troop = troopDao.findById(id);
        if (troop == null) {
            throw new NotFoundException("Troop with ID " + id + " not found");
        }
        Set<Hero> heroes = new HashSet<>(troop.getHeroes());
        heroes.stream().forEach(hero -> { //remove all references from heroes
            troop.removeHero(hero);
            heroDao.update(hero);
        });
        troopDao.delete(troop);
    }

    @Override
    public void addTroopHero(final Long troopId, final Long heroId) {
        notNull(troopId);
        notNull(heroId);

        final Troop troop = findTroopById(troopId);
        if (troop == null) {
            throw new NotFoundException("Troop with ID " + troopId + " not found");
        }

        final Hero hero = heroDao.findById(heroId);
        if (hero == null) {
            throw new NotFoundException("Hero with ID " + heroId + " not found");
        }

        troop.addHero(hero);
        troopDao.update(troop);
        heroDao.update(hero);
    }

    @Override
    public List<Hero> getTroopHeroes(final Long id) {
        notNull(id);
        final Troop troop = findTroopById(id);
        if (troop == null) {
            throw new NotFoundException("Troop with ID " + id + " not found");
        }
        return new ArrayList<>(troop.getHeroes());
    }

    @Override
    public TroopsAvgExpReportDTO countTroopsAverageExperienceReport() {
        List<TroopsAvgExpReportItemDTO> listOfReportItems = new ArrayList<>();
        for (Troop troop : findAllTroops()) {
            final List<Hero> heroes = new ArrayList<>(troop.getHeroes());
            if (heroes.size() == 0) {
                listOfReportItems.add(new TroopsAvgExpReportItemDTO(convertToDTO(troop), 0.0));
                continue;
            }
            if (heroes.size() > 2) { //if troop has more than two heroes, remove hero with highest and lowest experience
                Collections.sort(heroes, (x, y) -> x.getExperience().compareTo(y.getExperience()));
                heroes.remove(heroes.size() - 1);
                heroes.remove(0);
            }
            final double average = heroes.stream().mapToLong(Hero::getExperience).average().getAsDouble();
            final TroopsAvgExpReportItemDTO troopReportItemDTO = new TroopsAvgExpReportItemDTO(convertToDTO(troop), average);
            listOfReportItems.add(troopReportItemDTO);
        }
        Collections.sort(listOfReportItems, (x, y) -> x.getAverage().compareTo(y.getAverage()));
        return new TroopsAvgExpReportDTO(listOfReportItems,
                (listOfReportItems.isEmpty()) ? 0 : listOfReportItems.get(listOfReportItems.size() - 1).getAverage(),
                (listOfReportItems.isEmpty()) ? 0 : listOfReportItems.get(0).getAverage());
    }
    
    @Override
    public TroopWealthDTO getMoneyPerHeroList() {
        List<TroopWealthItemDTO> troops = new ArrayList<>();
        for(Troop troop :findAllTroops()) {
            if(troop.getHeroes().isEmpty()) {
                troops.add(new TroopWealthItemDTO(convertToDTO(troop), new Double(troop.getAmountOfMoney())));
            } else {
                Double moneyPerHero = new Double(troop.getAmountOfMoney().doubleValue() / troop.getHeroes().size());
                troops.add(new TroopWealthItemDTO(convertToDTO(troop), moneyPerHero));
            }
        }
        Collections.sort(troops, (x, y) -> x.getMoneyPerHero().compareTo(y.getMoneyPerHero()));
        return new TroopWealthDTO(troops);
    }

    private TroopDTO convertToDTO(final Troop troop) {
        return new TroopDTO(
                troop.getId(),
                troop.getName(),
                troop.getMission(),
                troop.getAmountOfMoney()
        );
    }
}
