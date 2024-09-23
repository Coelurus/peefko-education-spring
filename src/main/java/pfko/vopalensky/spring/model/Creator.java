package pfko.vopalensky.spring.model;

import lombok.Data;

@Data
public class Creator {
    protected Long creatorId;
    protected CreatorType creatorType;
}
