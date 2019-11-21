package tothek.raf.fg.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/classic")
public class ClassicEndpoints {

    @GetMapping("/alwaysOk")
    public ResponseEntity alwaysOk() throws InterruptedException {
        return ResponseEntity.ok(null);
    }
    
    @GetMapping("/alwaysOkConstantDelay")
    public ResponseEntity alwaysOkConstantDelay() throws InterruptedException {
        Thread.sleep(250l);
        return ResponseEntity.ok(null);
    }
}
