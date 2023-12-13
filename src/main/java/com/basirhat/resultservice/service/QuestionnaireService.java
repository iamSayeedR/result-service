package com.basirhat.resultservice.service;

import com.basirhat.questionnaires.model.Answer;
import com.basirhat.questionnaires.model.QuestionAnswerResponse;
import com.basirhat.resultservice.client.QuestionnaireServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionnaireService {

    private final QuestionnaireServiceClient questionnaireServiceClient;

    public QuestionAnswerResponse getQuestionAnswers(List<Integer> questionIdList) {

        log.info("getting list of questions");

        ResponseEntity<QuestionAnswerResponse> listResponseEntity = questionnaireServiceClient.getQuestionAnswers(questionIdList);

        if (listResponseEntity.getStatusCode().is2xxSuccessful()) {
            return listResponseEntity.getBody();
        } else {
            throw new RestClientException("Could not able to call Questionnaire Service Client.");
        }



    }


    public void answer (List<Answer> answerList) {

        log.info("getting list of answers");



    }


}
