package ldjp.jassistant.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * A ClassLoader to load class from a class file
 */
public class FileClassLoader extends ClassLoader {

    public Class<?> findClass(String name) {
        try {
            byte[] b = loadClassData(name);
            return defineClass(null, b, 0, b.length);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }

    private byte[] loadClassData(String name) throws IOException {
        // load the class data from file
        File file = new File(name);
        FileInputStream fis = new FileInputStream(file);
        int len = (int) file.length();

        byte[] b = new byte[len];
        fis.read(b, 0, len);
        fis.close();
        return b;
    }
}
