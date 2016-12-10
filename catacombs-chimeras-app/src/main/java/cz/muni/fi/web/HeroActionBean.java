package cz.muni.fi.web;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleCreateDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.dto.TroopCreateDTO;
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


    /**
     * list heroes
     */
    @RequestMapping("/list")
    public String listHeroes(Model model) {
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


    /**
     * create new hero form
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newHero(Model model) {
        model.addAttribute("heroCreate", new HeroCreateDTO());
        return "hero/new";
    }


    /**
     * create hero
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createHero(@Valid @ModelAttribute("heroCreate") HeroCreateDTO formBean, BindingResult bindingResult,
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
            redirectAttributes.addFlashAttribute("alert_success", "Hero \"" + hero.getName() + "\" was created");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to create hero \"" + formBean.getName() + "\", name taken?");
        }

        return "redirect:" + uriBuilder.path("/hero/list").toUriString();
    }

    /**
     * delete hero
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        final String name = heroFacade.findHeroById(id).getName();
        heroFacade.removeHero(id);
        redirectAttributes.addFlashAttribute("alert_success", "Hero \"" + name + "\" was deleted");
        return "redirect:" + uriBuilder.path("/hero/list").toUriString();
    }


    /**
     * details of hero containing roles
     */
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


    /**
     * remove role
     */
    @RequestMapping(value = "/{heroId}/role/remove/{roleId}", method = RequestMethod.POST)
    public String removeRole(@PathVariable("heroId") long heroId, @PathVariable("roleId") long roleId,
                             UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        final String roleName = roleFacade.findRoleById(roleId).getName();
        heroFacade.removeHeroRole(heroId, roleId);
        redirectAttributes.addFlashAttribute("alert_success", "Hero no longer has role \"" + roleName + "\"");
        return "redirect:" + uriBuilder.path("/hero/details/" + heroId).toUriString();
    }


    /**
     * form for role selection
     */
    @RequestMapping(value = "/{heroId}/role", method = RequestMethod.GET)
    public String role(@PathVariable("heroId") long heroId, Model model) {
        model.addAttribute("id", heroId);
        model.addAttribute("role", new RoleCreateDTO());
        model.addAttribute("availableRoles", roleFacade.findAllRoles());
        return "hero/role";
    }


    /**
     * assign role
     */
    @RequestMapping(value = "/{heroId}/role/add", method = RequestMethod.POST)
    public String assignRole(@PathVariable("heroId") long heroId, @ModelAttribute("role") RoleCreateDTO role,
                             UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        Long roleId = roleFacade.findRoleByName(role.getName()).getId();
        heroFacade.addHeroRole(heroId, roleId);
        redirectAttributes.addFlashAttribute("alert_success", "Hero was given a new role \"" + role.getName() + "\"");
        return "redirect:" + uriBuilder.path("/hero/details/" + heroId).toUriString();
    }

    /**
     * Removes selected hero from troop
     *
     * @param id of hero to be removed from troop
     * @return redirects to list of heroes after deleting hero
     */
    @RequestMapping(value = "/{id}/troop/remove", method = RequestMethod.POST)
    public String remove(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        final HeroDTO hero = heroFacade.findHeroById(id);
        if (hero.getTroopId() == null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Hero \"" + hero.getName() + "\" is not in a troop");
            return "redirect:" + uriBuilder.path("/hero/details/" + id).toUriString();
        }
        hero.setTroopId(null);
        heroFacade.updateHero(hero);
        redirectAttributes.addFlashAttribute("alert_success", "Hero \"" + hero.getName() + "\" was removed from the troop");
        return "redirect:" + uriBuilder.path("/hero/details/" + id).toUriString();
    }

    /**
     * form for role selection
     */
    @RequestMapping(value = "/{heroId}/troop", method = RequestMethod.GET)
    public String troop(@PathVariable("heroId") long heroId, Model model) {
        model.addAttribute("id", heroId);
        model.addAttribute("troop", new TroopCreateDTO());
        model.addAttribute("availableTroops", troopFacade.findAllTroops());
        return "hero/troop";
    }

    /**
     * joinTroop role
     */
    @RequestMapping(value = "/{heroId}/troop/add", method = RequestMethod.POST)
    public String joinTroop(@PathVariable("heroId") long heroId, @ModelAttribute("troop") TroopCreateDTO troop,
                            UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        final Long troopId = troopFacade.findTroopByName(troop.getName()).getId();
        troopFacade.addTroopHero(troopId, heroId);
        redirectAttributes.addFlashAttribute("alert_success", "Hero joined troop \"" + troop.getName() + "\"");
        return "redirect:" + uriBuilder.path("/hero/details/" + heroId).toUriString();
    }

    /**
     * create update hero form
     */
    @RequestMapping(value = "/update/new/{heroId}", method = RequestMethod.GET)
    public String updateHero(@PathVariable("heroId") long heroId, Model model) {
        HeroDTO hero = heroFacade.findHeroById(heroId);
        HeroCreateDTO heroCreateDTO = new HeroCreateDTO(); // bypass that we dont have default constructor in HeroDTO
        heroCreateDTO.setExperience(hero.getExperience());
        heroCreateDTO.setName(hero.getName());
        heroCreateDTO.setTroopId(hero.getTroopId());
        model.addAttribute("hero", heroCreateDTO);
        model.addAttribute("heroId", heroId);
        return "hero/update";
    }

    /**
     * update hero
     */
    @RequestMapping(value = "/update/{heroId}", method = RequestMethod.POST)
    public String updateHero(@PathVariable("heroId") long heroId,  @Valid @ModelAttribute("hero") HeroCreateDTO formBean,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder) {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "hero/update";
        }

        //update hero
        try {
            heroFacade.updateHero(new HeroDTO(heroId, formBean.getName(), formBean.getExperience(), formBean.getTroopId()));
            redirectAttributes.addFlashAttribute("alert_success", "Hero \"" + formBean.getName() + "\" was updated");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to update hero \"" + formBean.getName() + "\", name taken?");
        }

        return "redirect:" + uriBuilder.path("/hero/list").toUriString();
    }
}
