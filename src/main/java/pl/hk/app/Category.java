package pl.hk.app;

public enum Category {
    Restaurants("restauracje");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
