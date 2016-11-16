/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.facade;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
import cz.muni.fi.service.HeroService;
import cz.muni.fi.service.TroopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HeroFacadeImpl implements HeroFacade {

    private final HeroService heroService;
    private final TroopService troopService;

    @Autowired
    public HeroFacadeImpl(final HeroService heroService, final TroopService troopService) {
        notNull(heroService);
        notNull(troopService);
        this.heroService = heroService;
        this.troopService = troopService;
    }

    @Override
    public HeroDTO findHeroById(final Long heroId) {
        notNull(heroId);
        final Hero hero = heroService.findHeroById(heroId);
        return convertToDTO(hero);
    }

    @Override
    public HeroDTO findHeroByName(final String name) {
        notEmpty(name);
        final Hero hero = heroService.findHeroByName(name);
        return convertToDTO(hero);
    }

    @Override
    public List<HeroDTO> findAllHeroes() {
        List<Hero> heroes = heroService.findAllHeroes();
        return heroes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void createHero(final HeroCreateDTO hero) {
        notNull(hero);
        final Hero heroEntity = convertToEntity(hero);
        heroService.createHero(heroEntity);
    }

    @Override
    public void updateHero(final HeroDTO hero) {
        notNull(hero);
        final Hero heroEntity = convertToEntity(hero);
        heroService.updateHero(heroEntity);
    }

    @Override
    public void removeHero(final Long id) {
        notNull(id);
        heroService.removeHero(id);
    }

    @Override
    public void addHeroRole(final Long heroId, final Long roleId) {
        notNull(heroId);
        notNull(roleId);
        heroService.addHeroRole(heroId, roleId);
    }

    @Override
    public List<RoleDTO> getHeroRoles(final Long heroId) {
        notNull(heroId);
        return heroService.getHeroRoles(heroId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private Hero convertToEntity(final HeroDTO heroDTO) {
        final Hero hero = new Hero();
        hero.setId(heroDTO.getId());
        hero.setExperience(heroDTO.getExperience());
        hero.setName(heroDTO.getName());
        hero.setTroop((heroDTO.getTroopId() != null) ? troopService.findTroopById(heroDTO.getTroopId()) : null);
        return hero;
    }

    private Hero convertToEntity(final HeroCreateDTO heroCreateDTO) {
        final Hero hero = new Hero();
        hero.setExperience(heroCreateDTO.getExperience());
        hero.setName(heroCreateDTO.getName());
        hero.setTroop((heroCreateDTO.getTroopId() != null) ? troopService.findTroopById(heroCreateDTO.getTroopId()) : null);
        return hero;
    }

    private HeroDTO convertToDTO(final Hero hero) {
        return new HeroDTO(
                hero.getId(),
                hero.getName(),
                hero.getExperience(),
                (hero.getTroop() != null) ? hero.getTroop().getId() : null);
    }

    private RoleDTO convertToDTO(final Role role) {
        return new RoleDTO(role.getId(), role.getName(), role.getDescription());
    }
}
