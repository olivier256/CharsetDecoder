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
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @see http://www.docjar.com/src/api/sun/nio/cs/ISO_8859_1.java
 */
public class SingleByteCharset extends Charset {
	private final char[] charset;

	public SingleByteCharset(String canonicalName, char[] charset) {
		super(canonicalName, new String[] { canonicalName });
		this.charset = charset;
	}

	@Override
	public boolean contains(final Charset charsetD) {
		ByteBuffer bb = ByteBuffer.allocate(Byte.MAX_VALUE - Byte.MIN_VALUE + 1);
		for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
			bb.put(b);
		}
		char[] charactersRepresentableInC = decode(bb).array();
		char[] charactersRepresentableInD = charsetD.decode(bb).array();
		for (char d : charactersRepresentableInD) {
			if (!contains(charactersRepresentableInC, d)) {
				return false;
			}
		}
		return true;
	}

	private boolean contains(char[] t, char c) {
		for (char _c : t) {
			if (_c == c) {
				return true;
			}
		}
		return false;
	}

	@Override
	public CharsetDecoder newDecoder() {
		return new SingleByteCharsetDecoder(this, charset);
	}

	@Override
	public CharsetEncoder newEncoder() {
		return new SingleByteCharacterSetEncoder(this, charset);
	}

}
