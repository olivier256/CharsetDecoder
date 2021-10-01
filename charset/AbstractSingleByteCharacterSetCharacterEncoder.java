package charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * @see http://www.docjar.com/src/api/sun/nio/cs/ISO_8859_1.java
 */
public abstract class AbstractSingleByteCharacterSetCharacterEncoder extends CharsetEncoder implements ArrayEncoder {

	protected AbstractSingleByteCharacterSetCharacterEncoder(Charset cs) {
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
		/**
		 * assert (sp <= sl); sp = (sp <= sl ? sp : sl);
		 */
		if (sp > sl) {
			throw new AssertionError();
		}
		byte[] da = dst.array();
		int dp = dst.arrayOffset() + dst.position();
		int dl = dst.arrayOffset() + dst.limit();
		/**
		 * assert (dp <= dl); dp = (dp <= dl ? dp : dl);
		 */
		if (dp > dl) {
			throw new AssertionError();
		}
		try {
			while (sp < sl) {
				char c = sa[sp];
				if (c <= '\u00FF') {
					if (dp >= dl)
						return CoderResult.OVERFLOW;
					byte b = charToByte(c);
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

	/** @return (byte) c */
	protected abstract byte charToByte(char c);

	private CoderResult encodeBufferLoop(CharBuffer src, ByteBuffer dst) {
		int mark = src.position();
		try {
			while (src.hasRemaining()) {
				char c = src.get();
				if (c <= '\u00FF') {
					if (!dst.hasRemaining())
						return CoderResult.OVERFLOW;
					dst.put(charToByte(c));
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
				dst[dp++] = charToByte(c);
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
