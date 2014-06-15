/**
 * 
 */
package sk.seges.corpis.domain.shared.pay.tatra;

/**
 * Represents possible values of the RES(ULT) parameter ( {@link TatraPayParameter.RESULT} ).
 * 
 * @author ladislav.gazo
 */
public enum TatraPayResult {
	/** Transaction processed correctly. */
	OK,
	/** Transaction was not processed correctly. */
	FAIL,
	/** Transaction was not processed and the bank does not know the final result. */
	TOUT
}
