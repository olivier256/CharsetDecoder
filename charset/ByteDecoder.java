package charset;

public interface ByteDecoder {
	/** @return (char)(b & 0xff) */
	char decode(final int i);

}
