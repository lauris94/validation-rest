package com.laurynas.workorder.validationrest;

import com.laurynas.workorder.validationrest.presentation.model.ValidationHistoryView;
import com.laurynas.workorder.validationrest.presentation.model.ValidationResultView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationRestApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void validateWorkOrderAndFetchHistory() throws IOException {
        ResponseEntity<List<ValidationHistoryView>> historyResponse = fetchHistory();
        assertSize(historyResponse, 0);

        ResponseEntity<ValidationResultView> validationResponse = validate("order1.json");
        assertOrderWasValid(validationResponse);

        historyResponse = fetchHistory();
        assertSize(historyResponse, 1);

        validationResponse = validate("order2.json");
        assertOrderWasValid(validationResponse);

        historyResponse = fetchHistory();
        assertSize(historyResponse, 2);

        validationResponse = validate("order3.json");
        assertOrderWasValid(validationResponse);

        historyResponse = fetchHistory();
        assertSize(historyResponse, 3);
    }

    @Test
    public void validateWorkOrderInvalidJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{ someInvalidJson }", headers);
        ResponseEntity<ValidationResultView> response = restTemplate.postForEntity("/orders/validation", entity, ValidationResultView.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void viewOrders() {
        ResponseEntity<String> response = restTemplate.getForEntity("/viewOrders", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders().getContentType());
        assertEquals("text", response.getHeaders().getContentType().getType());
        assertEquals("html", response.getHeaders().getContentType().getSubtype());
    }

    @Test
    void validationForm() {
        ResponseEntity<String> response = restTemplate.getForEntity("/validationForm", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders().getContentType());
        assertEquals("text", response.getHeaders().getContentType().getType());
        assertEquals("html", response.getHeaders().getContentType().getSubtype());
    }

    private void assertSize(ResponseEntity<List<ValidationHistoryView>> response, int expectedSize) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedSize, response.getBody().size());
    }

    private ResponseEntity<List<ValidationHistoryView>> fetchHistory() {
        return restTemplate.exchange("/orders/history",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<ValidationHistoryView>>() {
                });
    }

    private void assertOrderWasValid(ResponseEntity<ValidationResultView> response) {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getResultCodes().isEmpty());
    }

    private ResponseEntity<ValidationResultView> validate(String path) throws IOException {
        byte[] bytes = resourceLoader.getResource("classpath:orders/" + path).getInputStream().readAllBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<byte[]> entity = new HttpEntity<>(bytes, headers);
        return restTemplate.postForEntity("/orders/validation", entity, ValidationResultView.class);
    }
}
