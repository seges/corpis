package sk.seges.corpis.pap.entity;

import sk.seges.corpis.pap.model.hibernate.provider.DataClasspathConfigurationProvider;
import sk.seges.sesam.core.pap.model.mutable.api.MutableDeclaredType;
import sk.seges.sesam.pap.model.model.EnvironmentContext;
import sk.seges.sesam.pap.model.model.TransferObjectProcessingEnvironment;
import sk.seges.sesam.pap.model.provider.api.ConfigurationProvider;
import sk.seges.sesam.pap.provider.EntityProviderProcessor;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * Created by PeterSimun on 14.12.2014.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class DataEntityProviderProcessor extends EntityProviderProcessor {

    @Override
    protected ConfigurationProvider[] getConfigurationProviders(MutableDeclaredType service, EnvironmentContext<TransferObjectProcessingEnvironment> context) {
        return new ConfigurationProvider[] {
                new DataClasspathConfigurationProvider(getClassPathTypes(), getEnvironmentContext(service))
        };
    }

}
