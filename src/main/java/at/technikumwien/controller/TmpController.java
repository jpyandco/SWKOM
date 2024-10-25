package at.technikumwien.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TmpController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public String getAllItems() {
        return "GET";
    }

    @PostMapping
    public String createItem() {
        return "POST";
    }

    @PutMapping
    public String updateItem() {
        return "PUT";
    }

    @DeleteMapping
    public String deleteItem() {
        return "DELETE";
    }
}
