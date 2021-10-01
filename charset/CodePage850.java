package charset;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CodePage850 extends AbstractSingleByteCharset {
	public static final String CANONICAL_NAME = "CP_850";
	private static final char[] CHARSET;
	static {
		final char[] upperCharset = new char[] { //
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
				'\u00B0', '\u00A8', '\u00B7', '\u00B9', '\u00B3', '\u00B2', '\u25A0', '\u00A0' };
		CHARSET = Arrays.copyOf(ASCII, 256);
		for (int i = 0; i < upperCharset.length; i++) {
			CHARSET[128 + i] = upperCharset[i];
		}
	}

	public CodePage850() {
		super(CANONICAL_NAME);
	}

	@Override
	public char decode(int b) {
		return CHARSET[b];
	}

	public static void main(final String[] args) {
		String line;
		try (InputStream is = new FileInputStream(args[0]);
				BufferedReader br = new BufferedReader(new InputStreamReader(is, new CodePage850()))) {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
