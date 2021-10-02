package charset;

import java.nio.charset.Charset;

/**
 * @see http://www.docjar.com/src/api/sun/nio/cs/ISO_8859_1.java
 */
public class CodePage850Test {

	public void test() {
		testDecode();
		testEncode();
	}

	public void testDecode() {
		Charset charset = new CodePage850();
		final byte A_GRAVE = (byte) 0xb7;
		String s = "_ partir de";
		byte[] bytes = s.getBytes();
		bytes[0] = A_GRAVE;
		String actual = new String(bytes, charset);
		String expected = "À partir de";
		assert actual.equals(expected) : "expected='" + expected + "';actual='" + actual + "'";

	}

	public void testEncode() {
		Charset charset = new CodePage850();
		byte expected = (byte) 0xb7;
		byte actual = charset.encode("À partir de").array()[0];
		assert expected == actual
				: "expected=0x" + Integer.toHexString(actual) + ";actualCharacterAt0=0x" + Integer.toHexString(actual);

	}

	public static void main(final String[] args) {
		boolean enabledAssert = false;
		assert enabledAssert = true;
		if (!enabledAssert) {
			throw new AssertionError("Lancer avec l'argument VM -ea");
		}

		new CodePage850Test().test();

	}

}
