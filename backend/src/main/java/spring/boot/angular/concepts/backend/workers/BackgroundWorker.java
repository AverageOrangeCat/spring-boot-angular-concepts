package spring.boot.angular.concepts.backend.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BackgroundWorker extends Thread {

    private final Logger logger = LoggerFactory.getLogger(BackgroundWorker.class);

    @Override
    public void run() {
        while (true) {

            logger.debug("Hello from 'BackgroundWorker'");

            try {
                Thread.sleep(10_000);

            } catch (InterruptedException exception) {
                interrupt();
            }
        }
    }

}
