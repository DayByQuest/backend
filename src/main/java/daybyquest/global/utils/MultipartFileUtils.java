package daybyquest.global.utils;

import daybyquest.global.error.exception.InvalidFileException;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class MultipartFileUtils {

    public static InputStream getInputStream(final MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new InvalidFileException();
        }
    }
}
