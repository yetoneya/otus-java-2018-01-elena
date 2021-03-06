package ru.otus.elena.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.elena.dbservice.dataset.base.DataSet;

@Component
public class EternalCache<T extends DataSet> implements Cache {    
    
    private  int maxElement;
    private final Map<KeyElement,DataSet> elements = new LinkedHashMap<>();   
    private int hit = 0;
    private int miss = 0;
    
    public EternalCache(){}

    public EternalCache(int maxElements) {
        this.maxElement = maxElements;

    }
    public void setMaxElement(int maxElement){
        this.maxElement=maxElement;
    }

    public <T extends DataSet> void put(T object) {
        if (elements.size() == maxElement) {
            KeyElement firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        KeyElement key = new KeyElement(object);
        elements.put(key, object);

    }

    @Override
    public <T extends DataSet> T get(long id, Class<T> clazz) {
        KeyElement key = new KeyElement(id, clazz);
        return get(key);
    }

    public <T extends DataSet> T get(KeyElement key) {
        T element = (T) elements.get(key);
        if (element != null) {
            hit++;
        } else {
            miss++;
        }
        return element;
    }
    @Override
    public int getCacheSize() {
        return elements.size();
    }

    @Override
    public int getMaxElements() {
        return maxElement;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {

    }


 


}
