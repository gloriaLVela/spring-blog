package com.codeup.blog.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MathController {


    @RequestMapping(path = "/add/{number1}/and/{number2}", method = RequestMethod.GET)
    @ResponseBody
    public String addNumbers(@PathVariable int number1, @PathVariable int number2) {
        return "The sum is " + Integer.toString(number1 + number2) + "!";
    }


    @RequestMapping(path = "/subtract/{number1}/from/{number2}", method = RequestMethod.GET)
    @ResponseBody
    public String subtractNumbers(@PathVariable int number1, @PathVariable int number2) {
        return "The result is " + Integer.toString(number2 - number1) + "!";
    }

    @RequestMapping(path = "/multiply/{number1}/and/{number2}", method = RequestMethod.GET)
    @ResponseBody
    public String multiplyNumbers(@PathVariable int number1, @PathVariable int number2) {
        return "The multiplication is " + Integer.toString(number1 * number2) + "!";
    }

    @RequestMapping(path = "/divide/{number1}/by/{number2}", method = RequestMethod.GET)
    @ResponseBody
    public String divideNumbers(@PathVariable double number1, @PathVariable double number2) {
        if(number2 == 0) {
            return number1 + " cannot be divided by zero";
        }
        return "The division is " + Double.toString(number1 / number2) + "!";
    }
}
