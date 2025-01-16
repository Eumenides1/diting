package com.rookie.diting.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AIModelType {

    OPEN_AI("OPENAI","gpt4");

    private final String name;
    private final String defaultModel;

}
