package com.lab4.demo.presentation;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Scanner;

@Controller
public class UI {
    @PostConstruct
    public void on_startup() throws Exception {
        System.out.println("""
                Select an operation:
                1 - Perform new calculation
                2 - Show results of previous calculations
                Please enter 1 or 2:\s""");
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()) {
            case 1:
                Calc_starter.activate();
            case 2:
                Results_reader.start();
        }
    }
}
