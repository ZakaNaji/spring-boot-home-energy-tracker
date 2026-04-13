package com.znaji.deviceservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "devices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 50)
    private String type;

    @Column(length = 255)
    private String location;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}