package pfko.vopalensky.spring.model;

public enum Role {
    CUSTOMER("CUSTOMER"),

    SUPPLIER("SUPPLIER");

    private final String value;


    Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
