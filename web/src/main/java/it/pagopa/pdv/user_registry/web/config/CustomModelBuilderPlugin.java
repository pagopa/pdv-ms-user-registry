package it.pagopa.pdv.user_registry.web.config;

import org.springframework.core.annotation.Order;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

//@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class CustomModelBuilderPlugin implements ModelBuilderPlugin {
    @Override
    public void apply(ModelContext context) {
        System.out.println();
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
