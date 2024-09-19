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

    public static StatusEnum fromValue(String text) {
        for (StatusEnum state : StatusEnum.values()) {
            if (String.valueOf(state.value).equals(text)) {
                return state;
            }
        }
        return null;
    }
}
