package com.basirhat.resultservice.model;

import lombok.Builder;

@Builder
public record Result(
        double totalQuestions,
        double score,
        String result){
}
