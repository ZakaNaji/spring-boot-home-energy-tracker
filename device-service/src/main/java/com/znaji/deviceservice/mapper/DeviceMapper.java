package com.znaji.deviceservice.mapper;

import com.znaji.deviceservice.domain.Device;
import com.znaji.deviceservice.dto.CreateDeviceRequest;
import com.znaji.deviceservice.dto.DeviceResponse;
import com.znaji.deviceservice.dto.UpdateDeviceRequest;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public Device toEntity(CreateDeviceRequest request) {
        return Device.builder()
                .name(request.name())
                .type(request.type())
                .location(request.location())
                .userId(request.userId())
                .build();
    }

    public void updateEntity(Device device, UpdateDeviceRequest request) {
        device.setName(request.name());
        device.setType(request.type());
        device.setLocation(request.location());
        device.setUserId(request.userId());
    }

    public DeviceResponse toResponse(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getName(),
                device.getType(),
                device.getLocation(),
                device.getUserId()
        );
    }
}