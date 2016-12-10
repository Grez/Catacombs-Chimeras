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
import cz.muni.fi.service.HeroService;
import cz.muni.fi.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HeroFacadeImpl implements HeroFacade {

    private final HeroService heroService;
    private final MappingService mappingService;

    @Autowired
    public HeroFacadeImpl(final HeroService heroService, final MappingService mappingService) {
        notNull(heroService);
        notNull(mappingService);
        this.heroService = heroService;
        this.mappingService = mappingService;
    }

    @Override
    public HeroDTO findHeroById(final Long heroId) {
        notNull(heroId);
        final Hero hero = heroService.findHeroById(heroId);
        return mappingService.convertToDTO(hero);
    }

    @Override
    public HeroDTO findHeroByName(final String name) {
        notEmpty(name);
        final Hero hero = heroService.findHeroByName(name);
        return mappingService.convertToDTO(hero);
    }

    @Override
    public List<HeroDTO> findAllHeroes() {
        List<Hero> heroes = heroService.findAllHeroes();
        return heroes.stream().map(mappingService::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public HeroDTO createHero(final HeroCreateDTO hero) {
        notNull(hero);
        final Hero heroEntity = mappingService.convertToEntity(hero);
        return mappingService.convertToDTO(heroService.createHero(heroEntity));
    }

    @Override
    public void updateHero(final HeroDTO hero) {
        notNull(hero);
        final Hero heroEntity = mappingService.convertToEntity(hero);
        List<RoleDTO> heroRoles = getHeroRoles(hero.getId());
        heroRoles.forEach(role -> heroEntity.addRole(mappingService.convertToEntity(role)));
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
    public void removeHeroRole(final Long heroId, final Long roleId) {
        notNull(heroId);
        notNull(roleId);
        heroService.removeHeroRole(heroId, roleId);
    }

    @Override
    public List<RoleDTO> getHeroRoles(final Long heroId) {
        notNull(heroId);
        return heroService.getHeroRoles(heroId).stream().map(mappingService::convertToDTO).collect(Collectors.toList());
    }
}
