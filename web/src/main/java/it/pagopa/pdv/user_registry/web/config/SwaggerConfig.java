package it.pagopa.pdv.user_registry.web.config;


import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import it.pagopa.pdv.user_registry.web.model.CertifiableFieldResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = SwaggerConfig.class)
class SwaggerConfig {


    @Configuration
    @Profile("swaggerIT")
    @PropertySource("classpath:/swagger/swagger_it.properties")
    public static class ItConfig {
    }

    @Configuration
    @Profile("swaggerEN")
    @PropertySource("classpath:/swagger/swagger_en.properties")
    public static class EnConfig {
    }

    private static class CertifiableFieldResourceString extends CertifiableFieldResource<String>{
    }
    private static class CertifiableFieldResourceDate extends CertifiableFieldResource<LocalDate>{
    }

    private final Environment environment;


    @Autowired
    SwaggerConfig(Environment environment) {
        log.trace("Initializing {}", SwaggerConfig.class.getSimpleName());
        Assert.notNull(environment, "Environment is required");
        this.environment = environment;
    }

    @Bean
    public OpenAPI swaggerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(environment.getProperty("swagger.title", environment.getProperty("spring.application.name")))
                        .description(environment.getProperty("swagger.description", "Api and Models"))
                        .version(environment.getProperty("swagger.version", environment.getProperty("spring.application.version"))))
                .components(new Components()
                        .addSchemas("NameCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceString.class, "${swagger.model.user.name}" ))
                        .addSchemas("FamilyNameCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceString.class, "${swagger.model.user.familyName}" ))
                        .addSchemas("EmailCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceString.class, "${swagger.model.user.email}" ))
                        .addSchemas("BirthDateCertifiableSchema", getSchemaWithDifferentDescription(CertifiableFieldResourceDate.class, "${swagger.model.user.birthDate}" )));

    }

    private Schema getSchemaWithDifferentDescription(Class className, String description){
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(
                        new AnnotatedType(className).resolveAsRef(false));
        return resolvedSchema.schema.description(description);
    }


}
