package Registry;

import java.util.HashMap;
import java.util.Map;

public class DeferredRegister<T extends IRegistrable> {
    private final Map<String, T> Registered = new HashMap<>();

    public void register(T Object) {
        Registered.put(Object.getRegistryName(), Object);
    }

    public T getRegistry(String Name) {
        return Registered.get(Name);
    }
}
