package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.util.error.idInvalidException;

@RestController
public class HelloController {

    @GetMapping("/")
    public String getHelloWorld() throws idInvalidException {
        return "Hello World (Hỏi Dân IT & Eric)";
    }
}
