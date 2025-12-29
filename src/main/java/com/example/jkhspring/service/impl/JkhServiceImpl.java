package com.example.jkhspring.service.impl;

import com.example.jkhspring.dto.RequestDto;
import com.example.jkhspring.entity.*;
import com.example.jkhspring.mapper.RequestMapper;
import com.example.jkhspring.repository.*;
import com.example.jkhspring.service.JkhService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JkhServiceImpl implements JkhService {

    private final RequestRepository requestRepository;
    private final ResidentRepository residentRepository;
    private final WorkerRepository workerRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final RequestMapper requestMapper;

    public JkhServiceImpl(RequestRepository requestRepository,
                          ResidentRepository residentRepository,
                          WorkerRepository workerRepository,
                          ServiceTypeRepository serviceTypeRepository,
                          RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.residentRepository = residentRepository;
        this.workerRepository = workerRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.requestMapper = requestMapper;
    }

    private Request getRequestByDto(RequestDto dto) {
        return requestMapper.toEntity(dto);
    }

    @Override
    public Request saveRequest(RequestDto requestDto) {
        return requestRepository.save(getRequestByDto(requestDto));
    }

    @Override
    public List<Request> fetchRequestList() {
        return requestRepository.findAll();
    }

    @Override
    public Request fetchRequestById(Long requestId) {
        return requestRepository.getReferenceById(requestId);
    }

    @Override
    public Request updateRequest(RequestDto requestDto, Long requestId) {
        Request req = getRequestByDto(requestDto);
        req.setRequestId(requestId);
        return requestRepository.save(req);
    }

    @Override
    public void deleteRequestById(Long requestId) {
        requestRepository.deleteById(requestId);
    }

    // Residents
    @Override
    public Resident saveResident(Resident resident) {
        return residentRepository.save(resident);
    }

    @Override
    public List<Resident> fetchResidentList() {
        return residentRepository.findAll();
    }

    @Override
    public Resident fetchResidentById(Long residentId) {
        return residentRepository.getReferenceById(residentId);
    }

    @Override
    public Resident updateResident(Resident resident, Long residentId) {
        Resident db = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Жилец не найден"));
        db.setName(resident.getName());
        db.setPhone(resident.getPhone());
        db.setEmail(resident.getEmail());
        return residentRepository.save(db);
    }

    @Override
    public void deleteResidentById(Long residentId) {
        residentRepository.deleteById(residentId);
    }

    // Service types
    @Override
    public ServiceType saveServiceType(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    @Override
    public List<ServiceType> fetchServiceTypeList() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public ServiceType fetchServiceTypeById(Long serviceTypeId) {
        return serviceTypeRepository.getReferenceById(serviceTypeId);
    }

    @Override
    public ServiceType updateServiceType(ServiceType serviceType, Long serviceTypeId) {
        ServiceType db = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new RuntimeException("Тип услуги не найден"));
        db.setName(serviceType.getName());
        db.setPrice(serviceType.getPrice());
        db.setDurationMinutes(serviceType.getDurationMinutes());
        return serviceTypeRepository.save(db);
    }

    @Override
    public void deleteServiceTypeById(Long serviceTypeId) {
        serviceTypeRepository.deleteById(serviceTypeId);
    }

    // Workers
    @Override
    public Worker saveWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public List<Worker> fetchWorkerList() {
        return workerRepository.findAll();
    }

    @Override
    public Worker fetchWorkerById(Long workerId) {
        return workerRepository.getReferenceById(workerId);
    }

    @Override
    public Worker updateWorker(Worker worker, Long workerId) {
        Worker db = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Исполнитель не найден"));
        db.setName(worker.getName());
        db.setRole(worker.getRole());
        db.setRating(worker.getRating());
        return workerRepository.save(db);
    }

    @Override
    public void deleteWorkerById(Long workerId) {
        workerRepository.deleteById(workerId);
    }
}
