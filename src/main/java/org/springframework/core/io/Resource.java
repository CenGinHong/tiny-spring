package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源的抽象和访问接口
 *
 * @author chenJianhang
 * @version 1.0 2022/11/28 16:49
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
