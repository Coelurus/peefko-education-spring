package pfko.vopalensky.spring;

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
        String offerString = "{ \"id\":1, \"name\":\"Test\", \"cost\":3000, \"services\":[1], \"creator\":0}";
        ResultActions result = mockMvc.perform(post("/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offerString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"Test\",\"cost\":3000,\"services\":[{\"id\":1,\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}}"));
    }


    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void getOfferById() throws Exception {
        ResultActions result = mockMvc.perform(get("/offer/0")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"name\":\"Security\",\"cost\":2000,\"services\":[{\"id\":2,\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}}"));
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
        String offerString = "{ \"id\":13, \"name\":\"Test\", \"cost\":3000, \"services\":[1], \"creator\":0}";
        mockMvc.perform(post("/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offerString)
                .header("Accept", "application/json"));
        ResultActions results = mockMvc.perform(delete("/offer/13"));
        results.andExpect(status().isOk());
        mockMvc.perform(get("/offer/13")
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
                .andExpect(content().string("{\"id\":1,\"name\":\"Changed\",\"cost\":1001,\"services\":[{\"id\":1,\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testUpdateOffer() throws Exception {
        String offerString = "{ \"id\":1, \"name\":\"PutChange\", \"cost\":696, \"services\":[1], \"creator\":0}";
        ResultActions result = mockMvc.perform(put("/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(offerString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"name\":\"PutChange\",\"cost\":696,\"services\":[{\"id\":1,\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testGetOffers() throws Exception {
        ResultActions result = mockMvc.perform(get("/offer")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":0,\"name\":\"Security\",\"cost\":2000,\"services\":[{\"id\":2,\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}},{\"id\":1,\"name\":\"Changed\",\"cost\":1001,\"services\":[{\"id\":1,\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}},{\"id\":2,\"name\":\"Home Page Button\",\"cost\":100000,\"services\":[{\"id\":0,\"name\":\"NAP\",\"description\":\"Like sleeping\"}],\"created\":{\"id\":2,\"userName\":\"creator\",\"status\":\"SUPPLIER\",\"name\":\"I CREATE STUFF\"}},{\"id\":1,\"name\":\"Test\",\"cost\":3000,\"services\":[{\"id\":1,\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}}]"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testPlaceOrder() throws Exception {
        String orderString = "{ \"id\":222, \"offerId\":0, \"customerId\":0, \"completed\":true, \"payed\":false}";
        ResultActions result = mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":222,\"offer\":{\"id\":0,\"name\":\"Security\",\"cost\":2000,\"services\":[{\"id\":2,\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}},\"customer\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"completed\":true,\"payed\":false}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testGetOrderById() throws Exception {
        mockMvc.perform(get("/order/0")
                        .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"offer\":{\"id\":1,\"name\":\"Changed\",\"cost\":1001,\"services\":[{\"id\":1,\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}},\"customer\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"completed\":true,\"payed\":false}"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"customer"})
    void testUpdateOrderWithForm() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("username", "user")
                .password("password", "user")
                .acceptMediaType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/order/0")
                        .param("complete", "true")
                        .param("payed", "false")
                        .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"offer\":{\"id\":1,\"name\":\"PutChange\",\"cost\":696,\"services\":[{\"id\":1,\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"}],\"created\":{\"id\":0,\"leader\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"members\":[{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},{\"id\":1,\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}]}},\"customer\":{\"id\":0,\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"completed\":true,\"payed\":false}"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"supplier"})
    void testDeleteOrder() throws Exception {
        String orderString = "{ \"id\":222, \"offerId\":0, \"customerId\":0, \"completed\":true, \"payed\":false}";
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderString)
                .header("Accept", "application/json"));
        mockMvc.perform(delete("/order/222"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/board/order/222")
                        .header("Accept", "application/json"))
                .andExpect(status().isNotFound());
    }

}
