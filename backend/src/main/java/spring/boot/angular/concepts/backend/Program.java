package spring.boot.angular.concepts.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import spring.boot.angular.concepts.backend.workers.BackgroundWorker;

@SpringBootApplication
public class Program {

    @Autowired
    private BackgroundWorker backgroundWorker;

    public static void main(String[] args) {
        SpringApplication.run(Program.class, args);
    }

    @PostConstruct
    public void startWorkers() {
        backgroundWorker.start();
    }

}
