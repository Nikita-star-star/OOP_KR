package com.example.jkhspring.dto;

import com.example.jkhspring.entity.Request;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * DTO для передачи данных заявки (по аналогии с AppointmentDto у друга).
 * Ссылки на сущности идут по id.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {

    private Long residentId;
    private Long serviceTypeId;
    private Long assignedWorkerId;

    private String title;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    private Request.Status status;
    private Request.Priority priority;
}
