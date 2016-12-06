package cz.muni.fi.web;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.facade.HeroFacade;
import cz.muni.fi.facade.RoleFacade;
import cz.muni.fi.facade.TroopFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hero")
public class HeroActionBean {

    private final HeroFacade heroFacade;
    private final TroopFacade troopFacade;
    private final RoleFacade roleFacade;

    @Autowired
    public HeroActionBean(final HeroFacade heroFacade, final TroopFacade troopFacade, final RoleFacade roleFacade) {
        this.heroFacade = heroFacade;
        this.troopFacade = troopFacade;
        this.roleFacade = roleFacade;
    }

    @RequestMapping("/list")
    public String hello(Model model) {
        final List<HeroDTO> allHeroes = heroFacade.findAllHeroes();
        List<HeroTroopNamePair> heroAndTroopNames = allHeroes.stream().map(e -> {
            Long troopId = e.getTroopId();
            String troopName = null;
            if (troopId != null) {
                troopName = troopFacade.findTroopById(troopId).getName();
            }
            return new HeroTroopNamePair(e, troopName);
        }).collect(Collectors.toList());
        model.addAttribute("heroes", heroAndTroopNames);
        return "hero/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newCategory(Model model) {
        model.addAttribute("heroCreate", new HeroCreateDTO());
        return "hero/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("heroCreate") HeroCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "hero/new";
        }

        //create hero
        try {
            HeroDTO hero = heroFacade.createHero(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "Hero " + hero.getId() + " was created");
        }
        catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "unable to create hero, name taken?");
        }

        return "redirect:" + uriBuilder.path("/hero/list").toUriString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        heroFacade.removeHero(id);
        redirectAttributes.addFlashAttribute("alert_success", "Hero with ID " + id + " was deleted.");
        return "redirect:" + uriBuilder.path("/hero/list").toUriString();
    }

    @RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
    public String details(@PathVariable long id, Model model) {
        final HeroDTO hero = heroFacade.findHeroById(id);
        if (hero.getTroopId() != null) {
            final TroopDTO troop = troopFacade.findTroopById(hero.getTroopId());
            model.addAttribute("troopName", troop.getName());
        }
        List<RoleDTO> heroRoles = heroFacade.getHeroRoles(hero.getId());
        model.addAttribute("hero", hero);
        model.addAttribute("roles", heroRoles);
        return "hero/details";
    }

    @RequestMapping(value = "/{heroId}/role/remove/{roleId}", method = RequestMethod.POST)
    public String removeRole(@PathVariable("heroId") long heroId, @PathVariable("roleId") long roleId,  Model model,
                             UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        heroFacade.removeHeroRole(heroId, roleId);
        redirectAttributes.addFlashAttribute("alert_success", "Hero role with ID " + roleId + " was removed.");
        return "redirect:" + uriBuilder.path("/hero/details/" + heroId).toUriString();
    }

    @RequestMapping(value = "/{heroId}/role", method = RequestMethod.GET)
    public String role(@PathVariable("heroId") long heroId,  Model model,
                             UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        model.addAttribute("hero", heroFacade.findHeroById(heroId));
        model.addAttribute("availableRoles", roleFacade.findAllRoles());
        return "hero/role";
    }

    @RequestMapping(value = "/{heroId}/role/add", method = RequestMethod.POST)
    public String assignRole(@PathVariable("heroId") long heroId, @RequestParam long roleId, Model model,
                             UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        HeroDTO heroById = heroFacade.findHeroById(heroId);
        if (heroById.getTroopId() != null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Hero \"" + heroById.getName() + "\" is already in troop");
            return "redirect:" + uriBuilder.path("/hero/details/" + heroId).toUriString();
        }
        heroFacade.addHeroRole(heroId, roleId);
        redirectAttributes.addFlashAttribute("alert_success", "Hero role with ID " + roleId + " was removed.");
        return "redirect:" + uriBuilder.path("/hero/details/" + heroId).toUriString();
    }
}
