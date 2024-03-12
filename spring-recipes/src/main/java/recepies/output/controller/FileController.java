package recepies.output.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/resource/{fileName}")
    public List<String> getFileResource(@PathVariable String fileName) throws IOException {
        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
        File resourceFromClassLoader = Optional.ofNullable(classLoader1.getResource(fileName))
                .map(URL::getPath)
                .map(File::new)
                .orElseThrow();

        return Files.readAllLines(resourceFromClassLoader.toPath());
    }

    @GetMapping("/stream/{fileName}")
    public String getFileStream(@PathVariable String fileName) throws IOException {
        ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = Optional.ofNullable(classLoader1.getResourceAsStream(fileName)).orElseThrow()) {
            return new String(inputStream.readAllBytes());
        }
    }

    @GetMapping("/spring/{fileName}")
    public String getFileSpring(@PathVariable String fileName) throws IOException {
        Resource resource = applicationContext.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + fileName);

        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes());
        }
    }

}
