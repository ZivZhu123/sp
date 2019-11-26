package com.zz.zuul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName DocumentationConfig
 * @Description: TODO
 * @Author zhuzhou
 * @Date 2019/11/15
 * @Version V1.0
 **/
@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    @Value("${rest.api.names}")
    private String[] apiNames;

    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        if (apiNames != null) {
            Arrays.stream(apiNames).forEach(s ->
                    resources.add(swaggerResource(s,  s + "/v2/api-docs", "2.0"))
            );
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
