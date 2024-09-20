package pfko.vopalensky.spring.model;

public enum StatusEnum {
    CUSTOMER("customer"),

    SUPPLIER("supplier");

    private final String value;


    StatusEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
