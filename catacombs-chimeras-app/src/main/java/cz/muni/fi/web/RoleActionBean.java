package cz.muni.fi.web;

import cz.muni.fi.dto.RoleCreateDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.facade.RoleFacade;
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
@RequestMapping("/role")
public class RoleActionBean {

    private final RoleFacade roleFacade;

    @Autowired
    public RoleActionBean(final RoleFacade roleFacade) {
        this.roleFacade = roleFacade;
    }

    /**
     * list roles
     *
     */
    @RequestMapping("/list")
    public String listRoles(Model model) {
        final List<RoleDTO> roles = roleFacade.findAllRoles();
        model.addAttribute("roles", roles);
        return "role/list";
    }

    /**
     * delete role
     *
     */
    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        final String name = roleFacade.findRoleById(id).getName();
        roleFacade.removeRole(id);
        redirectAttributes.addFlashAttribute("alert_success", "Role \"" + name + "\" was deleted");
        return "redirect:" + uriBuilder.path("/role/list").toUriString();
    }

    /**
     * create new role form
     *
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newRole(Model model) {
        model.addAttribute("roleCreate", new RoleCreateDTO());
        return "role/new";
    }

    /**
     * create hero
     *
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createRole(@Valid @ModelAttribute("roleCreate") RoleCreateDTO formBean, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "role/new";
        }

        //create role
        try {
            RoleDTO role = roleFacade.createRole(formBean);
            redirectAttributes.addFlashAttribute("alert_success", "Role \"" + role.getName() + "\" was created");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to create role \"" + formBean.getName() + "\", name taken?");
        }

        return "redirect:" + uriBuilder.path("/role/list").toUriString();
    }
}
