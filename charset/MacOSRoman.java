package charset;

/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @see http://www.docjar.com/src/api/sun/nio/cs/ISO_8859_1.java
 */
public class MacOSRoman extends Charset implements HistoricallyNamedCharset {

	private static final String MAC_OS_ROMAN = "Mac_OS_Roman";

	private static final char[] CHARSET = new char[] { //
	'\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007', '\u0008', '\u0009', '\n', '\u000B', '\u000C', '\r', '\u000E',
			'\u000F', '\u0010', '\u2318', '\u21E7', '\u2325', '\u2303', '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001A', '\u001B',
			'\u001C', '\u001D', '\u001E', '\u001F', '\u0020', '\u0021', '\u0022', '\u0023', '\u0024', '\u0025', '\u0026', '\'', '\u0028', '\u0029',
			'\u002A', '\u002B', '\u002C', '\u002D', '\u002E', '\u002F', '\u0030', '\u0031', '\u0032', '\u0033', '\u0034', '\u0035', '\u0036',
			'\u0037', '\u0038', '\u0039', '\u003A', '\u003B', '\u003C', '\u003D', '\u003E', '\u003F', '\u0040', '\u0041', '\u0042', '\u0043',
			'\u0044', '\u0045', '\u0046', '\u0047', '\u0048', '\u0049', '\u004A', '\u004B', '\u004C', '\u004D', '\u004E', '\u004F', '\u0050',
			'\u0051', '\u0052', '\u0053', '\u0054', '\u0055', '\u0056', '\u0057', '\u0058', '\u0059', '\u005A', '\u005B', '\\', '\u005D', '\u005E',
			'\u005F', '\u0060', '\u0061', '\u0062', '\u0063', '\u0064', '\u0065', '\u0066', '\u0067', '\u0068', '\u0069', '\u006A', '\u006B',
			'\u006C', '\u006D', '\u006E', '\u006F', '\u0070', '\u0071', '\u0072', '\u0073', '\u0074', '\u0075', '\u0076', '\u0077', '\u0078',
			'\u0079', '\u007A', '\u007B', '\u007C', '\u007D', '\u007E', '\u007F', '\u00C4', '\u00C5', '\u00C7', '\u00C9', '\u00D1', '\u00D6',
			'\u00DC', '\u00E1', '\u00E0', '\u00E2', '\u00E4', '\u00E3', '\u00E5', '\u00E7', '\u00E9', '\u00E8', '\u00EA', '\u00EB', '\u00ED',
			'\u00EC', '\u00EE', '\u00EF', '\u00F1', '\u00F3', '\u00F2', '\u00F4', '\u00F6', '\u00F5', '\u00FA', '\u00F9', '\u00FB', '\u00FC',
			'\u2020', '\u00B0', '\u00A2', '\u00A3', '\u00A7', '\u2022', '\u00B6', '\u00DF', '\u00AE', '\u00A9', '\u2122', '\u00B4', '\u00A8',
			'\u2260', '\u00C6', '\u00D8', '\u221E', '\u00B1', '\u2264', '\u2265', '\u00A5', '\u00B5', '\u2202', '\u2211', '\u220F', '\u03C0',
			'\u222B', '\u00AA', '\u00BA', '\u03A9', '\u00E6', '\u00F8', '\u00BF', '\u00A1', '\u00AC', '\u221A', '\u0192', '\u2248', '\u2206',
			'\u00AB', '\u00BB', '\u2026', '\u00A0', '\u00C0', '\u00C3', '\u00D5', '\u0152', '\u0153', '\u2013', '\u2014', '\u201C', '\u201D',
			'\u2018', '\u2019', '\u00F7', '\u25CA', '\u00FF', '\u0178', '\u2044', '\u20AC', '\u2039', '\u203A', '\uFB01', '\uFB02', '\u2021',
			'\u00B7', '\u201A', '\u201E', '\u2030', '\u00C2', '\u00CA', '\u00C1', '\u00CB', '\u00C8', '\u00CD', '\u00CE', '\u00CF', '\u00CC',
			'\u00D3', '\u00D4', '\uF8FF', '\u00D2', '\u00DA', '\u00DB', '\u00D9', '\u0131', '\u02C6', '\u02DC', '\u00AF', '\u02D8', '\u02D9',
			'\u02DA', '\u00B8', '\u02DD', '\u02DB', '\u02C7'};

	public MacOSRoman() {
		super(MAC_OS_ROMAN, new String[] {MAC_OS_ROMAN});
	}

	@Override
	public String historicalName() {
		return MAC_OS_ROMAN;
	}

	@Override
	public boolean contains(final Charset cs) {
		return cs instanceof MacOSRoman;
	}

	@Override
	public CharsetDecoder newDecoder() {
		return new AbstractSingleByteCharacterSetCharsetDecoder(this) {

			@Override
			protected char byteToChar(final int b) {
				return CHARSET[b];
			}
		};
	}

	@Override
	public CharsetEncoder newEncoder() {
		return null;

	}

	public static void main(final String[] args) {
		boolean enabledAssert = false;
		assert enabledAssert = true;
		if (!enabledAssert) {
			throw new AssertionError("Lancer avec l'argument VM -ea");
		}

		final byte eGraveEncodedInMacOsRoman = (byte) 0x8f;
		String s = "La rivi_re";
		byte[] bytes = s.getBytes();
		bytes[7] = eGraveEncodedInMacOsRoman;
		String t = new String(bytes, new MacOSRoman());
		assert t.equals("La rivière");
	}
}
