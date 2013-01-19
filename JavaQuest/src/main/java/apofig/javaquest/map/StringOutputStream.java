package apofig.javaquest.map;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * User: oleksandr.baglai
 * Date: 1/19/13
 * Time: 2:15 PM
 */
public class StringOutputStream extends ByteArrayOutputStream {

    public String getResult() {
        return new String(toByteArray());
    }

}
