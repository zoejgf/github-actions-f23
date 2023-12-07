package edu.greenriver.sdev.jokesapi;

import edu.greenriver.sdev.jokesapi.model.Joke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JokesApiApplicationTests
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    @Test
    void contextLoads()
    {
        System.out.println("Spring app context loaded");

    }

    @Test void getAllJokes()
    {
        String endpoint = "http://localhost:" + port + "/jokes";

        //assemble a request
        HttpEntity request = new HttpEntity(new HttpHeaders());

        //make the request and get a response
        ResponseEntity<Joke[]> response = rest.exchange(endpoint, HttpMethod.GET, request, Joke[].class);

        //test it
        HttpStatusCode status = response.getStatusCode();
        Joke[] jokes = response.getBody();

        System.out.println("Assert our results");
        assertEquals(status, HttpStatus.OK);
        assertTrue(jokes.length > 0);
    }
    @Test
    public void createJokeTest() {
        String endpoint = "http://localhost" + port + "/jokes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //"Content-Type: "Application/json"

        Joke joke = new Joke("Knock, knock!");
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<Joke> response = rest.exchange(endpoint, HttpMethod.POST,
                request, Joke.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getId() > 0);
    }

}
