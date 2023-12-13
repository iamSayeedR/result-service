package com.basirhat.resultservice.service;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.basirhat.questionnaires.model.Answer;
import com.basirhat.questionnaires.model.AnswerRequest;
import com.basirhat.questionnaires.model.QuestionAnswer;
import com.basirhat.questionnaires.model.QuestionAnswerResponse;
import com.basirhat.resultservice.util.LogUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @InjectMocks
   private ResultService resultService;

    @Mock
    private QuestionnaireService questionnaireService;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        listAppender = LogUtil.buildTestLogListAppender(ResultService.class);
    }

    @Test
    void shouldTestVerifyTheBothAnswers() {



        AnswerRequest answerRequest = AnswerRequest.builder().answers(List.of(
                        Answer.builder().questionId(1).answers(List.of("B", "A")).build(),
                        Answer.builder().questionId(3).answers(List.of("A")).build(),
                        Answer.builder().questionId(7).answers(List.of("B")).build(),
                        Answer.builder().questionId(10).answers(List.of("D")).build(),
                        Answer.builder().questionId(12).answers(List.of()).build()))
                .build();
        List<Integer> questionIdList = answerRequest.answers().stream().map(Answer::questionId).toList();

        QuestionAnswerResponse questionAnswerResponse = QuestionAnswerResponse.builder().questionAnswers(List.of(
                        QuestionAnswer.builder().questionId(1).answer(List.of("A", "B")).build(),
                                QuestionAnswer.builder().questionId(3).answer(List.of( "B")).build(),
                                QuestionAnswer.builder().questionId(7).answer(List.of("B")).build(),
                                QuestionAnswer.builder().questionId(10).answer(List.of("D")).build(),
                                QuestionAnswer.builder().questionId(12).answer(List.of("A")).build()))

                .build();



        when(questionnaireService.getQuestionAnswers(questionIdList)).thenReturn(questionAnswerResponse);

        resultService.processMessage(answerRequest);

        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .anySatisfy(s -> assertThat(s).contains("You have scored 60.0%"))
                .anySatisfy(s -> assertThat(s).contains("You are Passed"));
    }
}