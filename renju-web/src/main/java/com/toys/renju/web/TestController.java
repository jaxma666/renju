package com.toys.renju.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lingyao on 16/5/21.
 */

@Controller
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "done";
    }

//    @MessageMapping("/hello")
//    public Greeting greeting(HelloMessage message) throws Exception {
//        Thread.sleep(3000); // simulated delay
//        return new Greeting("Hello, " + message.getName() + "!");
//    }

}
