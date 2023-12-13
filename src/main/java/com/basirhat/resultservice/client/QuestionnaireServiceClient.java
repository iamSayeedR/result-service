package com.basirhat.resultservice.client;


import com.basirhat.questionnaires.model.Question;
import com.basirhat.questionnaires.model.QuestionAnswerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "questionnaireServiceClient", url = "${questionnaire-service.url}")
public interface QuestionnaireServiceClient {

    @GetMapping("${questionnaire-service.answer-end-point}")
    ResponseEntity<QuestionAnswerResponse> getQuestionAnswers(@RequestBody List<Integer> questionIdList);

}
