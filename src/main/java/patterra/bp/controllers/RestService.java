package patterra.bp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

    @GetMapping("/test")
    public String testPath() {
        return "test...";
    }
}