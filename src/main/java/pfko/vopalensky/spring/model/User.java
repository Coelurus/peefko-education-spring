package pfko.vopalensky.spring.model;

public class User {
    private long id;
    private String userName;
    private String hashPassword;
    private StatusEnum status;
    private String name;
    private String phone;
    private String email;

    public enum StatusEnum {
        CUSTOMER("customer"),

        SUPPLIER("supplier");

        private String value;


        StatusEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static StatusEnum fromValue(String text) {
            for (StatusEnum state : StatusEnum.values()) {
                if (String.valueOf(state.value).equals(text)) {
                    return state;
                }
            }
            return null;
        }
    }

    public User(long id, String userName, String hashPassword, StatusEnum status, String name, String phone, String email) {
        this.id = id;
        this.userName = userName;
        this.hashPassword = hashPassword;
        this.status = status;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
