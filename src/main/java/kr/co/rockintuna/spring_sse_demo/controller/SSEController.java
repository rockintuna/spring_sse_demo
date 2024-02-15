package kr.co.rockintuna.spring_sse_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

@RestController
public class SSEController {

    private boolean hasMessage = false;
    private String message = null;

    @GetMapping("/stream-sse-mvc")
    public SseEmitter streamSseMvc() {
        SseEmitter emitter = new SseEmitter();
        ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                while (true) {
                    while (!hasMessage) {
                        sleep(1000L);
                    }
                    SseEmitter.SseEventBuilder event = SseEmitter.event()
                            .data(message)
                            .id("0")
                            .name("sse event - mvc");
                    emitter.send(event);
                    hasMessage = false;
                }
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

    @GetMapping("/push")
    public void push(@RequestParam("message") String message) {
        this.hasMessage = true;
        this.message = message;
    }
}
