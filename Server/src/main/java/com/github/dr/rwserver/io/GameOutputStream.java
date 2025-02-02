package com.github.dr.rwserver.io;

import com.github.dr.rwserver.io.output.DisableSyncByteArrayOutputStream;
import com.github.dr.rwserver.util.zip.gzip.GzipEncoder;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Dr
 */
public class GameOutputStream {
	final DisableSyncByteArrayOutputStream buffer = new DisableSyncByteArrayOutputStream();
	final DataOutputStream stream = new DataOutputStream(buffer);

    @NotNull
    public Packet createPacket() {
        try {
            this.stream.flush();
            this.buffer.flush();
            return new Packet(0,buffer.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    @NotNull
	public Packet createPacket(int type) {
        try {
            this.stream.flush();
            this.buffer.flush();
            return new Packet(type,this.buffer.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    @NotNull
    public Packet createPackets(int type) {
        try {
            this.stream.flush();
            this.buffer.flush();
            return new Packet(type,this.buffer.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    public void writeByte(int val) throws IOException {
        this.stream.writeByte(val);
    }

    public void writeBytes(@NotNull byte[] val) throws IOException {
        this.buffer.writeBytes(val);
    }

    public void writeBoolean(boolean val) throws IOException {
        this.stream.writeBoolean(val);
    }

    public void writeInt(int val) throws IOException {
        this.stream.writeInt(val);
    }

    public void writeShort(short val) throws IOException {
        this.stream.writeShort(val);
    }

    public void writeFloat(float val) throws IOException {
        this.stream.writeFloat(val);
    }

    public void writeLong(long val) throws IOException {
        this.stream.writeLong(val);
    }

    public void writeString(@NotNull String val) throws IOException {
        this.stream.writeUTF(val);
    }

    public void flushData(@NotNull GameInputStream inputStream) throws IOException {
        inputStream.buffer.transferTo(this.buffer);
    }

    public void flushEncodeData(@NotNull GzipEncoder enc) throws IOException {
        enc.closeGzip();
        this.writeString(enc.str);
        this.writeInt(enc.buffer.size());
        enc.buffer.writeTo(this.stream);
        stream.flush();
    }

    public void flushMapData(int mapSize, @NotNull byte[] bytes) throws IOException {
        this.writeInt(mapSize);
        this.stream.write(bytes);
    }

    private void close() {
        try {
            this.buffer.close();
        } catch (IOException e) {
        } finally {
            try {
                this.stream.close();
            } catch (IOException e) {
            }
        }
    }
}
