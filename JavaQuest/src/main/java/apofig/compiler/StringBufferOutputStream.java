package apofig.compiler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: oleksandr.baglai
 * Date: 2/13/13
 * Time: 8:03 PM
 */
public class StringBufferOutputStream extends OutputStream {
    private StringBuffer buffer = new StringBuffer();

    @Override
    public void write(int b) throws IOException {
        buffer.append((char)b);
    }

    public String get() {
        String result = buffer.toString();
        buffer = new StringBuffer();
        return result;
    }
}
