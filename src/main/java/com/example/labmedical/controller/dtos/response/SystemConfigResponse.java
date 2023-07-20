package com.example.labmedical.controller.dtos.response;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SystemConfigResponse {

    private String companyName;
    private String logoUrl;
    private String primaryColor;
    private String secundaryColor;
    private String fontColor;

}
