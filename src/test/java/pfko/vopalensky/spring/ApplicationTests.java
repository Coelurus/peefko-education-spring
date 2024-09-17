package pfko.vopalensky.spring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void fillWithOffers() throws Exception {
        String[] offer1String = {"{ \"id\":0, \"name\":\"Test\", \"cost\":999}",
                "{ \"id\":1, \"name\":\"Offer\", \"cost\":123}",
                "{ \"id\":2, \"name\":\"Dont miss\", \"cost\":1000}",
                "{ \"id\":3, \"name\":\"ALL\", \"cost\":30000}"};

        for (String s : offer1String) {
            mockMvc.perform(post("/offer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(s)
                    .header("Accept", "application/json"));
        }
    }

    @AfterEach
    void clear() throws Exception {
        for (int i = 0; i < 10; i++) {
            mockMvc.perform(delete("/offer/" + i));
        }

    }

    @Test
    void testAddOffer() throws Exception {
        String offerString = "{ \"id\":1, \"name\":\"Test\", \"cost\":3000}";
        ResultActions result = mockMvc.perform(post("/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offerString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Test\",\"cost\":3000,\"services\":null,\"createdBy\":null}"));
    }

    /*
    @Test
    void addUnprocessableOffer() throws Exception {
        mockMvc.perform(post("/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\":422, \"cost\":\"price\", \"label\":\"EPic label\"}")
                        .header("Accept", "application/json"))
                .andExpect(status().isUnprocessableEntity());
    }
    */

    @Test
    void getOfferById() throws Exception {
        ResultActions result = mockMvc.perform(get("/offer/1")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Offer\",\"cost\":123,\"services\":null,\"createdBy\":null}"));
    }

    @Test
    void getOfferByInvalidId() throws Exception {
        ResultActions results = mockMvc.perform(get("/offer/999")
                .header("Accept", "application/json"));
        results.andExpect(status().isNotFound());
    }

    @Test
    void deleteOffer() throws Exception {
        ResultActions results = mockMvc.perform(delete("/offer/1"));
        results.andExpect(status().isOk());
    }

    @Test
    void updateOfferWithForm() throws Exception {
        ResultActions result = mockMvc.perform(post("/offer/1")
                .param("name", "Changed")
                .param("cost", "1001")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Changed\",\"cost\":1001,\"services\":null,\"createdBy\":null}"));
    }

    @Test
    void testUpdateOffer() throws Exception {
        String offerString = "{ \"id\":1, \"name\":\"PutChange\", \"cost\":696}";
        ResultActions result = mockMvc.perform(put("/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offerString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"PutChange\",\"cost\":696,\"services\":null,\"createdBy\":null}"));
    }
}
