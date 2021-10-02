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

package charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * @see http://www.docjar.com/src/api/sun/nio/cs/ISO_8859_1.java
 */
public class SingleByteCharacterSetEncoder extends CharsetEncoder {
	private final Surrogate.Parser parser;
	private final char[] charset;

	protected SingleByteCharacterSetEncoder(Charset cs, final char[] charset) {
		super(cs, 1.0f, 1.0f);
		parser = new Surrogate.Parser();
		this.charset = charset;
	}

	@Override
	public boolean canEncode(char c) {
		return c <= '\u00FF';
	}

	@Override
	public boolean isLegalReplacement(byte[] repl) {
		return true; // we accept any byte value
	}

	@Override
	protected CoderResult encodeLoop(CharBuffer src, ByteBuffer dst) {
		if (src.hasArray() && dst.hasArray())
			return encodeArrayLoop(src, dst);
		else
			return encodeBufferLoop(src, dst);
	}

	private CoderResult encodeArrayLoop(CharBuffer src, ByteBuffer dst) {
		char[] sa = src.array();
		int sp = src.arrayOffset() + src.position();
		int sl = src.arrayOffset() + src.limit();
		if (sp > sl) {
			throw new AssertionError();
		}
		byte[] da = dst.array();
		int dp = dst.arrayOffset() + dst.position();
		int dl = dst.arrayOffset() + dst.limit();
		if (dp > dl) {
			throw new AssertionError();
		}
		try {
			while (sp < sl) {
				char c = sa[sp];
				if (c <= '\u00FF') {
					if (dp >= dl)
						return CoderResult.OVERFLOW;
					byte b = encode(c);
					da[dp++] = b;
					sp++;
					continue;
				}
				if (parser.parse(c, sa, sp, sl) < 0)
					return parser.error();
				return parser.unmappableResult();
			}
			return CoderResult.UNDERFLOW;
		} finally {
			src.position(sp - src.arrayOffset());
			dst.position(dp - dst.arrayOffset());
		}
	}

	private byte encode(char c) {
		for (int b = 0; b < 256; b++) {
			char _c = charset[b];
			if (_c == c) {
				return (byte) b;
			}
		}
		throw new IllegalArgumentException("Unable to find char '" + c + "'");

	}

	private CoderResult encodeBufferLoop(CharBuffer src, ByteBuffer dst) {
		int mark = src.position();
		try {
			while (src.hasRemaining()) {
				char c = src.get();
				if (c <= '\u00FF') {
					if (!dst.hasRemaining())
						return CoderResult.OVERFLOW;
					dst.put(encode(c));
					mark++;
					continue;
				}
				if (parser.parse(c, src) < 0)
					return parser.error();
				return parser.unmappableResult();
			}
			return CoderResult.UNDERFLOW;
		} finally {
			src.position(mark);
		}
	}

	private static class Surrogate {

		private Surrogate() {
		}

		/**
		 * Surrogate parsing support. Charset implementations may use instances of this
		 * class to handle the details of parsing UTF-16 surrogate pairs.
		 */
		public static class Parser {

			private int character; // UCS-4

			private CoderResult error = CoderResult.UNDERFLOW;

			private boolean isPair;

			/**
			 * If the previous parse operation detected an error, return the object
			 * describing that error.
			 */
			public CoderResult error() {
				assert error != null;
				return error;
			}

			/**
			 * Returns an unmappable-input result object, with the appropriate input length,
			 * for the previously-parsed character.
			 */
			public CoderResult unmappableResult() {
				assert error == null;
				return CoderResult.unmappableForLength(isPair ? 2 : 1);
			}

			/**
			 * Parses a UCS-4 character from the given source buffer, handling surrogates.
			 * 
			 * @param c  The first character
			 * @param in The source buffer, from which one more character will be consumed
			 *           if c is a high surrogate
			 * @returns Either a parsed UCS-4 character, in which case the isPair() and
			 *          increment() methods will return meaningful values, or -1, in which
			 *          case error() will return a descriptive result object
			 */
			public int parse(final char c, final CharBuffer in) {
				if (Character.isHighSurrogate(c)) {
					if (!in.hasRemaining()) {
						error = CoderResult.UNDERFLOW;
						return -1;
					}
					char d = in.get();
					if (Character.isLowSurrogate(d)) {
						character = Character.toCodePoint(c, d);
						isPair = true;
						error = null;
						return character;
					}
					error = CoderResult.malformedForLength(1);
					return -1;
				}
				if (Character.isLowSurrogate(c)) {
					error = CoderResult.malformedForLength(1);
					return -1;
				}
				character = c;
				isPair = false;
				error = null;
				return character;
			}

			/**
			 * Parses a UCS-4 character from the given source buffer, handling surrogates.
			 * 
			 * @param c  The first character
			 * @param ia The input array, from which one more character will be consumed if
			 *           c is a high surrogate
			 * @param ip The input index
			 * @param il The input limit
			 * @returns Either a parsed UCS-4 character, in which case the isPair() and
			 *          increment() methods will return meaningful values, or -1, in which
			 *          case error() will return a descriptive result object
			 */
			public int parse(final char c, final char[] ia, final int ip, final int il) {
				if (ia[ip] != c) {
					throw new AssertionError();
				}
				if (Character.isHighSurrogate(c)) {
					if (il - ip < 2) {
						error = CoderResult.UNDERFLOW;
						return -1;
					}
					char d = ia[ip + 1];
					if (Character.isLowSurrogate(d)) {
						character = Character.toCodePoint(c, d);
						isPair = true;
						error = null;
						return character;
					}
					error = CoderResult.malformedForLength(1);
					return -1;
				}
				if (Character.isLowSurrogate(c)) {
					error = CoderResult.malformedForLength(1);
					return -1;
				}
				character = c;
				isPair = false;
				error = null;
				return character;
			}

		}

	}

}
