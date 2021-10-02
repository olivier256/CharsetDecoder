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
public abstract class AbstractSingleByteCharacterSetEncoder extends CharsetEncoder
		implements ArrayEncoder, CharEncoder {

	protected AbstractSingleByteCharacterSetEncoder(Charset cs) {
		super(cs, 1.0f, 1.0f);
	}

	@Override
	public boolean canEncode(char c) {
		return c <= '\u00FF';
	}

	@Override
	public boolean isLegalReplacement(byte[] repl) {
		return true; // we accept any byte value
	}

	private final Surrogate.Parser sgp = new Surrogate.Parser();

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
				if (sgp.parse(c, sa, sp, sl) < 0)
					return sgp.error();
				return sgp.unmappableResult();
			}
			return CoderResult.UNDERFLOW;
		} finally {
			src.position(sp - src.arrayOffset());
			dst.position(dp - dst.arrayOffset());
		}
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
				if (sgp.parse(c, src) < 0)
					return sgp.error();
				return sgp.unmappableResult();
			}
			return CoderResult.UNDERFLOW;
		} finally {
			src.position(mark);
		}
	}

	protected CoderResult encodeLoop(CharBuffer src, ByteBuffer dst) {
		if (src.hasArray() && dst.hasArray())
			return encodeArrayLoop(src, dst);
		else
			return encodeBufferLoop(src, dst);
	}

	private byte repl = (byte) '?';

	@Override
	protected void implReplaceWith(byte[] newReplacement) {
		repl = newReplacement[0];
	}

	public int encode(char[] src, int sp, int len, byte[] dst) {
		int dp = 0;
		int sl = sp + Math.min(len, dst.length);
		while (sp < sl) {
			char c = src[sp++];
			if (c <= '\u00FF') {
				dst[dp++] = encode(c);
				continue;
			}
			if (Character.isHighSurrogate(c) && sp < sl && Character.isLowSurrogate(src[sp])) {
				if (len > dst.length) {
					sl++;
					len--;
				}
				sp++;
			}
			dst[dp++] = repl;
		}
		return dp;
	}
}
