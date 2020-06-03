package it.gruppopam.app_common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

public final class IOUtils {
    private static final String TAG = IOUtils.class.getName();

    @Inject
    public IOUtils() {
    }

    public void copy(InputStream sourceStream, OutputStream destinationStream) throws IOException {
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = sourceStream.read(buf)) > 0) {
                destinationStream.write(buf, 0, len);
            }
        } finally {
            sourceStream.close();
            destinationStream.close();
        }
    }
}
