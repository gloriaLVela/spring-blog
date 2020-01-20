package com.codeup.blog.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;

import static java.lang.Math.random;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice/{dice}")
    public String currentNumber(@PathVariable Integer dice, Model vModel) {
       int random = (int) (Math.random() * ((6 - 1) + 1)) + 1;
        System.out.println("dice = " + dice);
        vModel.addAttribute("dice", dice);
        if (dice == random) {
            vModel.addAttribute("result", "The numbers " + Integer.toString(dice) + " and " + Integer.toString(random) + " are the same!");
        } else {
            vModel.addAttribute("result", "The numbers " + Integer.toString(dice) + " and " + Integer.toString(random) + " are different!");
        }
        return "roll-dice";
    }


}
