package logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * The Logger class is a custom PrintStream that redirects output to two PrintStreams simultaneously.
 * It allows logging of output to multiple destinations.
 */
public class Logger extends PrintStream {
    private final PrintStream second;

    /**
     * Constructs a Logger object.
     *
     * @param main   The main OutputStream to which the output is redirected.
     * @param second The secondary PrintStream to which the output is also redirected.
     */
    public Logger(OutputStream main, PrintStream second) {
        super(main);
        this.second = second;
    }

    /**
     * Closes the Logger and releases any system resources associated with it.
     * The close method of the main PrintStream is called.
     */
    @Override
    public void close() {
        super.close();
    }

    /**
     * Flushes the output stream and forces any buffered output bytes to be written out.
     * The flush method of both the main and secondary PrintStreams is called.
     */
    @Override
    public void flush() {
        super.flush();
        second.flush();
    }

    /**
     * Writes a subarray of bytes.
     * The write method of both the main and secondary PrintStreams is called.
     *
     * @param buf  The data.
     * @param off  The start offset in the data.
     * @param len  The number of bytes to write.
     */
    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
        second.write(buf, off, len);
    }

    /**
     * Writes the specified byte to this output stream.
     * The write method of both the main and secondary PrintStreams is called.
     *
     * @param b The byte to write.
     */
    @Override
    public void write(int b) {
        super.write(b);
        second.write(b);
    }

    /**
     * Writes an array of bytes.
     * The write method of both the main and secondary PrintStreams is called.
     *
     * @param b The data.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        second.write(b);
    }
}
