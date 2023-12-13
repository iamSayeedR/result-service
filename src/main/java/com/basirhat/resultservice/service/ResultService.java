package com.basirhat.resultservice.service;

import com.basirhat.questionnaires.model.Answer;
import com.basirhat.questionnaires.model.AnswerRequest;
import com.basirhat.questionnaires.model.QuestionAnswer;
import com.basirhat.questionnaires.model.QuestionAnswerResponse;
import com.basirhat.resultservice.model.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultService {

    @Value("${no_of_questions_per_exam}")
    private int noOfQuestionsPerExam;

    @Value("${passing_score}")
    private double passingScore;

    private final QuestionnaireService questionnaireService;

    private final EmailService emailService;


    public void processMessage(AnswerRequest answerRequest) {
        List<Integer> questionIdList = answerRequest.answers().stream().map(Answer::questionId).toList();

        QuestionAnswerResponse questionAnswerResponse = questionnaireService.getQuestionAnswers(questionIdList);

        //answer
        //KEY - VALUE
        //1 - [B,A]
        //3 - [A]
        //7 - [B]
        //10 - [D]
        //12 - []


        //question answer - correct
        //KEY - VALUE
        //1 - [A,B] - EntrySet
        //3 - [B]
        //7 - [B]
        //10 - [D]
        //12 - [A]

        Map<Integer, List<String>> clientAnswers = answerRequest.answers().stream()
                .collect(Collectors.groupingBy(Answer::questionId, flatMapping(a -> a.answers().stream(), toList())));

        Map<Integer, List<String>> dbAnswers = questionAnswerResponse.questionAnswers().stream()
                .collect(Collectors.groupingBy(QuestionAnswer::questionId, flatMapping(q -> q.answer().stream(), toList())));

        long numOfCorrectAnswers = clientAnswers.entrySet().stream().filter(entry -> compareAnswer(entry, dbAnswers)).count();

        double totalQuestions = noOfQuestionsPerExam;
        double score = numOfCorrectAnswers * 100 / totalQuestions;
        String resultStr = (score >= passingScore) ? "Passed" : "Failed";


        log.info("You have scored {}%.", score);
        log.info("You are {}.", resultStr);

        Result result = Result.builder().result(resultStr)
                .score(score).totalQuestions(totalQuestions)
                .build();

        emailService.sendEmail(result);



    }

    private boolean compareAnswer(Map.Entry<Integer, List<String>> clientQuestionAnswerEntry, Map<Integer, List<String>> dbAnswers) {
        Integer questionId = clientQuestionAnswerEntry.getKey();
        List<String> clientAnswer = clientQuestionAnswerEntry.getValue();
        List<String> dbAnswer = dbAnswers.get(questionId);
        return clientAnswer.containsAll(dbAnswer);
    }
}

