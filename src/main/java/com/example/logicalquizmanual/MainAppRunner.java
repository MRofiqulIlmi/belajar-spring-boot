package com.example.logicalquizmanual;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;

@Component
public class MainAppRunner implements CommandLineRunner {

    private final OddChecker oddChecker;
    private final NaturalNumberSum naturalNumberSum;

    public MainAppRunner(OddChecker oddChecker, NaturalNumberSum naturalNumberSum) {
        this.oddChecker = oddChecker;
        this.naturalNumberSum = naturalNumberSum;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Check Odd/Even");
                System.out.println("2. Sum of Natural Numbers");
                System.out.println("3. Exit");
                System.out.print("Your choice: ");
                String input = scanner.nextLine();

                switch (input) {
                    case "1":
                        oddChecker.run(scanner);
                        break;
                    case "2":
                        naturalNumberSum.run(scanner);
                        break;
                    case "3":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } finally {
            // No scanner.close() — we do NOT close System.in
        }
    }
}
