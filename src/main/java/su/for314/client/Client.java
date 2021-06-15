package su.for314.client;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import su.for314.model.User;

public class Client {
    static final String URL_USERS = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) {
        String answer = "";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result;

        ResponseEntity<String> response = restTemplate.getForEntity(URL_USERS, String.class);

        HttpHeaders head = response.getHeaders();
        String set_cookie = head.getFirst("Set-cookie");

        System.out.println("Set-Cookie: " + set_cookie + "\n");
        System.out.println("********* FINISH 1 *********");

        User newUser = new User(3l, "James", "Brown", (byte) 22);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("Cookie", set_cookie);
        HttpEntity<User> entity2 = new HttpEntity<>(newUser, headers);
        result = restTemplate.exchange(URL_USERS, //
                HttpMethod.POST, entity2, String.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            answer += result.getBody();
            System.out.println("********* FINISH 2 *********");
        }

        User updateInfo = new User(3l, "Thomas", "Shelby", (byte) 22);
        HttpEntity<User> entity3 = new HttpEntity<>(updateInfo, headers);
        result = restTemplate.exchange(URL_USERS, HttpMethod.PUT, entity3, String.class);
        if (result != null) {
            answer += result.getBody();
            System.out.println("********* FINISH 3 *********");
        }

        HttpEntity<User> entity4 = new HttpEntity<>(headers);
        result = restTemplate.exchange(URL_USERS + "/3", HttpMethod.DELETE, entity4, String.class);
        if (result != null) {
            answer += result.getBody();
            System.out.println("********* FINISH 4 *********");
        }

        System.err.println(answer);

    }
}
