package org.springframework.core.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/28 18:30
 */
public class ClassPathResource implements Resource {

    private final String path;

    public ClassPathResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(path + " cannot be opened because it does not exist");
        }
        return is;
    }

}

