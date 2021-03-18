package com.xmen.mutanthunter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmen.mutanthunter.model.DnaRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "/mutant";

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isMutant_happy_path() throws Exception {
        DnaRequest dnaRequest = new DnaRequest();
        dnaRequest.setDna(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
        mockMvc.perform(MockMvcRequestBuilders
                .post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)

        .content(asJsonString(dnaRequest))
                ).andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
