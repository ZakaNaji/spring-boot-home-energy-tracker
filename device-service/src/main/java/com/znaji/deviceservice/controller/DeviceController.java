package com.znaji.deviceservice.controller;

import com.znaji.deviceservice.dto.CreateDeviceRequest;
import com.znaji.deviceservice.dto.DeviceResponse;
import com.znaji.deviceservice.dto.UpdateDeviceRequest;
import com.znaji.deviceservice.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse createDevice(@Valid @RequestBody CreateDeviceRequest request) {
        return deviceService.createDevice(request);
    }

    @GetMapping("/{id}")
    public DeviceResponse getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    @GetMapping
    public List<DeviceResponse> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/by-user/{userId}")
    public List<DeviceResponse> getDevicesByUserId(@PathVariable Long userId) {
        return deviceService.getDevicesByUserId(userId);
    }

    @PutMapping("/{id}")
    public DeviceResponse updateDevice(@PathVariable Long id,
                                       @Valid @RequestBody UpdateDeviceRequest request) {
        return deviceService.updateDevice(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
    }
}