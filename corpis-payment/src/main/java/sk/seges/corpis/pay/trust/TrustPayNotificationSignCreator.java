/**
 * 
 */
package sk.seges.corpis.pay.trust;

import java.util.ArrayList;
import java.util.List;

import sk.seges.corpis.domain.shared.pay.trust.TrustPaySettings;
import sk.seges.corpis.pay.signer.PaymentSigner;

/**
 * @author stefan.sivak
 * 
 * @since Dec 15, 2013
 */
public class TrustPayNotificationSignCreator {

	private final PaymentSigner signer;

	/**
	 * 
	 */
	public TrustPayNotificationSignCreator(PaymentSigner signer) {
		this.signer = signer;
	}

	/**
	 * @param settings
	 * @param typ
	 * @param amt
	 * @param cur
	 * @param ref
	 * @param res
	 * @param tid
	 * @param oid
	 * @param tss
	 * @return
	 */
	public String createSign(TrustPaySettings settings, String typ, String amt, String cur, String ref, String res,
			String tid, String oid, String tss) {

		List<String> paramList = new ArrayList<String>();
		paramList.add(settings.getKey());
		paramList.add(settings.getMid());
		paramList.add(typ);
		paramList.add(amt);
		paramList.add(cur);
		paramList.add(ref);
		paramList.add(res);
		paramList.add(tid);
		paramList.add(oid);
		paramList.add(tss);

		return signer.forgeSignature(settings, paramList);
	}

}
