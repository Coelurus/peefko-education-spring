package pfko.vopalensky.spring.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceResponse {
    @JsonProperty("id")
    private final long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;
}