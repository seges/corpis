package sk.seges.corpis.pap.converter.model;

import sk.seges.corpis.server.model.converter.provider.AbstractContextualConverterProvider;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.core.pap.model.mutable.utils.MutableProcessingEnvironment;
import sk.seges.sesam.pap.converter.model.ConverterProviderContextType;

public class ContextualConverterProviderType extends ConverterProviderContextType {

    public ContextualConverterProviderType(MutableDeclaredType mutableType, MutableProcessingEnvironment processingEnv) {
        super(mutableType, processingEnv);
    }

    @Override
    protected Class<?> getContextSuperClass() {
        return AbstractContextualConverterProvider.class;
    }
}
