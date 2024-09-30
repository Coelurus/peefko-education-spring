package pfko.vopalensky.spring.model;

public enum CreatorType {
    INDIVIDUAL("INDIVIDUAL"),

    TEAM("TEAM");

    private final String value;

    private CreatorType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
