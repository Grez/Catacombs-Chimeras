package cz.muni.fi.web;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.TroopCreateDTO;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.facade.HeroFacade;
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

@Controller
@RequestMapping("/troop")
public class TroopActionBean {

    private final TroopFacade troopFacade;
    private final HeroFacade heroFacade;

    @Autowired
    public TroopActionBean(final TroopFacade troopFacade, final HeroFacade heroFacade) {
        this.troopFacade = troopFacade;
        this.heroFacade = heroFacade;
    }

    /**
     * list troops
     */
    @RequestMapping("/list")
    public String listRoles(Model model) {
        final List<TroopDTO> troops = troopFacade.findAllTroops();
        model.addAttribute("troops", troops);
        return "troop/list";
    }

    /**
     * delete troop
     */
    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        final String name = troopFacade.findTroopById(id).getName();
        troopFacade.removeTroop(id);
        redirectAttributes.addFlashAttribute("alert_success", "Troop \"" + name + "\" was deleted");
        return "redirect:" + uriBuilder.path("/troop/list").toUriString();
    }

    /**
     * create new troop form
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTroop(Model model) {
        model.addAttribute("troopCreate", new TroopCreateDTO());
        return "troop/new";
    }

    /**
     * create troop
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createTroop(@Valid @ModelAttribute("troopCreate") TroopCreateDTO formBean, BindingResult bindingResult,
                              Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "troop/new";
        }

        //create role
        try {
            TroopDTO role = troopFacade.createTroop(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "Troop \"" + role.getName() + "\" was created");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to create troop \"" + formBean.getName() + "\", name taken?");
        }

        return "redirect:" + uriBuilder.path("/troop/list").toUriString();
    }

    /**
     * details of troop containing heroes
     */
    @RequestMapping(value = "/{id}/details", method = RequestMethod.GET)
    public String details(@PathVariable long id, Model model) {
        final TroopDTO troop = troopFacade.findTroopById(id);
        List<HeroDTO> troopHeroes = troopFacade.getTroopHeroes(id);
        model.addAttribute("troop", troop);
        model.addAttribute("troopHeroes", troopHeroes);
        return "troop/details";
    }


    /**
     * remove hero from troop
     */
    @RequestMapping(value = "/{troopId}/hero/remove/{heroId}", method = RequestMethod.POST)
    public String removeHero(@PathVariable("troopId") long troopId, @PathVariable("heroId") long heroId,
                             UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        final HeroDTO hero = heroFacade.findHeroById(heroId);
        hero.setTroopId(null);
        heroFacade.updateHero(hero);
        redirectAttributes.addFlashAttribute("alert_success", "Hero \"" + hero.getName() + "\" was removed from troop");
        return "redirect:" + uriBuilder.path("/troop/" + troopId + "/details").toUriString();
    }

    /**
     * create update hero form
     */
    @RequestMapping(value = "/update/new/{troopId}", method = RequestMethod.GET)
    public String updateTroop(@PathVariable("troopId") long troopId, Model model) {
        TroopDTO troop = troopFacade.findTroopById(troopId);
        TroopCreateDTO troopCreateDTO = new TroopCreateDTO(); // bypass that we dont have default constructor in TroopDTO
        troopCreateDTO.setName(troop.getName());
        troopCreateDTO.setAmountOfMoney(troop.getAmountOfMoney());
        troopCreateDTO.setMission(troop.getMission());
        model.addAttribute("troop", troopCreateDTO);
        model.addAttribute("troopId", troopId);
        return "troop/update";
    }

    /**
     * update hero
     */
    @RequestMapping(value = "/update/{troopId}", method = RequestMethod.POST)
    public String updateTroop(@PathVariable("troopId") long troopId,  @Valid @ModelAttribute("troop") TroopCreateDTO formBean,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder) {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "troop/update";
        }

        //update hero
        try {
            troopFacade.updateTroop(new TroopDTO(troopId, formBean.getName(), formBean.getMission(), formBean.getAmountOfMoney()));
            redirectAttributes.addFlashAttribute("alert_success", "Troop \"" + formBean.getName() + "\" was updated");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to update troop \"" + formBean.getName() + "\", name taken?");
        }

        return "redirect:" + uriBuilder.path("/troop/list").toUriString();
    }
}
