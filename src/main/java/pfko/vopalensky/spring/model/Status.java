package pfko.vopalensky.spring.model;

public enum Status {
    CUSTOMER("customer"),

    SUPPLIER("supplier");

    private final String value;


    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
