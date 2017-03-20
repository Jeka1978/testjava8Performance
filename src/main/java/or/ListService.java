package or;

import java.util.List;

/**
 * Created by Evegeny on 20/03/2017.
 */
public class ListService {
    public void doForeachOldSchool(List<Person> personList) {
        for (Person person : personList) {
            person.setName(person.getName().toUpperCase());
        }
    }

    public void doForeachWithStream(List<Person> personList) {
        personList.stream().forEach(person -> person.setName(person.getName().toUpperCase()));
    }

    public void doForeachWithParallelStream(List<Person> personList) {
        personList.parallelStream().forEach(person -> person.setName(person.getName().toUpperCase()));

    }
}



