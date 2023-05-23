package sample.api.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {
  @GetMapping("/just")
  public Mono<String> just() {
    return Mono.just("just");
  }
}
