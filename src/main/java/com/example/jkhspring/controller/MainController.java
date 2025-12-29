package com.example.jkhspring.controller;

import com.example.jkhspring.entity.*;
import com.example.jkhspring.service.JkhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    private final JkhService service;

    public MainController(JkhService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(name = "req_q", required = false) String reqQ,
            @RequestParam(name = "res_q", required = false) String resQ,
            @RequestParam(name = "wor_q", required = false) String worQ,
            @RequestParam(name = "srv_q", required = false) String srvQ,
            @RequestParam(name = "status", required = false) String status,
            Model model
    ) {
        List<Request> requests = service.fetchRequestList();
        List<Resident> residents = service.fetchResidentList();
        List<Worker> workers = service.fetchWorkerList();
        List<ServiceType> serviceTypes = service.fetchServiceTypeList();

        // Поиск по совпадениям (как у друга, через Searchable.matches)
        if (reqQ != null && !reqQ.isBlank()) {
            requests = requests.stream().filter(r -> r.matches(reqQ)).toList();
        }
        if (resQ != null && !resQ.isBlank()) {
            residents = residents.stream().filter(r -> r.matches(resQ)).toList();
        }
        if (worQ != null && !worQ.isBlank()) {
            workers = workers.stream().filter(w -> w.matches(worQ)).toList();
        }
        if (srvQ != null && !srvQ.isBlank()) {
            serviceTypes = serviceTypes.stream().filter(s -> s.matches(srvQ)).toList();
        }

        // Фильтр заявок по статусу
        if (status != null && !status.isBlank() && !status.equalsIgnoreCase("ALL")) {
            try {
                Request.Status st = Request.Status.valueOf(status);
                requests = requests.stream().filter(r -> r.getStatus() == st).toList();
            } catch (IllegalArgumentException ignored) {
                // если пришёл мусор, просто не фильтруем
            }
        }

        model.addAttribute("requests", requests);
        model.addAttribute("residents", residents);
        model.addAttribute("workers", workers);
        model.addAttribute("serviceTypes", serviceTypes);

        model.addAttribute("statuses", Request.Status.values());
        model.addAttribute("currentStatus", status == null ? "ALL" : status);

        model.addAttribute("req_q", reqQ == null ? "" : reqQ);
        model.addAttribute("res_q", resQ == null ? "" : resQ);
        model.addAttribute("wor_q", worQ == null ? "" : worQ);
        model.addAttribute("srv_q", srvQ == null ? "" : srvQ);

        return "main";
    }

    /**
     * Алиасы на случай, если в браузере/закладках остались старые ссылки.
     * Без этого после логина можно попасть на несуществующий URL и получить 404.
     */
    @GetMapping({"/request", "/requests", "/index"})
    public String legacyAliases() {
        return "redirect:/";
    }

    /* ====== Delete actions ====== */

    @PostMapping("/requests/delete")
    public String deleteRequest(@RequestParam("id") Long id) {
        service.deleteRequestById(id);
        return "redirect:/";
    }

    @PostMapping("/residents/delete")
    public String deleteResident(@RequestParam("id") Long id) {
        service.deleteResidentById(id);
        return "redirect:/";
    }

    @PostMapping("/workers/delete")
    public String deleteWorker(@RequestParam("id") Long id) {
        service.deleteWorkerById(id);
        return "redirect:/";
    }

    @PostMapping("/service-types/delete")
    public String deleteServiceType(@RequestParam("id") Long id) {
        service.deleteServiceTypeById(id);
        return "redirect:/";
    }
}
