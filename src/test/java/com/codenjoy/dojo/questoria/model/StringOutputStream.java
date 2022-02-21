package com.codenjoy.dojo.questoria.model;

import java.io.ByteArrayOutputStream;

public class StringOutputStream extends ByteArrayOutputStream {

    public String getResult() {
        return new String(toByteArray());
    }

}
