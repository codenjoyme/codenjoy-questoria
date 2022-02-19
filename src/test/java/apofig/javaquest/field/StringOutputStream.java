package apofig.javaquest.field;

import java.io.ByteArrayOutputStream;

public class StringOutputStream extends ByteArrayOutputStream {

    public String getResult() {
        return new String(toByteArray());
    }

}
