package com.lab4.demo.presentation;

import com.lab4.demo.service.Math_model;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;

@Controller
public abstract class Calc_starter {
    @PostConstruct
    public static void activate() throws Exception {
        Math_model.calculate();
    }
}
