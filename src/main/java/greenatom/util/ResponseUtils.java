package greenatom.util;

import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    public static ResponseEntity<?> responseEntityOf(boolean value) {
        if (value) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
