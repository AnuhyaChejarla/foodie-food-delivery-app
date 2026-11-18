
package com.tap;

import java.util.Scanner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your admin password: ");
        String password = scanner.nextLine();

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String hashedPassword = encoder.encode(password);

        System.out.println("BCrypt Hash: " + hashedPassword);

        scanner.close();
    }
}
