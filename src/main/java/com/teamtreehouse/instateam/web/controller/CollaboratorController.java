package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.RoleService;
import com.teamtreehouse.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CollaboratorController {
    @Autowired
    private CollaboratorService collaboratorService;

    @Autowired
    private RoleService roleService;

    // Show all the roles
    @RequestMapping("/collaborators")
    public String showCollaborators(Model model) {
        List<Collaborator> collaborators = collaboratorService.findAll();
        model.addAttribute("collaborators", collaborators);
        model.addAttribute("layoutAction", "list");
        return "collaborators/index";
    }

    // Show a collaborator for editing and list the other collaborators
    @RequestMapping("/collaborators/{collaboratorId}/edit")
    public String showEditCollaborators(@PathVariable Long collaboratorId, Model model) {
        List<Collaborator> collaborators = collaboratorService.findAll();
        model.addAttribute("collaborators", collaborators);
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("layoutAction", "edit");
        if (!model.containsAttribute("editCollaborator")) {
            model.addAttribute("editCollaborator", collaboratorService.findCollaboratorById(collaboratorId));
        }
        return "collaborators/index";
    }

    // Edit a collaborator
    @RequestMapping(value = "/collaborators/{collaboratorId}/edit", method = RequestMethod.POST)
    public String editCollaborator(@PathVariable Long collaboratorId, @Valid Collaborator collaborator, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);

            // Add project if invalid data was received
            redirectAttributes.addFlashAttribute("editCollaborator", collaborator);

            // Add flash message with the error
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Invalid collaborator name or role.  Name must be alphanumeric and role must be selected from dropdown list.", FlashMessage.Status.FAILURE));

            // And redirect
            return String.format("redirect:/collaborators/%d/edit", collaboratorId);
        }
        collaboratorService.update(collaborator);
        return "redirect:/collaborators";
    }

    // Delete a collaborator
    @RequestMapping("/collaborators/{collaboratorId}/delete")
    public String deleteCollaborator(@PathVariable Long collaboratorId) {
        collaboratorService.delete(collaboratorService.findCollaboratorById(collaboratorId));
        return "redirect:/collaborators";
    }

    // Add a new collaborator
    @RequestMapping("/collaborators/add")
    public String addCollaborator() {
        // Create a new collaborator with a default name and role
        List<Role> roles = roleService.findAll();
        String collaboratorName = "New Collaborator";
        Collaborator collaborator = new Collaborator();
        collaborator.setName(collaboratorName);
        collaborator.setRole(roles.get(0));
        collaboratorService.update(collaborator);

        // Find all the collaborators and determine the index of the one we just created
        List<Collaborator> collaborators = collaboratorService.findAll();
        Collaborator newCollaborator = collaborators.stream().filter(r -> r.getName().equals(collaboratorName)).findFirst().orElse(null);
        return String.format("redirect:/collaborators/%d/edit", newCollaborator.getId());
    }
}
