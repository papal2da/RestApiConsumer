package ru.KataAcademy.Filatov;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.KataAcademy.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Consumer {
    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        final String URL = "http://94.198.50.185:7081/api/users";

        ResponseEntity<List<User>> responseEntity = restTemplate
                .exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                });

        // получаем куки из тела ответа
        HttpHeaders headers = responseEntity.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        // создаем новый хеддер и сетаем вытащенные из тела запроса куки
        HttpHeaders headersForRequest = new HttpHeaders();
//        headersForRequest.setContentType(MediaType.APPLICATION_JSON);           // может убрать? в джейсоне ли передается инфо?
        headersForRequest.set(HttpHeaders.COOKIE, set_cookie);
//        // кладем в http-сущность объект user и header
//        HttpEntity<User> request = new HttpEntity<>(user, headersForRequest);         старая версия, клал юзера целиком, ниже кладу в мапу по ключам
//        ResponseEntity<String> responseToSave = restTemplate
//                .exchange(URL, HttpMethod.POST, request, String.class);
//        String stringNewUser = responseToSave.getBody();


        // другая версия, сохраняем через мапу. результат один, прилетает ответ  5ebfeb
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("id", "3");
        jsonData.put("name", "James");
        jsonData.put("lastName", "Brown");
        jsonData.put("age", "26");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonData, headersForRequest);
        ResponseEntity<String> responseToSave = restTemplate
                .exchange(URL, HttpMethod.POST, request, String.class);
        String stringNewUser = responseToSave.getBody();
        HttpStatusCode status = responseToSave.getStatusCode(); // https://tproger.ru/articles/osnovy-rest-teorija-i-praktika/ - тут все статус-коды для запросов
//        String response1 = restTemplate.postForObject(URL, request, String.class); // POST-запрос к серверу

        Map<String, String> jsonData1 = new HashMap<>();
        jsonData.put("id", "3");
        jsonData.put("name", "Thomas");
        jsonData.put("lastName", "Shelby");
        jsonData.put("age", "26");
        HttpEntity<Map<String, String>> requestUpdate = new HttpEntity<>(jsonData, headersForRequest);
//        HttpEntity<User> requestUpdate = new HttpEntity<>(updatedInstance, headersForRequest);
        ResponseEntity<String> s = restTemplate.exchange(URL, HttpMethod.PUT, requestUpdate, String.class);
        String responseToUpdate = s.getBody();

        ResponseEntity<String> requestDelete  = restTemplate.exchange(URL+ "/" + "3", HttpMethod.DELETE, requestUpdate, String.class);
        String responseToDelete = requestDelete.getBody();
//
//        HttpEntity<User> http = restTemplate.getForEntity(URL, User.class);


//        List<User> usery = responseEntity.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree();
//        usery.forEach(user -> System.out.println(user));
//        System.out.println(headers);


        System.out.println(re);
    }
}