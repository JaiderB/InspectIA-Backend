package io.inspectia.app.service.Analyze;

import io.inspectia.app.model.infra.DTO.Entities.Analysis;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.infra.POJO.Enums.AI.PromptsPath;
import io.inspectia.app.repository.Firebase.Analyze.AnalysisRepository;
import io.inspectia.app.service.Analyze.AI.AiAnalyzer;
import io.inspectia.app.service.Auth.UserService;
import io.inspectia.app.service.Manager.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class AnalysisService {

    private final AiAnalyzer aiAnalyzer;
    private final UserService userService;
    private final AnalysisRepository analysisRepository;
    private final JwtManager jwtManager;

    public Analysis createAnalysis(List<MultipartFile> files, String token) throws IOException, ExecutionException, InterruptedException {
        Optional<Analysis> analysisResult = aiAnalyzer.makeAnalysis(files);
        return saveAnalysis(analysisResult, token);
    }

    public Analysis createIaAnalysis(List<MultipartFile> files, String token) throws IOException, ExecutionException, InterruptedException {
        Optional<Analysis> analysisResult = aiAnalyzer.makeAnalysis(PromptsPath.ASSISTANT_ANALYZER_PROMPT_FILE, files);
        return saveAnalysis(analysisResult, token);
    }

    private Analysis saveAnalysis(Optional<Analysis> analysisResult, String token) throws ExecutionException, InterruptedException, IOException {


        Optional<User> user = userService.getByEmail(jwtManager.extractEmail(jwtManager.getTokenFromBearer(token)));

        if(analysisResult.isPresent() && user.isPresent()){

            analysisResult.get().setFingerprintEmail(user.get().getEmail());
            Analysis analysisSaved = analysisRepository.save(analysisResult.get());
            userService.update(User.builder()
                            .uuid(user.get().getUuid())
                            .reports(List.of(analysisSaved.getUuid()))
                            .build());

            return analysisResult.get();
        }

        throw new RuntimeException("It was not possible to generate the analysis.");
    }


}
