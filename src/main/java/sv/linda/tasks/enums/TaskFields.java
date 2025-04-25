package sv.linda.tasks.enums;

public enum TaskFields {
    TITLE("Title"),
    STATUS("Status"),
    DESCRIPTION("Description"),
    SUBTASKS("Subtasks");

    private final String name;

    TaskFields(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
