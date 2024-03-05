package rastak.train.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UController {

    @GetMapping
    public String get() {
        return "GET:: management controller";
    }
}
