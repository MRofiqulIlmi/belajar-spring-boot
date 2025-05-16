package com.example.logicalquizmanual;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class OddChecker implements CommandLineRunner {

    public String oddCheckerFunc(int number){
        return number % 2 == 0 ? "Odd Number" : "Even Number";
    }

    @Override
    public void run(String... args) {
        System.out.println("Spring Boot app started. Running logic...");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter a number (or type 'exit' to quit): ");
                if (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    String resultTest = this.oddCheckerFunc(number);
                    System.out.println(resultTest);
                } else {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting...");
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter an integer or 'exit'.");
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("No input received (maybe from Gradle or terminal issue).");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    
}
