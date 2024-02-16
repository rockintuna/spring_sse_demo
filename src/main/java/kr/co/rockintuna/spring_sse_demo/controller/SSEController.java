package kr.co.rockintuna.spring_sse_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SSEController {

    private List<SseEmitter> emitterList = new ArrayList<>();

    @GetMapping("/stream-sse-mvc")
    public SseEmitter streamSseMvc() throws IOException {
        SseEmitter emitter = new SseEmitter();
        emitter.send(SseEmitter.event()
                .name("connect")         // 해당 이벤트의 이름 지정
                .data("connected!"));
        emitter.onCompletion(() -> emitterList.remove(emitter));
        emitter.onTimeout(() -> emitterList.remove(emitter));
        emitter.onError((ex) -> {
            System.out.println(ex.getMessage());
            emitterList.remove(emitter);
        });
        emitterList.add(emitter);
        return emitter;
    }

    @GetMapping("/push")
    public void push(@RequestParam("message") String message) throws IOException {
        System.out.println(emitterList.size());
        for (SseEmitter sseEmitter : emitterList) {
            sseEmitter.send(message);
        }
    }
}
