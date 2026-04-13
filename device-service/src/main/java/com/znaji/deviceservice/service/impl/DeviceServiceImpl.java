package com.znaji.deviceservice.service.impl;

import com.znaji.deviceservice.client.UserServiceClient;
import com.znaji.deviceservice.domain.Device;
import com.znaji.deviceservice.dto.CreateDeviceRequest;
import com.znaji.deviceservice.dto.DeviceResponse;
import com.znaji.deviceservice.dto.UpdateDeviceRequest;
import com.znaji.deviceservice.exception.DeviceNotFoundException;
import com.znaji.deviceservice.mapper.DeviceMapper;
import com.znaji.deviceservice.repository.DeviceRepository;
import com.znaji.deviceservice.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final UserServiceClient userServiceClient;

    @Override
    @Transactional
    public DeviceResponse createDevice(CreateDeviceRequest request) {
        userServiceClient.validateUserExists(request.userId());

        Device device = deviceMapper.toEntity(request);
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toResponse(savedDevice);
    }

    @Override
    public DeviceResponse getDeviceById(Long id) {
        Device device = findDeviceById(id);
        return deviceMapper.toResponse(device);
    }

    @Override
    public List<DeviceResponse> getAllDevices() {
        return deviceRepository.findAll()
                .stream()
                .map(deviceMapper::toResponse)
                .toList();
    }

    @Override
    public List<DeviceResponse> getDevicesByUserId(Long userId) {
        return deviceRepository.findByUserId(userId)
                .stream()
                .map(deviceMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public DeviceResponse updateDevice(Long id, UpdateDeviceRequest request) {
        userServiceClient.validateUserExists(request.userId());

        Device existingDevice = findDeviceById(id);
        deviceMapper.updateEntity(existingDevice, request);

        Device updatedDevice = deviceRepository.save(existingDevice);
        return deviceMapper.toResponse(updatedDevice);
    }

    @Override
    @Transactional
    public void deleteDevice(Long id) {
        Device device = findDeviceById(id);
        deviceRepository.delete(device);
    }

    private Device findDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
    }
}