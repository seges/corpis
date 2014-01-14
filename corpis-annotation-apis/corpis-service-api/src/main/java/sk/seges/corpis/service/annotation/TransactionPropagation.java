package sk.seges.corpis.service.annotation;

import sk.seges.sesam.core.configuration.annotation.GenerateModel;

@GenerateModel
public @interface TransactionPropagation {

	PropagationType value() default sk.seges.corpis.service.annotation.PropagationType.ISOLATE;
	PropagationTarget[] target() default { sk.seges.corpis.service.annotation.PropagationTarget.RETURN_VALUE, sk.seges.corpis.service.annotation.PropagationTarget.ARGUMENTS };

	String[] fields() default {};
}