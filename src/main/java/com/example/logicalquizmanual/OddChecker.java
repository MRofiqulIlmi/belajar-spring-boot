package com.example.logicalquizmanual;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class OddChecker {

    public String oddCheckerFunc(int number){
        return number % 2 == 0 ? "Even Number" : "Odd Number";
    }

    public void run(Scanner scanner) {
        System.out.println("Spring Boot app started. Running logic...");

        
            
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
                        
                    } else {
                        System.out.println("Invalid input. Please enter an integer or 'exit'.");
                    }
                }
            
            System.out.println("odd checker skipped");
        
    }

    
}
