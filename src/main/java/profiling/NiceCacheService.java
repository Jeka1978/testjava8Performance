package profiling;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import or.Person;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Evegeny on 20/03/2017.
 */
public class NiceCacheService {
    private Map<Long, Person> cache = new WeakHashMap<>();

    public void addToCache(long id, Person person) {
        WeakReference<Person> weakReference = new WeakReference<>(new Person("sadas"));
        Person person1 = weakReference.get();
        cache.put(id, person);

        Cache<Long, Person> cache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.HOURS)
                .expireAfterWrite(5, TimeUnit.DAYS)
                .weakKeys().concurrencyLevel(2).build();

    }
}
