package it.gruppopam.app_common.utils;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.fail;

public class IOUtilsTest {

    @Test
    public void copyTest() {
        InputStream inputStream = new ByteArrayInputStream("test".getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            new IOUtils().copy(inputStream, outputStream);

        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }
    }
}
