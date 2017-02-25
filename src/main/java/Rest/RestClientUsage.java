package Rest;

import javax.management.OperationsException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RestClientUsage {
    public RestClientUsage() {
    }

    public Future<String> futureDownloadHtmlSource(String absoluteUrl) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future future = executor.submit(() -> {
            String responseString;
            try {
                responseString = RestClient.get(absoluteUrl);
                return responseString;
            } catch (IOException ex) {
                System.out.println(">>> Failure");
                ex.printStackTrace();
            } catch (OperationsException ex) {
                System.out.println(">>> OperationsException: " + ex);
                ex.printStackTrace();
            }
            return "";
        });
        return future;
    }
}
