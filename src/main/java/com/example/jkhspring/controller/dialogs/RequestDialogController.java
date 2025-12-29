package com.example.jkhspring.controller.dialogs;

import com.example.jkhspring.dto.RequestDto;
import com.example.jkhspring.entity.Request;
import com.example.jkhspring.mapper.RequestMapper;
import com.example.jkhspring.service.JkhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/dialogs/requests")
public class RequestDialogController {

    private final JkhService service;
    private final RequestMapper mapper;

    public RequestDialogController(JkhService service, RequestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    private void fillDictionaries(Model model) {
        model.addAttribute("residents", service.fetchResidentList());
        model.addAttribute("workers", service.fetchWorkerList());
        model.addAttribute("serviceTypes", service.fetchServiceTypeList());
        model.addAttribute("statuses", Request.Status.values());
        model.addAttribute("priorities", Request.Priority.values());
    }

    @GetMapping("/new")
    public String newRequest(Model model) {
        RequestDto dto = RequestDto.builder()
                .status(Request.Status.NEW)
                .priority(Request.Priority.MEDIUM)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();

        model.addAttribute("dto", dto);
        model.addAttribute("mode", "create");
        model.addAttribute("id", null);
        fillDictionaries(model);
        return "dialogs/request_form";
    }

    @GetMapping("/{id}/edit")
    public String editRequest(@PathVariable Long id, Model model) {
        Request entity = service.fetchRequestById(id);
        RequestDto dto = mapper.toDto(entity);

        model.addAttribute("dto", dto);
        model.addAttribute("mode", "edit");
        model.addAttribute("id", id);
        fillDictionaries(model);
        return "dialogs/request_form";
    }

    @PostMapping("/save")
    public String saveRequest(@ModelAttribute("dto") RequestDto dto,
                              @RequestParam(name = "id", required = false) Long id) {
        if (id == null) {
            service.saveRequest(dto);
        } else {
            service.updateRequest(dto, id);
        }
        return "redirect:/";
    }
}
