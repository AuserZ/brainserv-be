package com.upakb.brainserv.service;

import com.upakb.brainserv.dto.AppStatusDTO;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class Pm2MonitorService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<AppStatusDTO> getProcessedStatus() throws Exception {
        List<AppStatusDTO> appList = new ArrayList<>();

        // Execute the PM2 command
        Process process = Runtime.getRuntime().exec("pm2 jlist");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        // Parse the JSON array from PM2
        JsonNode root = objectMapper.readTree(output.toString());

        for (JsonNode node : root) {
            AppStatusDTO dto = new AppStatusDTO();
            dto.setName(node.get("name").asText());
            dto.setPid(node.get("pid").asInt());

            // Status is inside pm2_env
            dto.setStatus(node.get("pm2_env").get("status").asText());
            dto.setRestarts(node.get("pm2_env").get("restart_time").asInt());

            // Metrics are inside monit
            dto.setCpu(node.get("monit").get("cpu").asDouble());
            dto.setMemory(node.get("monit").get("memory").asLong());

            appList.add(dto);
        }

        return appList;
    }
}