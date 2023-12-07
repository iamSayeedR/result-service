package com.basirhat.resultservice.listener;

import com.basirhat.questionnaires.model.AnswerRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResultMessageConsumer {

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${exam.result.queue-name}")
    public void processMessage(@Payload AnswerRequest answerRequest) {

        log.info("Processing message {}", answerRequest);
    }
}
