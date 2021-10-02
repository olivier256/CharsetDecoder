package charset;

import java.nio.charset.Charset;

/**
 * @see http://www.docjar.com/src/api/sun/nio/cs/ISO_8859_1.java
 */
public class SingleByteCharsetTest {
	public static final String MAC_OS_ROMAN_CANONICAL_NAME = "MAC_OS_ROMAN";
	private static final char[] MAC_OS_ROMAN_CHARSET = new char[] { //
			'\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007', //
			'\u0008', '\u0009', '\n', '\u000B', '\u000C', '\r', '\u000E', '\u000F', //
			'\u0010', '\u2318', '\u21E7', '\u2325', '\u2303', '\u0015', '\u0016', '\u0017', //
			'\u0018', '\u0019', '\u001A', '\u001B', '\u001C', '\u001D', '\u001E', '\u001F', //
			'\u0020', '\u0021', '\u0022', '\u0023', '\u0024', '\u0025', '\u0026', '\'', //
			'\u0028', '\u0029', '\u002A', '\u002B', '\u002C', '\u002D', '\u002E', '\u002F', //
			'\u0030', '\u0031', '\u0032', '\u0033', '\u0034', '\u0035', '\u0036', '\u0037', //
			'\u0038', '\u0039', '\u003A', '\u003B', '\u003C', '\u003D', '\u003E', '\u003F', //
			'\u0040', '\u0041', '\u0042', '\u0043', '\u0044', '\u0045', '\u0046', '\u0047', //
			'\u0048', '\u0049', '\u004A', '\u004B', '\u004C', '\u004D', '\u004E', '\u004F', //
			'\u0050', '\u0051', '\u0052', '\u0053', '\u0054', '\u0055', '\u0056', '\u0057', //
			'\u0058', '\u0059', '\u005A', '\u005B', '\\', '\u005D', '\u005E', '\u005F', //
			'\u0060', '\u0061', '\u0062', '\u0063', '\u0064', '\u0065', '\u0066', '\u0067', //
			'\u0068', '\u0069', '\u006A', '\u006B', '\u006C', '\u006D', '\u006E', '\u006F', //
			'\u0070', '\u0071', '\u0072', '\u0073', '\u0074', '\u0075', '\u0076', '\u0077', //
			'\u0078', '\u0079', '\u007A', '\u007B', '\u007C', '\u007D', '\u007E', '\u007F', //

			'\u00C4', '\u00C5', '\u00C7', '\u00C9', '\u00D1', '\u00D6', '\u00DC', '\u00E1', //
			'\u00E0', '\u00E2', '\u00E4', '\u00E3', '\u00E5', '\u00E7', '\u00E9', '\u00E8', //
			'\u00EA', '\u00EB', '\u00ED', '\u00EC', '\u00EE', '\u00EF', '\u00F1', '\u00F3', //
			'\u00F2', '\u00F4', '\u00F6', '\u00F5', '\u00FA', '\u00F9', '\u00FB', '\u00FC', //
			'\u2020', '\u00B0', '\u00A2', '\u00A3', '\u00A7', '\u2022', '\u00B6', '\u00DF', //
			'\u00AE', '\u00A9', '\u2122', '\u00B4', '\u00A8', '\u2260', '\u00C6', '\u00D8', //
			'\u221E', '\u00B1', '\u2264', '\u2265', '\u00A5', '\u00B5', '\u2202', '\u2211', //
			'\u220F', '\u03C0', '\u222B', '\u00AA', '\u00BA', '\u03A9', '\u00E6', '\u00F8', //
			'\u00BF', '\u00A1', '\u00AC', '\u221A', '\u0192', '\u2248', '\u2206', '\u00AB', //
			'\u00BB', '\u2026', '\u00A0', '\u00C0', '\u00C3', '\u00D5', '\u0152', '\u0153', //
			'\u2013', '\u2014', '\u201C', '\u201D', '\u2018', '\u2019', '\u00F7', '\u25CA', //
			'\u00FF', '\u0178', '\u2044', '\u20AC', '\u2039', '\u203A', '\uFB01', '\uFB02', //
			'\u2021', '\u00B7', '\u201A', '\u201E', '\u2030', '\u00C2', '\u00CA', '\u00C1', //
			'\u00CB', '\u00C8', '\u00CD', '\u00CE', '\u00CF', '\u00CC', '\u00D3', '\u00D4', //
			'\uF8FF', '\u00D2', '\u00DA', '\u00DB', '\u00D9', '\u0131', '\u02C6', '\u02DC', //
			'\u00AF', '\u02D8', '\u02D9', '\u02DA', '\u00B8', '\u02DD', '\u02DB', '\u02C7' };

