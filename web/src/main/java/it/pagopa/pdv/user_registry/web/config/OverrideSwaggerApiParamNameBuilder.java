package it.pagopa.pdv.user_registry.web.config;

/*
//@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class OverrideSwaggerApiParamNameBuilder implements ExpandedParameterBuilderPlugin {

    @Override
    public boolean supports(DocumentationType type) {
        return true;
    }

    @Override
    public void apply(ParameterExpansionContext context) {
        Optional<ApiParam> apiParamOptional = findApiParamAnnotation(context.getField().getRawMember());
        if (apiParamOptional.isPresent()) {
            fromApiParam(context, apiParamOptional.get());
        }
    }

    private void fromApiParam(ParameterExpansionContext context, ApiParam apiParam) {
        context.getParameterBuilder()
                .name(emptyToNull(apiParam.name()));
    }

    private String emptyToNull(String str) {
        return StringUtils.hasText(str) ? str : null;
    }
}*/
