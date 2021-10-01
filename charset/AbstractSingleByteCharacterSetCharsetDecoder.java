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
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * @see http://www.docjar.com/src/api/sun/nio/cs/ISO_8859_1.java
 */
public abstract class AbstractSingleByteCharacterSetCharsetDecoder extends CharsetDecoder implements ArrayDecoder {
	protected AbstractSingleByteCharacterSetCharsetDecoder(final Charset cs) {
		super(cs, 1.0f, 1.0f);
	}

	@Override
	protected CoderResult decodeLoop(final ByteBuffer src, final CharBuffer dst) {
		if (src.hasArray() && dst.hasArray()) {
			return decodeArrayLoop(src, dst);
		} else {
			return decodeBufferLoop(src, dst);
		}
	}

	private CoderResult decodeArrayLoop(final ByteBuffer src, final CharBuffer dst) {
		byte[] sa = src.array();
		int sp = src.arrayOffset() + src.position();
		int sl = src.arrayOffset() + src.limit();
		if (sp > sl) {
			throw new AssertionError();
		}
		/**
		 * assert sp <= sl; sp = sp <= sl ? sp : sl;
		 */
		char[] da = dst.array();
		int dp = dst.arrayOffset() + dst.position();
		int dl = dst.arrayOffset() + dst.limit();
		if (dp > dl) {
			throw new AssertionError();
		}
		/**
		 * assert dp <= dl; dp = dp <= dl ? dp : dl;
		 */
		try {
			while (sp < sl) {
				byte b = sa[sp];
				if (dp >= dl) {
					return CoderResult.OVERFLOW;
				}
				final char c = byteToChar(b & 0xff);
				da[dp++] = c;
				sp++;
			}
			return CoderResult.UNDERFLOW;
		} finally {
			src.position(sp - src.arrayOffset());
			dst.position(dp - dst.arrayOffset());
		}
	}

	/** @return (char)(b & 0xff) */
	protected abstract char byteToChar(final int i);

	private CoderResult decodeBufferLoop(final ByteBuffer src, final CharBuffer dst) {
		int mark = src.position();
		try {
			while (src.hasRemaining()) {
				byte b = src.get();
				if (!dst.hasRemaining()) {
					return CoderResult.OVERFLOW;
				}
				final char c = byteToChar(b & 0xff);
				dst.put(c);
				mark++;
			}
			return CoderResult.UNDERFLOW;
		} finally {
			src.position(mark);
		}
	}

	@Override
	public int decode(final byte[] src, int sp, int len, final char[] dst) {
		if (len > dst.length) {
			len = dst.length;
		}
		int dp = 0;
		while (dp < len) {
			byte b = src[sp++];
			final char c = byteToChar(b & 0xff);
			dst[dp++] = c;
		}
		return dp;
	}
}
