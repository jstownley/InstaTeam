package com.teamtreehouse.instateam.web.controller;

import com.teamtreehouse.instateam.model.Collaborator;
import com.teamtreehouse.instateam.model.Project;
import com.teamtreehouse.instateam.model.Role;
import com.teamtreehouse.instateam.service.CollaboratorService;
import com.teamtreehouse.instateam.service.ProjectService;
import com.teamtreehouse.instateam.service.RoleService;
import com.teamtreehouse.instateam.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CollaboratorService collaboratorService;
    @Autowired
    private RoleService roleService;

    // Show the home page
    @RequestMapping("/")
    public String showHomePage() {
        return "redirect:/projects";
    }

    // Show a list of the projects
    @RequestMapping("/projects")
    public String showProjectList(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "projects/index";
    }

    // Show the detail page for a project
    @RequestMapping("/projects/{projectId}")
    public String showProjectDetail(@PathVariable Long projectId, Model model, ModelMap modelMap) {
        Project project = projectService.findProjectById(projectId);
        model.addAttribute("project", project);
        // We also need to map the assigned collaborator to the needed role
        List<Role> roles = project.getRolesNeeded();
        List<Collaborator> collaborators = project.getCollaborators();
        Map<String, Collaborator> rcMap = new HashMap<>();
        for (Role role : roles) {
            rcMap.put(role.getName(), collaborators.stream()
                .filter(c->c.getRole()
                    .getId()==role.getId())
                .findFirst()
                .orElse(null));
        }
        modelMap.addAttribute("rcMap", rcMap);
        return "projects/detail";
    }

    // Show the page for editing the info for a project
    @RequestMapping("/projects/{projectId}/edit")
    public String showEditProject(@PathVariable Long projectId, Model model) {
        Project project = projectService.findProjectById(projectId);
        List<Role> roles = roleService.findAll();
        model.addAttribute("project", project);
        model.addAttribute("roles", roles);
        model.addAttribute("action", String.format("/projects/%d/edit", projectId));
        model.addAttribute("submit", "Save");
        return "projects/edit";
    }

    // Show the page for editing the info for a project
    @RequestMapping("/projects/new")
    public String showNewProject(Model model) {
        List<Role> roles = roleService.findAll();
        if (!model.containsAttribute("project")) {
            model.addAttribute("project", new Project());
        }
        model.addAttribute("roles", roles);
        model.addAttribute("action", "/projects");
        model.addAttribute("submit", "Add");
        return "projects/edit";
    }

    // Show the home page (a list of projects)
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public String AddProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);

            // Add project if invalid data was received
            redirectAttributes.addFlashAttribute("project", project);

            // And redirect
            return "redirect:/projects/new";
        }

        projectService.update(FixNeededRoles(project));
        return "redirect:/projects";
    }

    // Update the project based on the input form
    @RequestMapping(value = "/projects/{projectId}/edit", method = RequestMethod.POST)
    public String updateProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);

            // Add project if invalid data was received
            redirectAttributes.addFlashAttribute("project", project);

            // And redirect
            return String.format("redirect:/projects/%d/edit", project.getId());
        }

        projectService.update(FixNeededRoles(project));

        return String.format("redirect:/projects/%d", project.getId());
    }

    // Show the collaborators for a project for editing
    @RequestMapping("/projects/{projectId}/collaborators")
    public String showEditProjectCollaborators(@PathVariable Long projectId, Model model, ModelMap modelMap) {
        Project project = projectService.findProjectById(projectId);
        model.addAttribute("project", project);
        List<Role> rolesNeeded = project.getRolesNeeded();
        Map<String,List<Collaborator>> rcMap = new HashMap<>();
        for (Role role : rolesNeeded) {
            rcMap.put(role.getName(), collaboratorService.findCollaboratorsByRoleId(role.getId()));
        }
        modelMap.addAttribute("rcMap", rcMap);
        return "projects/collaborators";
    }

    // Edit the collaborators for a project
    @RequestMapping(value = "/projects/{projectId}/collaborators", method = RequestMethod.POST)
    public String editProjectCollaborators(@Valid Project project, @PathVariable Long projectId, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);

            // Add project if invalid data was received
            redirectAttributes.addFlashAttribute("project", project);

            // And redirect
            return String.format("redirect:/projects/%d/collaborators", projectId);
        }
        project = FixNeededRoles(project);
        project = FixNeededCollaborators(project);
        projectService.update(project);
        return String.format("redirect:/projects/%d", projectId);
    }

    private Project FixNeededRoles(Project project) {
        // For some reason, I can't get both the role name and role ID to bind to the th:field, so I need to ensure that
        // the role we save to the project includes all the right fields.
        List<Role> roles = project.getRolesNeeded();
        List<Role> projectRoles = new ArrayList<>();
        for (Role inRole : roles) {
            if (null != inRole.getId()) {
                projectRoles.add(roleService.findRoleById(inRole.getId()));
            }
        }
        project.setRolesNeeded(projectRoles);
        return project;
    }

    private Project FixNeededCollaborators(Project project) {
        // For some reason, I can't get all the collaborator fields bind to the th:field, so I need to ensure that
        // the collaborator we save to the project includes all the right fields.
        List<Collaborator> collaborators = project.getCollaborators();
        List<Collaborator> projectCollaborators = new ArrayList<>();
        for (Collaborator inCollaborator : collaborators) {
            if (null != inCollaborator.getId()) {
                projectCollaborators.add(collaboratorService.findCollaboratorById(inCollaborator.getId()));
            }
        }
        project.setCollaborators(projectCollaborators);
        return project;
    }
}
