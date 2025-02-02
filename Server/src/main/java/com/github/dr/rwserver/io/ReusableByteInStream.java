package com.github.dr.rwserver.io;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;

/**
 * @author Dr
 */
public class ReusableByteInStream extends ByteArrayInputStream {
    public ReusableByteInStream() {
        super(new byte[0]);
    }

    public int position() {
        return this.pos;
    }

    public void setBytes(@NotNull final byte[] bytes) {
        this.pos = 0;
        this.count = bytes.length;
        this.mark = 0;
        this.buf = bytes;
    }

    public void setBytes(@NotNull final byte[] bytes,final int offset,final int length) {
        this.buf = bytes;
        this.pos = offset;
        this.count = Math.min(offset + length, bytes.length);
        this.mark = offset;
    }
}
