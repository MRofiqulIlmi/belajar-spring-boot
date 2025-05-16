package com.example.logicalquizmanual;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Component
public class NaturalNumberSum{

    public int sumOfNaturalNumber(int number){
        int sum = 0;
        int count = 0;
        while(count < number){
            count += 1;
            sum += count;
        }

        return sum;
    }

    public void run(Scanner scanner) {
        System.out.println("Spring Boot app started. Running logic...");

            
                System.out.print("Enter a number (or type 'exit' to quit): ");
                if (scanner.hasNextInt()) {
                    int number = scanner.nextInt();

                    if(number <= 0){
                        System.out.println("Invalid input. Please enter an Natural Number integer or 'exit'.");
                    }else{
                        scanner.nextLine(); // consume newline
                        int resultTest = this.sumOfNaturalNumber(number);
                        System.out.println(resultTest);
                    }

                    
                } else {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting...");
                        
                    } else {
                        System.out.println("Invalid input. Please enter an integer or 'exit'.");
                    }
                }
            
        
    }

    
}
