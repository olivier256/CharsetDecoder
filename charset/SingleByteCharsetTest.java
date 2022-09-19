package charset;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
		final byte A_GRAVE_IN_CP850 = (byte) 0xb7;
		byte[] byteArray = new byte[] { A_GRAVE_IN_CP850 };
		try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
				InputStreamReader isw = new InputStreamReader(bais, charset)) {
			char c = (char) isw.read();
			assert 'À' == c : "expected=À;actual=" + c;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testCP850Encode() {
		Charset charset = SingleByteCharsets.CP_850;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(1);
				OutputStreamWriter os = new OutputStreamWriter(baos, charset)) {
			os.write("À");
			os.flush();
			byte[] byteArray = baos.toByteArray();
			byte actual = byteArray[0];
			assert (byte) 0xb7 == actual : "expected=0xb7;actual=0x" + Integer.toHexString(actual);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
