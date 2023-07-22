package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.SystemConfigRequest;
import com.example.labmedical.controller.dtos.response.SystemConfigResponse;
import com.example.labmedical.exceptions.ConfigNotFoundException;
import com.example.labmedical.repository.ConfigRepository;
import com.example.labmedical.repository.model.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private ObjectMapper om;
    public SystemConfigResponse saveSystemConfig(SystemConfigRequest system) throws JsonProcessingException {
        var config = new Config("systemConfig", om.writeValueAsString(system));
        configRepository.save(config);
        return om.readValue(config.getValue(), SystemConfigResponse.class);
    }

    public SystemConfigResponse getSystemConfig() throws JsonProcessingException {
        Config config = configRepository.findById("systemConfig")
                        .orElseThrow(() -> new ConfigNotFoundException("systemConfig not found"));
        return om.readValue(config.getValue(), SystemConfigResponse.class);
    }
}
