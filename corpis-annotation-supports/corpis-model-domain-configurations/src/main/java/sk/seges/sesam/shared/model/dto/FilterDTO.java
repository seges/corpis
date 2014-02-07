package sk.seges.sesam.shared.model.dto;

import sk.seges.sesam.shared.model.api.PropertyHolder;
import sk.seges.sesam.shared.model.dao.MatchMode;

import java.util.Date;

public class FilterDTO {

	//TODO common with Filter constants - move to common class
	public static final String NE = "ne";
	public static final String LE = "le";
	public static final String ILIKE = "ilike";
	public static final String GT = "gt";
	public static final String GE = "ge";
	public static final String EQ = "eq";
	public static final String BETWEEN = "between";
	public static final String LT = "lt";
	public static final String LIKE = "like";

	public static ConjunctionDTO conjunction() {
		return new ConjunctionDTO();
	}

	public static BetweenExpressionDTO between(String property) {
		return new BetweenExpressionDTO(property, BETWEEN, new PropertyHolder(), new PropertyHolder());
	}

	public static BetweenExpressionDTO between(String property, Date low, Date high) {
		return new BetweenExpressionDTO(property, BETWEEN, new PropertyHolder(low), new PropertyHolder(high));
	}

	public static SimpleExpressionDTO eq(String property) {
		return new SimpleExpressionDTO(property, EQ, null);
	}

	public static SimpleExpressionDTO eq(String property, String value) {
		return new SimpleExpressionDTO(property, EQ, new PropertyHolder(value));
	}

	public static SimpleExpressionDTO ge(String property) {
		return new SimpleExpressionDTO(property, GE, null);
	}

	public static SimpleExpressionDTO ge(String property, String value) {
		return new SimpleExpressionDTO(property, GE, new PropertyHolder(value));
	}

	public static  SimpleExpressionDTO gt(String property) {
		return new SimpleExpressionDTO(property, GT, null);
	}

	public static SimpleExpressionDTO gt(String property, String value) {
		return new SimpleExpressionDTO(property, GT, new PropertyHolder(value));
	}

	public static LikeExpressionDTO ilike(String property) {
		LikeExpressionDTO result = new LikeExpressionDTO(property, false, MatchMode.ANYWHERE, new PropertyHolder());
		result.setOperation(LikeExpressionDTO.ILIKE);
		return result;
	}

	public static LikeExpressionDTO ilike(String property, PropertyHolder value) {
		LikeExpressionDTO result = new LikeExpressionDTO(property, false, MatchMode.ANYWHERE, value);
		result.setOperation(LikeExpressionDTO.ILIKE);
		return result;
	}

	public static LikeExpressionDTO ilike(String property, MatchMode matchMode, PropertyHolder value) {
		LikeExpressionDTO result = new LikeExpressionDTO(property, false, matchMode, value);
		result.setOperation(LikeExpressionDTO.ILIKE);
		return result;
	}

	public static NotNullExpressionDTO isNotNull(String property) {
		return new NotNullExpressionDTO(property);
	}

}
