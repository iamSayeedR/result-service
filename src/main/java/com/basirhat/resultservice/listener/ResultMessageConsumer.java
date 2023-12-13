package com.basirhat.resultservice.listener;

import com.basirhat.questionnaires.model.AnswerRequest;
import com.basirhat.resultservice.service.ResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResultMessageConsumer {

    private final ResultService resultService;


    @RabbitListener(queues = "${exam.result.queue-name}")
    public void processMessage(@Payload AnswerRequest answerRequest) {
        resultService.processMessage(answerRequest);
        log.info("Processing message {}", answerRequest);
    }
}
