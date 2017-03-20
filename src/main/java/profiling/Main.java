package profiling;

/**
 * Created by Evegeny on 20/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        NicePersonService service = new NicePersonService();
        while (true) {
            service.doWork();
        }
    }
}
