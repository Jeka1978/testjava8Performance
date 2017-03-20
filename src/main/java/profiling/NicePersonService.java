package profiling;

import or.Person;
import org.fluttercode.datafactory.impl.DataFactory;

import java.util.Random;

/**
 * Created by Evegeny on 20/03/2017.
 */
public class NicePersonService {
    private DataFactory dataFactory = new DataFactory();
    private NiceCacheService cache = new NiceCacheService();
    public void doWork() {
        Random random = new Random();

        Person person = new Person(dataFactory.getName());
        if (random.nextInt(100) == 10) {
            cache.addToCache(random.nextInt(10_000),person);
        }

    }
}








