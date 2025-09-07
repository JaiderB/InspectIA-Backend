package io.inspectia.app.service.Analyze.AI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.inspectia.app.model.infra.DTO.Entities.Analysis;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.infra.POJO.Enums.AI.MockAiAnalysisGenerator;
import io.inspectia.app.model.infra.POJO.Enums.AI.PromptsPath;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import org.springframework.ai.chat.messages.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

@AllArgsConstructor
@Component("aiAnalyzer")
public class AiAnalyzer {


    private final Gson gson;
    private final OllamaChatModel ollamaChatClient;

    public Optional<Analysis> makeAnalysis(PromptsPath promptPath, List<MultipartFile> files) throws IOException {

        String promptBase = Files.readString(Paths.get(promptPath.getFilePath()));
        var generalInstructionsSystemMessage = new SystemMessage(promptBase);

//        List<Media> mediaResources = files.stream().map(file -> {
//            Resource fileResource = file.getResource();
//            return new Media(MimeType.valueOf(Objects.requireNonNull(file.getContentType())), fileResource);
//        }).toList();

        UserMessage userMessage = new UserMessage("Genera el Json con base en la siguiente información:\n\n");

        StringBuilder combinedCode = new StringBuilder();
        for (MultipartFile file : files) {

            combinedCode
                    .append("filename: ")
                    .append(file.getOriginalFilename()).append("\nlines: ").append(countNewlines(file))
                    .append("\n\n\n");
        }

        UserMessage codeMessage = new UserMessage(combinedCode.toString());
        Prompt prompt = new Prompt(List.of(generalInstructionsSystemMessage, userMessage, codeMessage));


        try{
            String chatModelResponse = ollamaChatClient.call(prompt).getResult().getOutput().getContent();
            String response = extractValidJson(chatModelResponse);
            JsonObject jsonObject = JsonParser.parseString(Objects.requireNonNull(response)).getAsJsonObject();

            Analysis analysis = gson.fromJson(jsonObject, Analysis.class);
            return Optional.ofNullable(analysis);
        }catch (JsonSyntaxException e){
            return Optional.empty();
        }

    }


    public Optional<Analysis> makeAnalysis(List<MultipartFile> files) throws IOException {

        try{
            return Optional.of(MockAiAnalysisGenerator.generateAnalysis(files));
        }catch (JsonSyntaxException e){
            return Optional.empty();
        }

    }


    private long countNewlines(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return 0;
        }

        byte[] bytes = file.getBytes();
        long newlineCount = 0;

        for (byte b : bytes) {
            if (b == '\n') {
                newlineCount++;
            }
        }

        return newlineCount;
    }

    private String extractValidJson(String response) {
        int startIndex = response.indexOf("{");

        if (startIndex == -1) {
            return null;
        }

        int braceCount = 0;
        int endIndex = -1;

        for (int i = startIndex; i < response.length(); i++) {
            char c = response.charAt(i);
            if (c == '{') {
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    endIndex = i;
                    break;
                }
            }
        }

        if (endIndex != -1) {
            return response.substring(startIndex, endIndex + 1);
        }

        return null;
    }

}
