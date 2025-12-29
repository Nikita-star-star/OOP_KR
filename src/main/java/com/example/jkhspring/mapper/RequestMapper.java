package com.example.jkhspring.mapper;

import com.example.jkhspring.dto.RequestDto;
import com.example.jkhspring.entity.*;
import com.example.jkhspring.repository.*;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    private final ResidentRepository residentRepository;
    private final WorkerRepository workerRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public RequestMapper(ResidentRepository residentRepository,
                         WorkerRepository workerRepository,
                         ServiceTypeRepository serviceTypeRepository) {
        this.residentRepository = residentRepository;
        this.workerRepository = workerRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public Request toEntity(RequestDto dto) {
        if (dto == null) return null;

        Resident resident = residentRepository.getReferenceById(dto.getResidentId());
        ServiceType serviceType = dto.getServiceTypeId() == null ? null : serviceTypeRepository.getReferenceById(dto.getServiceTypeId());
        Worker worker = dto.getAssignedWorkerId() == null ? null : workerRepository.getReferenceById(dto.getAssignedWorkerId());

        return Request.builder()
                .requestId(null)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .resident(resident)
                .serviceType(serviceType)
                .assignedWorker(worker)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .build();
    }

    public RequestDto toDto(Request entity) {
        if (entity == null) return null;

        RequestDto dto = new RequestDto();
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setResidentId(entity.getResident() == null ? null : entity.getResident().getResidentId());
        dto.setServiceTypeId(entity.getServiceType() == null ? null : entity.getServiceType().getServiceTypeId());
        dto.setAssignedWorkerId(entity.getAssignedWorker() == null ? null : entity.getAssignedWorker().getWorkerId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        return dto;
    }
}
