package it.gov.innovazione.lode;

import org.owasp.encoder.Encode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LodeControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(getErrorPage(ex), headers, HttpStatus.BAD_REQUEST);
    }

    private String getErrorPage(Exception e) {
        return "<html>" +
                "<head>" +
                "<title>LODE error</title>" +
                "</head>" +
                "<body>" +
                "<h2>LODE error</h2>" +
                "<p><strong>Reason: </strong>" + Encode.forHtml(e.getMessage()) + "</p>" +
                "</body>" +
                "</html>";
    }
}
