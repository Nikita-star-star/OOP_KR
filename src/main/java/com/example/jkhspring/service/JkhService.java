package com.example.jkhspring.service;

import com.example.jkhspring.dto.RequestDto;
import com.example.jkhspring.entity.*;

import java.util.List;

public interface JkhService {

    // Requests
    Request saveRequest(RequestDto requestDto);
    List<Request> fetchRequestList();
    Request fetchRequestById(Long requestId);
    Request updateRequest(RequestDto requestDto, Long requestId);
    void deleteRequestById(Long requestId);

    // Residents
    Resident saveResident(Resident resident);
    List<Resident> fetchResidentList();
    Resident fetchResidentById(Long residentId);
    Resident updateResident(Resident resident, Long residentId);
    void deleteResidentById(Long residentId);

    // Service types
    ServiceType saveServiceType(ServiceType serviceType);
    List<ServiceType> fetchServiceTypeList();
    ServiceType fetchServiceTypeById(Long serviceTypeId);
    ServiceType updateServiceType(ServiceType serviceType, Long serviceTypeId);
    void deleteServiceTypeById(Long serviceTypeId);

    // Workers
    Worker saveWorker(Worker worker);
    List<Worker> fetchWorkerList();
    Worker fetchWorkerById(Long workerId);
    Worker updateWorker(Worker worker, Long workerId);
    void deleteWorkerById(Long workerId);
}
