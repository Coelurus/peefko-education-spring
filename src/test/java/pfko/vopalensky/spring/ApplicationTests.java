package pfko.vopalensky.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApplicationTests {

    @BeforeEach
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void fillWithOffers() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("username", "admin")
                .password("password", "admin")
                .acceptMediaType(MediaType.APPLICATION_JSON));
    }


    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @Sql(scripts = "/data.sql")
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void getOfferById() throws Exception {
        ResultActions result = mockMvc.perform(get("/offer/0")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"SECURITY\",\"cost\":2000,\"services\":[{\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"leader\":{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},\"members\":[{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},{\"userName\":\"creator\",\"status\":\"SUPPLIER\",\"name\":\"I CREATE STUFF\"}]}}"));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void getOfferByInvalidId() throws Exception {
        ResultActions results = mockMvc.perform(get("/offer/999")
                .header("Accept", "application/json"));
        results.andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void testGetOffers() throws Exception {
        ResultActions result = mockMvc.perform(get("/offer")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("[{\"name\":\"SECURITY\",\"cost\":2000,\"services\":[{\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"leader\":{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},\"members\":[{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},{\"userName\":\"creator\",\"status\":\"SUPPLIER\",\"name\":\"I CREATE STUFF\"}]}},{\"name\":\"All exclusive\",\"cost\":666,\"services\":[{\"name\":\"NAP\",\"description\":\"Like sleeping\"},{\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"},{\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}},{\"name\":\"Home Page Button\",\"cost\":100000,\"services\":[{\"name\":\"NAP\",\"description\":\"Like sleeping\"}],\"created\":{\"userName\":\"creator\",\"status\":\"SUPPLIER\",\"name\":\"I CREATE STUFF\"}}]"));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "admin", password = "admin", roles = {"SUPPLIER"})
    void testAddOffer() throws Exception {
        String offerString = "{\"name\":\"Test\", \"cost\":3000, \"services\":[1],\"creator_type\":\"TEAM\", \"creator_id\":0}";
        ResultActions result = mockMvc.perform(post("/offer")
                .content(offerString)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"Test\",\"cost\":3000,\"services\":[{\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"}],\"created\":{\"leader\":{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},\"members\":[{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},{\"userName\":\"creator\",\"status\":\"SUPPLIER\",\"name\":\"I CREATE STUFF\"}]}}"));
    }

    @Test
    @Order(5)
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void testDeleteOffer() throws Exception {
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
    @Order(6)
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void testUpdateOfferWithForm() throws Exception {
        ResultActions result = mockMvc.perform(post("/offer/1")
                .param("name", "Changed")
                .param("cost", "1001")
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"Changed\",\"cost\":1001,\"services\":[{\"name\":\"NAP\",\"description\":\"Like sleeping\"},{\"name\":\"COFFEE SERVICE\",\"description\":\"I drink all your milk\"},{\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"}}"));
    }

    @Test
    @Order(8)
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void testGetOrderById() throws Exception {
        mockMvc.perform(get("/order/0")
                        .header("Accept", "application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":0,\"offer\":{\"name\":\"SECURITY\",\"cost\":2000,\"services\":[{\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"leader\":{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},\"members\":[{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},{\"userName\":\"creator\",\"status\":\"SUPPLIER\",\"name\":\"I CREATE STUFF\"}]}},\"customer\":{\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"completed\":false,\"payed\":false}"));
    }

    @Test
    @Order(9)
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void testPlaceOrder() throws Exception {
        String orderString = "{\"offer_id\":0, \"customer_id\":0, \"completed\":true, \"payed\":false}";
        ResultActions result = mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderString)
                .header("Accept", "application/json"));
        result.andExpect(status().isOk())
                .andExpect(content().string("{\"id\":2,\"offer\":{\"name\":\"SECURITY\",\"cost\":2000,\"services\":[{\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"leader\":{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},\"members\":[{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},{\"userName\":\"creator\",\"status\":\"SUPPLIER\",\"name\":\"I CREATE STUFF\"}]}},\"customer\":{\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"completed\":true,\"payed\":false}"));
    }

    @Test
    @Order(10)
    @WithMockUser(username = "admin", roles = {"SUPPLIER"})
    void testDeleteOrder() throws Exception {

        mockMvc.perform(delete("/order/0"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/board/order/0")
                        .header("Accept", "application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(11)
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
                .andExpect(content().string("{\"id\":0,\"offer\":{\"name\":\"SECURITY\",\"cost\":2000,\"services\":[{\"name\":\"DESK INSPECTION\",\"description\":\"Dont even ask\"}],\"created\":{\"leader\":{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},\"members\":[{\"userName\":\"admin\",\"status\":\"SUPPLIER\",\"name\":\"MONEYZ\"},{\"userName\":\"creator\",\"status\":\"SUPPLIER\",\"name\":\"I CREATE STUFF\"}]}},\"customer\":{\"userName\":\"user\",\"status\":\"CUSTOMER\",\"name\":\"IM PAYING\"},\"completed\":true,\"payed\":false}"));
    }

}
