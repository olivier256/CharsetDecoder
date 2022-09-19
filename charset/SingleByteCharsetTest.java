package charset;

import java.nio.charset.Charset;

/**
 * @see http://www.docjar.com/src/api/sun/nio/cs/ISO_8859_1.java
 */
public class SingleByteCharsetTest {

	public static void main(final String[] args) {
		boolean enabledAssert = false;
		assert enabledAssert = true;
		if (!enabledAssert) {
			throw new AssertionError("Lancer avec l'argument VM -ea");
		}
		new SingleByteCharsetTest().test();
	}

	public void test() {
		testCP850Decode();
		testCP850Encode();
		testMacOSRomanDecode();
	}

	public void testCP850Decode() {
		final Charset charset = SingleByteCharsets.CP_850;
		final byte A_GRAVE = (byte) 0xb7;
		String s = "_ partir de";
		byte[] bytes = s.getBytes();
		bytes[0] = A_GRAVE;
		String actual = new String(bytes, charset);
		String expected = "À partir de";
		assert actual.equals(expected) : "expected='" + expected + "';actual='" + actual + "'";

	}

	public void testCP850Encode() {
		Charset charset = SingleByteCharsets.CP_850;
		byte expected = (byte) 0xb7;
		byte actual = charset.encode("À partir de").array()[0];
		assert expected == actual : "expected=0x" + Integer.toHexString(actual) + ";actualCharacterAt0=0x"
				+ Integer.toHexString(actual);

	}

	public void testMacOSRomanDecode() {
		final Charset macOSRoman = SingleByteCharsets.MAC_OS_ROMAN;

		final byte E_GRAVE = (byte) 0x8f;
		String s = "La rivi_re";
		byte[] bytes = s.getBytes();
		bytes[7] = E_GRAVE;
		String actual = new String(bytes, macOSRoman);
		String expected = "La rivière";
		assert actual.equals(expected) : "expected='" + expected + "';actual='" + actual + "'";
	}

}
