package com.example.jkhspring.controller.dialogs;

import com.example.jkhspring.entity.Worker;
import com.example.jkhspring.service.JkhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dialogs/workers")
public class WorkerDialogController {

    private final JkhService service;

    public WorkerDialogController(JkhService service) {
        this.service = service;
    }

    @GetMapping("/new")
    public String newWorker(Model model) {
        model.addAttribute("worker", Worker.builder().rating(0.0).build());
        model.addAttribute("mode", "create");
        return "dialogs/worker_form";
    }

    @GetMapping("/{id}/edit")
    public String editWorker(@PathVariable Long id, Model model) {
        model.addAttribute("worker", service.fetchWorkerById(id));
        model.addAttribute("mode", "edit");
        return "dialogs/worker_form";
    }

    @PostMapping("/save")
    public String saveWorker(@ModelAttribute Worker worker,
                             @RequestParam(name = "id", required = false) Long id) {
        if (id == null) {
            service.saveWorker(worker);
        } else {
            service.updateWorker(worker, id);
        }
        return "redirect:/";
    }
}
