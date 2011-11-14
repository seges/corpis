package sk.seges.corpis.pap.service.hibernate.accessor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

import org.springframework.transaction.annotation.Transactional;

import sk.seges.corpis.service.annotation.TransactionPropagation;
import sk.seges.corpis.service.annotation.TransactionPropagation.PropagationType;
import sk.seges.corpis.service.annotation.TransactionPropagationModel;
import sk.seges.corpis.service.annotation.TransactionPropagations;
import sk.seges.sesam.core.pap.accessor.AnnotationAccessor;

public class TransactionPropagationAccessor extends AnnotationAccessor {

	private Transactional transactional;
	private List<TransactionPropagation> transactionPropagations = new ArrayList<TransactionPropagation>();
	
	private final ProcessingEnvironment processingEnv;
	
	public TransactionPropagationAccessor(Element element, ProcessingEnvironment processingEnv) {
		
		this.processingEnv = processingEnv;
		
		TransactionPropagation transactionPropagation = getAnnotation(element, TransactionPropagation.class);
		
		if (transactionPropagation != null) {
			transactionPropagations.add(transactionPropagation);
		}

		TransactionPropagations transactionPropagations = getAnnotation(element, TransactionPropagations.class);

		if (transactionPropagations != null) {
			for (TransactionPropagation propagation: transactionPropagations.value()) {
				this.transactionPropagations.add(propagation);
			}
		}
		
		transactional = getAnnotation(element, Transactional.class);
	}
	
	public TransactionPropagationModel[] getPropagations() {
		List<TransactionPropagationModel> result = new ArrayList<TransactionPropagationModel>();

		if (transactional == null) {
			result.add(new TransactionPropagationModel());
			return result.toArray(new TransactionPropagationModel[] {});
		}

		for (TransactionPropagation annotation: transactionPropagations) {
			result.add(toPojo(annotation, TransactionPropagationModel.class, processingEnv));
		}
		
		return result.toArray(new TransactionPropagationModel[] {});
	}
	
	public boolean isTransactionPropagated() {
		if (transactionPropagations == null) {
			return transactional != null;
		}
		
		for (TransactionPropagation transactionPropagation: transactionPropagations) {
			if (transactionPropagation.value().equals(PropagationType.PROPAGATE)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isValid() {
		return false;
	}
}