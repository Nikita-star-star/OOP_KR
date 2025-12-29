package com.example.jkhspring.controller.dialogs;

import com.example.jkhspring.entity.Resident;
import com.example.jkhspring.service.JkhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dialogs/residents")
public class ResidentDialogController {

    private final JkhService service;

    public ResidentDialogController(JkhService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String newResident(Model model) {
        model.addAttribute("resident", new Resident());
        model.addAttribute("mode", "create");
        return "dialogs/resident_form";
    }

    @GetMapping("/{id}/edit")
    public String editResident(@PathVariable Long id, Model model) {
        model.addAttribute("resident", service.fetchResidentById(id));
        model.addAttribute("mode", "edit");
        return "dialogs/resident_form";
    }

    @PostMapping("/save")
    public String saveResident(@ModelAttribute Resident resident,
                               @RequestParam(name = "id", required = false) Long id) {
        if (id == null) {
            service.saveResident(resident);
        } else {
            service.updateResident(resident, id);
        }
        return "redirect:/";
    }
}
