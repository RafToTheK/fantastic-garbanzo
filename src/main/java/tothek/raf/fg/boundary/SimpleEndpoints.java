package tothek.raf.fg.boundary;

import java.time.Duration;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@Slf4j
@RequestMapping("/simple")
public class SimpleEndpoints {
    
    private final Flux<Long> randomNumbers = Flux.fromStream(() -> new Random().longs(0,1000).boxed());
    
    @GetMapping("/alwaysOk")
    public Mono alwaysOk() {
        return Mono.empty();
    }
    
    @GetMapping("/alwaysOkRandomDelay")
    public Mono alwaysOkRandomDelay() {
        return randomNumbers.next()
                .flatMap(l -> Mono.empty().delaySubscription(Duration.ofMillis(l)))
                .subscribeOn(Schedulers.elastic());
    }
    
    @GetMapping("/alwaysOkConstantDelay")
    public Mono alwaysOkConstantDelay() {
        return Mono.empty()
                .delaySubscription(Duration.ofMillis(250l));
    }
    
    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, path = "/echo") 
    public Mono<String> echo(@RequestBody String toEcho) {
        return Mono.fromCallable(() -> toEcho);
    }
    
    @GetMapping("alwaysBad")
    public Mono alwaysBadRequest() {
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
    
    @GetMapping("alwaysError")
    public Mono alwayServerError() {
        return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
