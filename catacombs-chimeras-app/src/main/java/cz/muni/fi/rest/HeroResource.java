package cz.muni.fi.rest;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.exceptions.NotFoundException;
import cz.muni.fi.exceptions.NotFoundRestException;
import cz.muni.fi.facade.HeroFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("/pa165/rest/heroes")
public class HeroResource {

    private HeroFacade heroFacade;

    @Autowired
    HeroResource(final HeroFacade heroFacade) {
        this.heroFacade = heroFacade;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<HeroDTO> getAllHeroes() {
        return heroFacade.findAllHeroes();
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<HeroDTO> createHero(@RequestBody HeroCreateDTO hero) {
        HeroDTO createdHero = heroFacade.createHero(hero);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdHero.getId()).toUri())
                .body(createdHero);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    ResponseEntity<HeroDTO> getHero(@PathVariable("id") final Long id) {
        try {
            HeroDTO hero = heroFacade.findHeroById(id);
            return ResponseEntity.ok()
                    .body(hero);
        } catch (NotFoundException ex) {
            throw new NotFoundRestException(ex);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity deleteHero(@PathVariable("id") final Long id) {
        try {
            heroFacade.removeHero(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException ex) {
            throw new NotFoundRestException(ex);
        }
    }
}
