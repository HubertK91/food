package pl.hk.food;

public enum Category {
    Polish("Kuchnia polska"), Italian("Kuchnia włoska"),
    Chinese("Kuchnia chińska"), Greek("Kuchnia grecka");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
