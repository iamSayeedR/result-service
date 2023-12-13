package com.basirhat.resultservice.service;

import com.basirhat.questionnaires.model.QuestionAnswerResponse;
import com.basirhat.resultservice.client.QuestionnaireServiceClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionnaireServiceTest {
    @InjectMocks
    private QuestionnaireService questionnaireService;

    @Mock
    private QuestionnaireServiceClient questionnaireServiceClient;



    @Test
    void shouldTestReturnQuestionAnswerResponseWhenItIsCalledSuccessfully() {

        List<Integer> questionIdList = List.of(1, 2);
        QuestionAnswerResponse questionAnswerResponse = QuestionAnswerResponse.builder().build();

        when(questionnaireServiceClient.getQuestionAnswers(questionIdList)).thenReturn(
                ResponseEntity.ok(questionAnswerResponse));

        QuestionAnswerResponse questionAnswers = questionnaireService.getQuestionAnswers(questionIdList);

        assertEquals(questionAnswerResponse, questionAnswers);

    }





}