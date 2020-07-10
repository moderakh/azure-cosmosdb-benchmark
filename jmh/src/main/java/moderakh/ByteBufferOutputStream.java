package moderakh;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class ByteBufferOutputStream extends ByteArrayOutputStream {
    public ByteBufferOutputStream(int size) {
        super(size);
    }

    public ByteBufferOutputStream() {
        super();
    }

    /**
     * wraps the buffer used by this {@link ByteArrayOutputStream} to a ByteBuffer.
     *
     * This is more efficient than
     * {@link ByteArrayOutputStream#toByteArray()} as it doesn't copy data.
     * @return ByteBuffer
     */
    public ByteBuffer asByteBuffer() {
        return ByteBuffer.wrap(super.buf, 0, super.count);
    }
}
