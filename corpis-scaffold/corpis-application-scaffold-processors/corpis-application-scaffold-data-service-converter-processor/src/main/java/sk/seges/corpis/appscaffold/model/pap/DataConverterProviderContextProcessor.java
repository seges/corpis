package sk.seges.corpis.appscaffold.model.pap;

import sk.seges.corpis.pap.model.hibernate.provider.DataClasspathConfigurationProvider;
import sk.seges.corpis.pap.service.hibernate.HibernateConverterProviderContextProcessor;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.pap.model.model.EnvironmentContext;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.provider.api.ConfigurationProvider;

public class DataConverterProviderContextProcessor extends HibernateConverterProviderContextProcessor {

    @Override
    protected ConfigurationProvider[] getConfigurationProviders(MutableDeclaredType service, EnvironmentContext<TransferObjectProcessingEnvironment> context) {
        return new ConfigurationProvider[] {
                new DataClasspathConfigurationProvider(getClassPathTypes(), getEnvironmentContext(service))
        };
    }
}
