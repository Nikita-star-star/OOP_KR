package com.example.jkhspring.controller.dialogs;

import com.example.jkhspring.entity.ServiceType;
import com.example.jkhspring.service.JkhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/dialogs/service-types")
public class ServiceTypeDialogController {

    private final JkhService service;

    public ServiceTypeDialogController(JkhService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String newServiceType(Model model) {
        model.addAttribute("serviceType", ServiceType.builder().price(BigDecimal.ZERO).durationMinutes(30).build());
        model.addAttribute("mode", "create");
        return "dialogs/service_type_form";
    }

    @GetMapping("/{id}/edit")
    public String editServiceType(@PathVariable Long id, Model model) {
        model.addAttribute("serviceType", service.fetchServiceTypeById(id));
        model.addAttribute("mode", "edit");
        return "dialogs/service_type_form";
    }

    @PostMapping("/save")
    public String saveServiceType(@ModelAttribute ServiceType serviceType,
                                  @RequestParam(name = "id", required = false) Long id) {
        if (id == null) {
            service.saveServiceType(serviceType);
        } else {
            service.updateServiceType(serviceType, id);
        }
        return "redirect:/";
    }
}
