package pfko.vopalensky.spring.model;

import lombok.Data;

@Data
public class User {
    private long id;
    private String userName;
    private String hashPassword;
    private StatusEnum status;
    private String name;

    public User(long id, String userName, String hashPassword, StatusEnum status, String name) {
        this.id = id;
        this.userName = userName;
        this.hashPassword = hashPassword;
        this.status = status;
        this.name = name;
    }
}
