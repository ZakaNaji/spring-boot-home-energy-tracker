package com.znaji.deviceservice.exception;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(Long deviceId) {
        super("Device not found with id: " + deviceId);
    }
}