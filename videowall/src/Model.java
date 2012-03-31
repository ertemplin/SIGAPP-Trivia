import java.util.Observable;
import java.util.ArrayList;

public class Model extends Observable
{
    static ArrayList<String> strings = new ArrayList<String>();
    
    void appendLine(String line) {
        strings.add(line);
        setChanged();
        notifyObservers(line);
    }
    
    void clear() {
        strings.clear();
        setChanged();
        notifyObservers(null);
    }
    
    void initialHack() {
        for (int i = 0; i < strings.size(); i++) {
            setChanged();
            notifyObservers(strings.get(i));
        }
    }
}
