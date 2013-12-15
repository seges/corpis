/**
 * 
 */
package sk.seges.corpis.pay.signer;

import java.security.MessageDigest;
import java.util.Collection;

import sk.seges.corpis.domain.shared.pay.HasKeyPaymentMethodSettings;
import sk.seges.corpis.domain.shared.pay.PaymentMethodSettings;

/**
 * @author stefan.sivak
 * 
 * @since Dec 12, 2013
 */
public class MD5Signer implements PaymentSigner {

	private static final String MD5 = "MD5";
	private static final String UTF8 = "UTF-8";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sk.seges.corpis.pay.signer.PaymentSigner#forgeSignature(sk.seges.corpis
	 * .domain.shared.pay.PaymentMethodSettings, java.util.Collection)
	 */
	@Override
	public String forgeSignature(PaymentMethodSettings settings, Collection<String> parametersInOrder) {
		if (!(settings instanceof HasKeyPaymentMethodSettings)) {
			throw new RuntimeException("Unable to forge signature for this type of settings = "
					+ settings.getClass().getName() + ", value = " + settings);
		}

		StringBuilder builder = new StringBuilder();
		for (String param : parametersInOrder) {
			if (param != null) {
				builder.append(param);
			}
		}

		HasKeyPaymentMethodSettings keySettings = (HasKeyPaymentMethodSettings) settings;
		builder.append(keySettings.getKey());

		String paramStr = builder.toString();

		try {
			byte[] wholeBytes = paramStr.getBytes(UTF8);
			MessageDigest md5 = MessageDigest.getInstance(MD5);
			md5.update(wholeBytes);
			byte[] md5Bytes = md5.digest();
			return toHexString(md5Bytes);
		} catch (Exception e) {
			throw new RuntimeException("Unable to create signature from params = " + paramStr, e);
		}
	}

	/**
	 * @param bytes
	 * @return
	 */
	private String toHexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			int j = (int) bytes[i];
			if (j < 0)
				j = 0x100 + j;
			if (j < 0x10)
				buf.append('0');
			buf.append(Integer.toHexString(j));
		}
		return buf.toString();
	}
}
