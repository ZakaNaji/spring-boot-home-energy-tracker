package com.znaji.deviceservice.repository;

import com.znaji.deviceservice.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByUserId(Long userId);
}