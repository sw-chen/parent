package com.sw.minitor;

import java.io.IOException;
import java.io.Writer;

public class ResultStringWriter extends Writer {
    private int maxLength = 1024 * 4;

    private StringBuffer buf;

    /**
     * Create a new string writer using the specified initial string-buffer
     * size.
     *
     * @param initialSize
     *        The number of <tt>char</tt> values that will fit into this buffer
     *        before it is automatically expanded
     */
    public ResultStringWriter(int initialSize) {
        buf = new StringBuffer(Math.max(initialSize, 16));
        lock = buf;
    }

    public ResultStringWriter(int initialSize, int maxLength) {
        this(initialSize);
        this.maxLength = Math.max(maxLength, 64);
    }

    /**
     * Write a single character.
     */
    public void write(int c) {
        if (buf.length() < maxLength) {
            buf.append((char) c);
        } else {
            stop();
        }
    }

    private void stop() {
        buf.append("...");
        throw new ResultTooLongException(maxLength);
    }

    /**
     * Write a portion of an array of characters.
     *
     * @param  cbuf  Array of characters
     * @param  off   Offset from which to start writing characters
     * @param  len   Number of characters to write
     */
    public void write(char cbuf[], int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        int max = maxLength - buf.length();
        if (max <= 0) {
            stop();
        } else if (max >= len) {
            buf.append(cbuf, off, len);
        } else {
            buf.append(cbuf, off, max);
            stop();
        }
    }

    /**
     * Write a string.
     */
    public void write(String str) {
        int max = maxLength - buf.length();
        if (max <= 0) {
            stop();
        } else if (max >= str.length()) {
            buf.append(str);
        } else {
            buf.append(str.substring(0, max));
            stop();
        }
    }

    /**
     * Write a portion of a string.
     *
     * @param  str  String to be written
     * @param  off  Offset from which to start writing characters
     * @param  len  Number of characters to write
     */
    public void write(String str, int off, int len)  {
        int max = maxLength - buf.length();
        if (max <= 0) {
            stop();
        } else if (max >= len) {
            buf.append(str.substring(off, off + len));
        } else {
            buf.append(str.substring(off, off + max));
            stop();
        }
    }

    /**
     * Appends the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.toString()) </pre>
     *
     * <p> Depending on the specification of <tt>toString</tt> for the
     * character sequence <tt>csq</tt>, the entire sequence may not be
     * appended. For instance, invoking the <tt>toString</tt> method of a
     * character buffer will return a subsequence whose content depends upon
     * the buffer's position and limit.
     *
     * @param  csq
     *         The character sequence to append.  If <tt>csq</tt> is
     *         <tt>null</tt>, then the four characters <tt>"null"</tt> are
     *         appended to this writer.
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public ResultStringWriter append(CharSequence csq) {
        if (csq == null)
            write("null");
        else
            write(csq.toString());
        return this;
    }

    /**
     * Appends a subsequence of the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq, start,
     * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in
     * exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.subSequence(start, end).toString()) </pre>
     *
     * @param  csq
     *         The character sequence from which a subsequence will be
     *         appended.  If <tt>csq</tt> is <tt>null</tt>, then characters
     *         will be appended as if <tt>csq</tt> contained the four
     *         characters <tt>"null"</tt>.
     *
     * @param  start
     *         The index of the first character in the subsequence
     *
     * @param  end
     *         The index of the character following the last character in the
     *         subsequence
     *
     * @return  This writer
     *
     * @throws  IndexOutOfBoundsException
     *          If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt>
     *          is greater than <tt>end</tt>, or <tt>end</tt> is greater than
     *          <tt>csq.length()</tt>
     *
     * @since  1.5
     */
    public ResultStringWriter append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
        return this;
    }

    /**
     * Appends the specified character to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(c)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(c) </pre>
     *
     * @param  c
     *         The 16-bit character to append
     *
     * @return  This writer
     *
     * @since 1.5
     */
    public ResultStringWriter append(char c) {
        write(c);
        return this;
    }

    /**
     * Return the buffer's current value as a string.
     */
    public String toString() {
        return buf.toString();
    }

    /**
     * Return the string buffer itself.
     *
     * @return StringBuffer holding the current buffer value.
     */
    public StringBuffer getBuffer() {
        return buf;
    }

    /**
     * Flush the stream.
     */
    public void flush() {
    }

    /**
     * Closing a <tt>StringWriter</tt> has no effect. The methods in this
     * class can be called after the stream has been closed without generating
     * an <tt>IOException</tt>.
     */
    public void close() throws IOException {
    }
}
