package it.pagopa.pdv.user_registry.web.config;

import com.fasterxml.classmate.members.RawField;
import org.springframework.core.annotation.Order;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.List;

//@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class ParameterIndexOrderReader2 implements ModelPropertyBuilderPlugin {
    @Override
    public void apply(ModelPropertyContext context) {
        System.out.println();
        String internalName = context.getBeanPropertyDefinition().get().getInternalName();
        List<RawField> memberFields = context.getOwner().getType().getMemberFields();
        for (int i = 0; i < memberFields.size(); i++) {
            if (internalName.equals(memberFields.get(i).getName())) {
                context.getSpecificationBuilder().position(i);
                break;
            }
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

}
