package com.znaji.deviceservice.service;

import com.znaji.deviceservice.dto.CreateDeviceRequest;
import com.znaji.deviceservice.dto.DeviceResponse;
import com.znaji.deviceservice.dto.UpdateDeviceRequest;

import java.util.List;

public interface DeviceService {

    DeviceResponse createDevice(CreateDeviceRequest request);

    DeviceResponse getDeviceById(Long id);

    List<DeviceResponse> getAllDevices();

    List<DeviceResponse> getDevicesByUserId(Long userId);

    DeviceResponse updateDevice(Long id, UpdateDeviceRequest request);

    void deleteDevice(Long id);
}