	private static final String CP_850_CANONICAL_NAME = "CP_850";
	private static final char[] CP_850_CHARSET = { //
			'\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007', //
			'\u0008', '\u0009', '\n', '\u000B', '\u000C', '\r', '\u000E', '\u000F', //
			'\u0010', '\u2318', '\u21E7', '\u2325', '\u2303', '\u0015', '\u0016', '\u0017', //
			'\u0018', '\u0019', '\u001A', '\u001B', '\u001C', '\u001D', '\u001E', '\u001F', //
			'\u0020', '\u0021', '\u0022', '\u0023', '\u0024', '\u0025', '\u0026', '\'', //
			'\u0028', '\u0029', '\u002A', '\u002B', '\u002C', '\u002D', '\u002E', '\u002F', //
			'\u0030', '\u0031', '\u0032', '\u0033', '\u0034', '\u0035', '\u0036', '\u0037', //
			'\u0038', '\u0039', '\u003A', '\u003B', '\u003C', '\u003D', '\u003E', '\u003F', //
			'\u0040', '\u0041', '\u0042', '\u0043', '\u0044', '\u0045', '\u0046', '\u0047', //
			'\u0048', '\u0049', '\u004A', '\u004B', '\u004C', '\u004D', '\u004E', '\u004F', //
			'\u0050', '\u0051', '\u0052', '\u0053', '\u0054', '\u0055', '\u0056', '\u0057', //
			'\u0058', '\u0059', '\u005A', '\u005B', '\\', '\u005D', '\u005E', '\u005F', //
			'\u0060', '\u0061', '\u0062', '\u0063', '\u0064', '\u0065', '\u0066', '\u0067', //
			'\u0068', '\u0069', '\u006A', '\u006B', '\u006C', '\u006D', '\u006E', '\u006F', //
			'\u0070', '\u0071', '\u0072', '\u0073', '\u0074', '\u0075', '\u0076', '\u0077', //
			'\u0078', '\u0079', '\u007A', '\u007B', '\u007C', '\u007D', '\u007E', '\u007F', //

			'\u00C7', '\u00FC', '\u00E9', '\u00E2', '\u00E4', '\u00E0', '\u00E5', '\u00E7', //
			'\u00EA', '\u00EB', '\u00E8', '\u00EF', '\u00EE', '\u00EC', '\u00C4', '\u00C5', //
			'\u00C9', '\u00E6', '\u00C6', '\u00F4', '\u00F6', '\u00F2', '\u00FB', '\u00F9', //
			'\u00FF', '\u00D6', '\u00DC', '\u00F8', '\u00A3', '\u00D8', '\u00D7', '\u0192', //
			'\u00E1', '\u00ED', '\u00F3', '\u00FA', '\u00F1', '\u00D1', '\u00AA', '\u00BA', //
			'\u00BF', '\u00AE', '\u00AC', '\u00BD', '\u00BC', '\u00A1', '\u00AB', '\u00BB', //
			'\u2591', '\u2592', '\u2593', '\u2502', '\u2524', '\u00C1', '\u00C2', '\u00C0', //
			'\u00A9', '\u2563', '\u2551', '\u2557', '\u255D', '\u00A2', '\u00A5', '\u2510', //
			'\u2514', '\u2534', '\u252C', '\u251C', '\u2500', '\u253C', '\u00E3', '\u00C3', //
			'\u255A', '\u2554', '\u2569', '\u2566', '\u2560', '\u2550', '\u256C', '\u00A4', //
			'\u00F0', '\u00D0', '\u00CA', '\u00CB', '\u00C8', '\u0131', '\u00CD', '\u00CE', //
			'\u00CF', '\u2518', '\u250C', '\u2588', '\u2584', '\u00A6', '\u00CC', '\u2580', //
			'\u00D3', '\u00DF', '\u00D4', '\u00D2', '\u00F5', '\u00D5', '\u00B5', '\u00FE', //
			'\u00DE', '\u00DA', '\u00DB', '\u00D9', '\u00FD', '\u00DD', '\u00AF', '\u00B4', //
			'\u00AD', '\u00B1', '\u2017', '\u00BE', '\u00B6', '\u00A7', '\u00F7', '\u00B8', //
			'\u00B0', '\u00A8', '\u00B7', '\u00B9', '\u00B3', '\u00B2', '\u25A0', '\u00A0' //
	};

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
		Charset charset = new SingleByteCharset(CP_850_CANONICAL_NAME, CP_850_CHARSET);
		final byte A_GRAVE = (byte) 0xb7;
		String s = "_ partir de";
		byte[] bytes = s.getBytes();
		bytes[0] = A_GRAVE;
		String actual = new String(bytes, charset);
		String expected = "À partir de";
		assert actual.equals(expected) : "expected='" + expected + "';actual='" + actual + "'";

	}

	public void testCP850Encode() {
		Charset charset = new SingleByteCharset(CP_850_CANONICAL_NAME, CP_850_CHARSET);
		byte expected = (byte) 0xb7;
		byte actual = charset.encode("À partir de").array()[0];
		assert expected == actual
				: "expected=0x" + Integer.toHexString(actual) + ";actualCharacterAt0=0x" + Integer.toHexString(actual);

	}

	public void testMacOSRomanDecode() {
		Charset macOSRoman = new SingleByteCharset(MAC_OS_ROMAN_CANONICAL_NAME, MAC_OS_ROMAN_CHARSET);

		final byte E_GRAVE = (byte) 0x8f;
		String s = "La rivi_re";
		byte[] bytes = s.getBytes();
		bytes[7] = E_GRAVE;
		String actual = new String(bytes, macOSRoman);
		String expected = "La rivière";
		assert actual.equals(expected) : "expected='" + expected + "';actual='" + actual + "'";
	}

}
