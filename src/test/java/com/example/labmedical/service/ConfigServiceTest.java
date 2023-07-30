package com.example.labmedical.service;

import com.example.labmedical.controller.dtos.request.SystemConfigRequest;
import com.example.labmedical.exceptions.ConfigNotFoundException;
import com.example.labmedical.repository.ConfigRepository;
import com.example.labmedical.repository.model.Config;
import com.example.labmedical.repository.model.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.matchers.Any;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ConfigServiceTest {
    @InjectMocks
    private ConfigService configService;
    @Mock
    private ConfigRepository configRepository;
    @Mock
    private LogService log;
    @Spy
    private ObjectMapper om;

    @Nested
    @DisplayName("Tests of saveSystemConfig Method")
    class saveSystemConfigMethodTests {
        @Test
        @DisplayName("When receives SystemConfigRequest, it should return SystemConfigResponse")
        void test1() throws JsonProcessingException {
            var systemConfigRequest = SystemConfigRequest.builder()
                    .companyName("CompanyTest")
                    .logoUrl("http://example.com/logo.png")
                    .primaryColor("#FF0000")
                    .secondaryColor("#00FF00")
                    .fontColor("#0000FF")
                    .build();

            var config = new Config("systemConfig", om.writeValueAsString(systemConfigRequest));
            when(configRepository.save(config)).thenReturn(config);
            when(log.info("Configuração de sistema alterada com sucesso.")).thenReturn(any(Log.class));
            var systemConfigResponse = configService.saveSystemConfig(systemConfigRequest);
            verify(configRepository).save(config);
            assertNotNull(systemConfigResponse);
            assertEquals(systemConfigResponse.getCompanyName(), systemConfigRequest.getCompanyName());
            assertEquals(systemConfigResponse.getLogoUrl(), systemConfigRequest.getLogoUrl());
            assertEquals(systemConfigResponse.getPrimaryColor(), systemConfigRequest.getPrimaryColor());
            assertEquals(systemConfigResponse.getSecondaryColor(), systemConfigRequest.getSecondaryColor());
            assertEquals(systemConfigResponse.getFontColor(), systemConfigRequest.getFontColor());

        }
    }

    @Nested
    @DisplayName("Tests of getSystemConfig Method")
    class getSystemConfigMethodTests {
        @Test
        @DisplayName("When systemConfig exists, it should return SystemConfigResponse with the right values")
        void test1() throws JsonProcessingException {
            var systemConfigRequest = SystemConfigRequest.builder()
                    .companyName("CompanyTest")
                    .logoUrl("http://example.com/logo.png")
                    .primaryColor("#FF0000")
                    .secondaryColor("#00FF00")
                    .fontColor("#0000FF")
                    .build();

            var config = new Config("systemConfig", om.writeValueAsString(systemConfigRequest));
            when(configRepository.findById("systemConfig")).thenReturn(Optional.of(config));
            when(log.info("Configuração de sistema recuperada com sucesso.")).thenReturn(any(Log.class));
            var systemConfigResponse = configService.getSystemConfig();
            verify(configRepository).findById("systemConfig");
            assertNotNull(systemConfigResponse);
            assertEquals(systemConfigResponse.getCompanyName(), systemConfigRequest.getCompanyName());
            assertEquals(systemConfigResponse.getLogoUrl(), systemConfigRequest.getLogoUrl());
            assertEquals(systemConfigResponse.getPrimaryColor(), systemConfigRequest.getPrimaryColor());
            assertEquals(systemConfigResponse.getSecondaryColor(), systemConfigRequest.getSecondaryColor());
            assertEquals(systemConfigResponse.getFontColor(), systemConfigRequest.getFontColor());

        }

        @Test
        @DisplayName("When systemConfig does't exist, it should return ConfigNotFoundException")
        void test2() throws JsonProcessingException {
            var systemConfigRequest = SystemConfigRequest.builder()
                    .companyName("CompanyTest")
                    .logoUrl("http://example.com/logo.png")
                    .primaryColor("#FF0000")
                    .secondaryColor("#00FF00")
                    .fontColor("#0000FF")
                    .build();

            var config = new Config("systemConfig", om.writeValueAsString(systemConfigRequest));
            when(configRepository.findById("systemConfig")).thenReturn(Optional.empty());
            assertThrows(ConfigNotFoundException.class, () -> configService.getSystemConfig());
            verify(configRepository).findById("systemConfig");

        }
    }

}