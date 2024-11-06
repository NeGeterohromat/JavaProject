import java.util.Collection;
import java.util.HashMap;

public class Course {
    private final String name;
    private HashMap<String, Module> modules = new HashMap<>();

    public Course(String name) {
        this.name = name;
    }

    public void addModule(Module m){
        modules.put(m.getName(),m);
    }

    public Module getModule(String name){
        return modules.getOrDefault(name,null);
    }

    public Collection<Module> getModules(){
        return modules.values();
    }
}
