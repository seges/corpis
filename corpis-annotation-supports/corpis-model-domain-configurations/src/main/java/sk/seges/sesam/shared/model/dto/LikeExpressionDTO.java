package sk.seges.sesam.shared.model.dto;

import sk.seges.sesam.pap.model.annotation.TransferObjectMapping;
import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.dao.MatchMode;

import javax.annotation.Generated;

@SuppressWarnings("serial")
@TransferObjectMapping(dtoClass = LikeExpressionDTO.class,
		domainClassName = "sk.seges.sesam.dao.LikeExpression", 
		configurationClassName = "sk.seges.sesam.server.model.dto.configuration.LikeExpressionDTOConfiguration", 
		generateConverter = false, generateDto = false, 
		converterClassName = "sk.seges.sesam.server.model.dto.configuration.LikeExpressionDTOConverter")
@Generated(value = "sk.seges.corpis.pap.model.hibernate.HibernateTransferObjectProcessor")
public class LikeExpressionDTO extends SimpleExpressionDTO {

	public static final String LIKE = "like";
	public static final String ILIKE = "ilike";

	public static final String CASE_SENSITIVE = "caseSensitive";
	
	private boolean caseSensitive;
	
	public static final String MODE = "mode";
	
	private MatchMode mode;
	
	public LikeExpressionDTO() {}
	
	public LikeExpressionDTO(String property, boolean caseSensitive, MatchMode mode, PropertyHolder value) {
		this.caseSensitive = caseSensitive;
		this.mode = mode;
		setValue(value);
		setProperty(property);
	}
	
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	
	public MatchMode getMode() {
		return mode;
	}
	
	public void setMode(MatchMode mode) {
		this.mode = mode;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LikeExpressionDTO)) return false;
		if (!super.equals(o)) return false;

		LikeExpressionDTO that = (LikeExpressionDTO) o;

		if (caseSensitive != that.caseSensitive) return false;
		if (mode != that.mode) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (caseSensitive ? 1 : 0);
		result = 31 * result + (mode != null ? mode.hashCode() : 0);
		return result;
	}
}