package io.inspectia.app.model.infra.POJO.Enums.AI;

import lombok.Getter;

@Getter
public enum PromptsPath {

    ASSISTANT_ANALYZER_PROMPT_FILE("src/main/java/io/inspectia/app/model/infra/POJO/Enums/AI/analysisPromptAssistent");
    private final String filePath;

    PromptsPath(String filePath) {
        this.filePath = filePath;
    }

}
