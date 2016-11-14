package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.RoleService;
import com.teamtreehouse.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    // Show all the roles
    @RequestMapping("/roles")
    public String showRoles(Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("layoutAction", "list");
        return "roles/index";
    }

    // Show a role for editing and list the other roles
    @RequestMapping("/roles/{roleId}/edit")
    public String showEditRoles(@PathVariable Long roleId, Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("layoutAction", "edit");
        if (!model.containsAttribute("editRole")) {
            model.addAttribute("editRole", roleService.findRoleById(roleId));
        }
        return "roles/index";
    }

    // Edit a role
    @RequestMapping(value = "/roles/{roleId}/edit", method = RequestMethod.POST)
    public String editRole(@PathVariable Long roleId, @Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);

            // Add project if invalid data was received
            redirectAttributes.addFlashAttribute("editRole", role);

            // Add flash message with the error
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid role name.  Name must be alphanumeric.", FlashMessage.Status.FAILURE));

            // And redirect
            return String.format("redirect:/roles/%d/edit", roleId);
        }
        roleService.update(role);
        return "redirect:/roles";
    }

    // Delete a role
    @RequestMapping("/roles/{roleId}/delete")
    public String deleteRole(@PathVariable Long roleId) {
        roleService.delete(roleService.findRoleById(roleId));
        return "redirect:/roles";
    }

    // Add a new role
    @RequestMapping("/roles/add")
    public String addRole() {
        // Create a new role with a default name
        String roleName = "New Role";
        Role role = new Role();
        role.setName(roleName);
        roleService.update(role);

        // Find all the roles and determine the index of the one we just created
        List<Role> roles = roleService.findAll();
        Role newRole = roles.stream().filter(r -> r.getName().equals(roleName)).findFirst().orElse(null);
        return String.format("redirect:/roles/%d/edit", newRole.getId());
    }
}
