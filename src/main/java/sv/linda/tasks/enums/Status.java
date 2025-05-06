package sv.linda.tasks.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Status {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
    DELETED("Deleted");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Status> getAll(){
        List<Status> temp = new ArrayList<>(Arrays.asList(Status.values()));
        temp.remove(Status.DELETED);
        return temp;
    }

    public static Status toEnum(String title) {
        for (Status status : Status.values()) {
            if (status.name.equalsIgnoreCase(title)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}