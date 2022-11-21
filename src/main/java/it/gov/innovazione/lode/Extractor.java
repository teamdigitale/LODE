package it.gov.innovazione.lode;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Service
@RequiredArgsConstructor
@Slf4j
public class Extractor {

    static final Collection<String> MIME_TYPES = List.of(
            "application/rdf+xml",
            "text/turtle",
            "application/x-turtle",
            "text/xml",
            "text/plain",
            "*/*");
    private final RestTemplate restTemplate;

    @SneakyThrows
    public String extract(URL url) {
        ResponseEntity<String> responseEntity = null;
        for (String mimeType : MIME_TYPES) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", mimeType);
                HttpEntity<String> entity = new HttpEntity<>(headers);
                responseEntity = restTemplate.exchange(url.toURI(), GET, entity, String.class);
            } catch (RuntimeException e) {
                log.warn("Unable to retrieve " + url.toString() + " using content type " + mimeType);
            }
            if (responseEntity != null) {
                return responseEntity.getBody();
            }
        }
        throw new IllegalArgumentException("Unable to retrieve " + url.toString() + " using any of the following mime types: " + MIME_TYPES);
    }
}
