package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.SystemConfigRequest;
import com.example.labmedical.controller.dtos.response.SystemConfigResponse;
import com.example.labmedical.controller.mapper.SystemConfigMapper;
import com.example.labmedical.exceptions.ConfigNotFoundException;
import com.example.labmedical.repository.ConfigRepository;
import com.example.labmedical.repository.model.Config;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class ConfigService {

    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private SystemConfigMapper systemConfigMapper;
    @Autowired
    private ObjectMapper om;
    public SystemConfigResponse saveSystemConfig(SystemConfigRequest system) throws JsonProcessingException {
        var config = new Config("systemConfig", om.writeValueAsString(system));
        configRepository.save(config);
        return systemConfigMapper.map(system);
    }

    public SystemConfigResponse getSystemConfig() throws JsonProcessingException {
        var config = configRepository.findById("systemConfig")
                        .orElseThrow(() -> new ConfigNotFoundException("systemConfig not found"));
        var systeConfigRequest = om.readValue(config.getValue(), SystemConfigRequest.class);
        return systemConfigMapper.map(systeConfigRequest);
    }
}
