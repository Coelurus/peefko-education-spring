package pfko.vopalensky.spring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @BeforeEach
    @WithMockUser(username = "admin", roles = {"supplier"})
    void fillWithOffers() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("username", "admin")
                .password("password", "admin")
                .acceptMediaType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = {"supplier"})
    void testAddOffer() throws Exception {
        String offerString = "{ \"id\":1, \"name\":\"Test\", \"cost\":3000}";
        ResultActions result = mockMvc.perform(post("/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offerString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Test\",\"cost\":3000,\"services\":null,\"created\":null}"));
    }


    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void getOfferById() throws Exception {
        ResultActions result = mockMvc.perform(get("/offer/0")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"name\":\"Security\",\"cost\":2000,\"services\":null,\"created\":{\"id\":0,\"userName\":\"user\",\"password\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"}}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void getOfferByInvalidId() throws Exception {
        ResultActions results = mockMvc.perform(get("/offer/999")
                .header("Accept", "application/json"));
        results.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void deleteOffer() throws Exception {
        ResultActions results = mockMvc.perform(delete("/offer/1"));
        results.andExpect(status().isOk());
        mockMvc.perform(get("/offer/1")
                        .header("Accept", "application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void updateOfferWithForm() throws Exception {
        ResultActions result = mockMvc.perform(post("/offer/1")
                .param("name", "Changed")
                .param("cost", "1001")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Changed\",\"cost\":1001,\"services\":null,\"created\":null}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testUpdateOffer() throws Exception {
        String offerString = "{ \"id\":1, \"name\":\"PutChange\", \"cost\":696}";
        ResultActions result = mockMvc.perform(put("/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offerString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"PutChange\",\"cost\":696,\"services\":null,\"created\":null}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testGetOffers() throws Exception {
        ResultActions result = mockMvc.perform(get("/offer")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":0,\"name\":\"Security\",\"cost\":2000,\"services\":null,\"created\":{\"id\":0,\"userName\":\"user\",\"password\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"}},{\"id\":2,\"name\":\"Home Page Button\",\"cost\":100000,\"services\":null,\"created\":{\"id\":1,\"userName\":\"admin\",\"password\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}},{\"id\":1,\"name\":\"Changed\",\"cost\":1001,\"services\":null,\"created\":null}]"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testPlaceOrder() throws Exception {
        String orderString = "{ \"id\":222, \"offerId\":440, \"customerId\":74, \"completed\":true, \"payed\":false}";
        ResultActions result = mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":222,\"offerId\":440,\"customerId\":74,\"completed\":true," +
                        "\"payed\":false}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testGetOrderById() throws Exception {
        mockMvc.perform(get("/order/0")
                        .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"offerId\":1,\"customerId\":0,\"completed\":true,\"payed\":true}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testUpdateOrderWithForm() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("username", "admin")
                .password("password", "admin")
                .acceptMediaType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/order/0")
                        .param("complete", "true")
                        .param("payed", "true")
                        .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"offerId\":1,\"customerId\":0,\"completed\":true,\"payed\":true}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/order/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/board/order/1")
                        .header("Accept", "application/json"))
                .andExpect(status().isNotFound());
    }

}
