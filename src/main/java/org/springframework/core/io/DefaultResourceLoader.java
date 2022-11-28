package org.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author chenJianhang
 * @version 1.0 2022/11/28 18:33
 */
public class DefaultResourceLoader implements ResourceLoader {

    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    @Override
    public Resource getResource(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // classpath下的资源,截取后面那段
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                // 尝试当成网络资源处理
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // 当成文件系统下的资源处理
                return new FileSystemResource(location);
            }
        }
    }

}
