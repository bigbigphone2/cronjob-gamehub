import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Cronjob {

    private static final Logger logger = Logger.getLogger(Cronjob.class.getName());

    public static void startTask() {
        initialLogger();

        Timer timer = new Timer();

        // Schedule the task to run every 1 minute
        timer.schedule(new HttpRequestTask(), 0, 1 * 60 * 1000);

        System.out.println("--------------");
        System.out.println("Function Start");
        System.out.println("--------------");
    }

    private static void initialLogger() {
        // Configure logger
        try {
            FileHandler fileHandler = new FileHandler("Cronjob.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class HttpRequestTask extends TimerTask {
        @Override
        public void run() {
            makeRequest();
        }

        // Function to make an HTTP request to the website
        private void makeRequest() {
            String url = "https://gamehub-backend-7qko.onrender.com/api/v1/game_sale_post/id/5";
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == 200) {
                    logSuccess();
                } else {
                    logFailure();
                }

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Log success event
    private static void logSuccess() {
        logger.log(Level.INFO, "Success at {0}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    // Log failure event
    private static void logFailure() {
        logger.log(Level.WARNING, "Failure at {0}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
