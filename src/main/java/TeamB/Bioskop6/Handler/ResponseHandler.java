package TeamB.Bioskop6.Handler;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String errorMessage, HttpStatus status, HttpHeaders headers, ZonedDateTime accessedTime, Object response){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("headers", headers);
        responseMap.put("status", status);
        responseMap.put("error_message", errorMessage);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("data", response);
        return new ResponseEntity<Object>(responseMap, status);
    }
}
