package com.upakb.brainserv.dto;

import lombok.Data;

@Data
public class AppStatusDTO {
    private String name;
    private Integer pid;
    private String status; // "online", "errored", "stopped"
    private Long memory;   // In bytes
    private Double cpu;    // Percentage
    private Integer restarts;
    private String uptime;
}