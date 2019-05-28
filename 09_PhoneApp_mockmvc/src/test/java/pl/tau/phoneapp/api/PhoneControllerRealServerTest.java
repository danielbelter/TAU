package pl.tau.phoneapp.api;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;
import pl.tau.phoneapp.domain.Phone;
import pl.tau.phoneapp.service.PhoneManager;

import java.util.LinkedHashMap;
import java.util.List;


/**
 * This example creates real server that will handle requests. The spring test will
 * query the server and tests checks the correctness of responses
 */

@Ignore
@RunWith(SpringRunner.class)
@ComponentScan({"pl.tau.phoneapp"})
@PropertySource("classpath:jdbc.properties")
@ImportResource({"classpath:/beans.xml"})
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhoneControllerRealServerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private PhoneController controller;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    PhoneManager libraryManager; // manager is needed for direct manipulation with database

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Hello");
    }

    @Test
    public void getAllShouldReturnSomeResultsFromDatabase() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/phones",
                        List.class)).isNotNull();
    }

    @Test
    public void getAllShouldReturnResultsThatWerePreviouslyPutIntoDatabase() throws Exception {
        Phone newPerson = new Phone();
        newPerson.setModel("Milo");
        Long newId = libraryManager.addPhonejkk(newPerson);
        List<java.util.LinkedHashMap> personsFromRest =
                this.restTemplate.getForObject("http://localhost:" + port + "/phones", List.class);
        boolean found = false;
        for (LinkedHashMap p: personsFromRest) {
            if (p.get("id").toString().equals(newId.toString())) found = true;
        }
        assertTrue(found);
    }

}