package com.sw.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.AllowableRangeValues;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(prefix = "market.swagger", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "market.swagger")
@Configuration
public class Swagger2Configuration {


    private String basePackage = "com.sw";
    private String version = "1.0";
    private String title = "test";
    private String description = "接口描述";
    private String termsOfServiceUrl = "API TERMS URL";
    private String license = "license";
    private String licenseUrl = "license url";
    private String contactName = "chenshiwan";
    private String contactUrl = "inleft.com";
    private String contactEmail = "inleft@qq.com";
    private List<SwaggerParam> globalOperationParameters = new ArrayList<>();

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();

        docket.globalOperationParameters(this.buildGlobalParam());
        return docket;

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(getTitle(),
                getDescription(),
                getVersion(),
                getTermsOfServiceUrl(),
                getContactName(),
                getLicense(),
                getLicenseUrl());
    }

    private List<Parameter> buildGlobalParam() {
        if (globalOperationParameters != null && !globalOperationParameters.isEmpty()) {
            ParameterBuilder ticketPar = new ParameterBuilder();
            List<Parameter> pars = new ArrayList<Parameter>(globalOperationParameters.size());
            for (SwaggerParam sp : globalOperationParameters) {
                ticketPar.name(sp.getName())
                        .description(sp.getDescription())
                        .modelRef(new ModelRef(sp.getModelRef()))
                        .parameterType(sp.getParamType())
                        .required(sp.isRequired())
                        .allowMultiple(sp.isAllowMultiple());
                if (!StringUtils.isEmpty(sp.getParamAccess())) {
                    ticketPar.parameterAccess(sp.getParamAccess());
                }
                if (!StringUtils.isEmpty(sp.getDefaultValue())) {
                    ticketPar.defaultValue(sp.getDefaultValue());
                }
                if (sp.getAllowableListValues() != null) {
                    SwaggerParam.ListValue lv = sp.getAllowableListValues();
                    ticketPar.allowableValues(new AllowableListValues(lv.getValues(), lv.getValueType()));
                } else if (sp.getAllowableRangeValues() != null) {
                    SwaggerParam.RangeValue rv = sp.getAllowableRangeValues();
                    ticketPar.allowableValues(new AllowableRangeValues(rv.getMin(), rv.isExclusiveMin(), rv.getMax(), rv.isExclusiveMax()));
                }
                pars.add(ticketPar.build());
            }
            return pars;
        }
        return null;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public List<SwaggerParam> getGlobalOperationParameters() {
        return globalOperationParameters;
    }

    public void setGlobalOperationParameters(List<SwaggerParam> globalOperationParameters) {
        this.globalOperationParameters = globalOperationParameters;
    }
}
