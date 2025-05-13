package sv.linda.tasks.functions;

import com.google.gson.Gson;
import lombok.Getter;
import sv.linda.tasks.constructors.Login.Login;
import sv.linda.tasks.constructors.Task.Task;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GetInfo {
    protected File[] listOfFiles;
    protected Gson gson;

    public GetInfo(File[] listOfFiles) {
        this.listOfFiles = listOfFiles;
        gson = new Gson();
    }

    protected Task makeTask(File file) throws FileNotFoundException {
        Reader read = new FileReader(file);
        return gson.fromJson(read, Task.class);
    }

    protected Login makeLogin(File file) throws FileNotFoundException {
        Reader read = new FileReader(file);
        return gson.fromJson(read, Login.class);
    }

    public Login toLogin(String title) throws FileNotFoundException {
        for (File F : listOfFiles) {
            if (F.getName().equals(title + ".json")) {
                return makeLogin(F);
            }
        }
        return null;
    }

    public Task toTask(String title) throws FileNotFoundException {
        for (File F : listOfFiles) {
            if (F.getName().equals(title + ".json")) {
                return makeTask(F);
            }
        }
        return null;
    }

    public List<Login> getLogins() {
        ArrayList<Login> logins = new ArrayList<>();
        for (File file : this.listOfFiles) {
            if (file.isFile()) {
                try {
                    logins.add(makeLogin(file));
                } catch (FileNotFoundException ignored) {
                }
            }
        }
        return logins;
    }

    public List<String> getTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        for (File file : this.listOfFiles) {
            if (file.isFile()) {
                tasks.add(file.getName().substring(0, file.getName().indexOf(".json")));
            }
        }
        return tasks;
    }
}