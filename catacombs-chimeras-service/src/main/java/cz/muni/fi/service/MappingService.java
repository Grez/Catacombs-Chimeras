package cz.muni.fi.service;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleCreateDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.dto.TroopCreateDTO;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
import cz.muni.fi.entity.Troop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    private final TroopService troopService;

    @Autowired
    public MappingService(final TroopService troopService){
        this.troopService = troopService;
    }

    public Hero convertToEntity(final HeroDTO heroDTO) {
        final Hero hero = new Hero();
        hero.setId(heroDTO.getId());
        hero.setExperience(heroDTO.getExperience());
        hero.setName(heroDTO.getName());
        hero.setTroop((heroDTO.getTroopId() != null) ? troopService.findTroopById(heroDTO.getTroopId()) : null);
        return hero;
    }

    public Hero convertToEntity(final HeroCreateDTO heroCreateDTO) {
        final Hero hero = new Hero();
        hero.setExperience(heroCreateDTO.getExperience());
        hero.setName(heroCreateDTO.getName());
        hero.setTroop((heroCreateDTO.getTroopId() != null) ? troopService.findTroopById(heroCreateDTO.getTroopId()) : null);
        return hero;
    }

    public HeroDTO convertToDTO(final Hero hero) {
        return new HeroDTO(
                hero.getId(),
                hero.getName(),
                hero.getExperience(),
                (hero.getTroop() != null) ? hero.getTroop().getId() : null
        );
    }

    public Role convertToEntity(final RoleDTO roleDTO) {
        final Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        return role;
    }

    public Role convertToEntity(final RoleCreateDTO roleCreateDTO) {
        final Role role = new Role();
        role.setName(roleCreateDTO.getName());
        role.setDescription(roleCreateDTO.getDescription());
        return role;
    }

    public RoleDTO convertToDTO(final Role role) {
        return new RoleDTO(role.getId(), role.getName(), role.getDescription());
    }

    public Troop convertToEntity(final TroopDTO troopDTO) {
        final Troop troop = new Troop();
        troop.setId(troopDTO.getId());
        troop.setName(troopDTO.getName());
        troop.setMission(troopDTO.getMission());
        troop.setAmountOfMoney(troopDTO.getAmountOfMoney());
        return troop;
    }

    public Troop convertToEntity(final TroopCreateDTO troopCreateDTO) {
        final Troop troop = new Troop();
        troop.setMission(troopCreateDTO.getMission());
        troop.setName(troopCreateDTO.getName());
        troop.setAmountOfMoney(troopCreateDTO.getAmountOfMoney());
        return troop;
    }

    public TroopDTO convertToDTO(final Troop troop) {
        return new TroopDTO(
                troop.getId(),
                troop.getName(),
                troop.getMission(),
                troop.getAmountOfMoney()
        );
    }
}
