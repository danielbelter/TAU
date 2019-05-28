package pl.tau.phoneapp.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.tau.phoneapp.domain.Phone;
import pl.tau.phoneapp.service.PhoneManager;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/*
This example uses MockMvc to mock server and http requests. This way we don't need to run real server.
We can also use Mockito to mock access to database.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhoneControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneManager service;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(mockMvc);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")));
    }

    @Test
    public void getAllShouldReturnEmptyResults() throws Exception {
        when(service.findAllPhone()).thenReturn(new LinkedList<Phone>());
        this.mockMvc.perform(get("/phones"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getAllShouldReturnSomeResults() throws Exception {
        List<Phone> expectedResult = new LinkedList<Phone>();
        Phone np = new Phone();
        np.setId(12L);
        np.setModel("Mimooo");
        expectedResult.add(np);
        when(service.findAllPhone()).thenReturn(expectedResult);
        this.mockMvc.perform(get("/phones"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":12,\"model\":\"Mimooo\"}]"));
    }

    @Test
    public void postNewPhoneShouldReallyAddItToDatabase() throws Exception {
        Phone p = new Phone();
        p.setModel("Mimo");
        p.setSerialNumber(133456);
        when(service.addPhonejkk(p)).thenReturn(0L);
        this.mockMvc.perform(post("/phones")
                .content("{\"model\":\"Mimo\"," +
                        "\"serialNumber\":\"133456\"}")

                .contentType("application/json"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0,\"model\":\"Mimo\",\"serialNumber\":133456,\"owner\":null}"));
        p.setId(0L);
        verify(service).addPhonejkk(p);
    }

    @Test
    public void deleteShouldRemovePhoneFromDatabase() throws Exception{
        Phone p = new Phone();
        p.setId(1L);
        p.setModel("Mimo");
        p.setSerialNumber(444);
        when(service.findPhoneById(1L)).thenReturn(p);
        this.mockMvc.perform(delete("/phone/"+p.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        verify(service).deletePhone(p);
    }

    @Test
    public void getShouldReturnPhoneFromDatabase() throws Exception {
        Phone p = new Phone();
        p.setId(1L);
        p.setModel("Mimo");
        p.setSerialNumber(444);
        when(service.findPhoneById(1L)).thenReturn(p);
        this.mockMvc.perform(get("/phone/" + p.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"model\":\"Mimo\",\"serialNumber\":444,\"owner\":null}"));
        verify(service).findPhoneById(1L);

    }
    @Test
    public void putShouldReturnUpdatedPhoneFromDatabase() throws Exception{
        Phone p = new Phone();
        p.setId(1L);
        p.setModel("Mimo");
        p.setSerialNumber(444);
        this.mockMvc.perform(put("/phone")
                .content("{\"id\":1,\"model\":\"Mimo2\",\"serialNumber\":444,\"owner\":null}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
        p.setModel("Mimo2");
        verify(service).updatePhone(p);

    }
}
