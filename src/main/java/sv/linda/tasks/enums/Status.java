package sv.linda.tasks.enums;

import lombok.Getter;

@Getter
public enum Status {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}