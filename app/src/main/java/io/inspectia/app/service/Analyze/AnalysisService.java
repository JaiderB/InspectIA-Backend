package io.inspectia.app.service.Analyze;

import io.inspectia.app.model.Domain.DTO.Entities.AnalysisResponse;
import io.inspectia.app.model.Mapper.AnalysisMapper;
import io.inspectia.app.model.infra.DTO.DB.AnalysisDB;
import io.inspectia.app.model.infra.DTO.Entities.Analysis;
import io.inspectia.app.model.infra.DTO.Entities.User;
import io.inspectia.app.model.infra.POJO.Enums.AI.PromptsPath;
import io.inspectia.app.repository.Firebase.Analyze.AnalysisRepository;
import io.inspectia.app.service.Analyze.AI.AiAnalyzer;
import io.inspectia.app.service.Auth.UserService;
import io.inspectia.app.service.Manager.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    public AnalysisResponse createAnalysis(List<MultipartFile> files, String token) throws IOException, ExecutionException, InterruptedException {
        Optional<Analysis> analysisResult = aiAnalyzer.makeAnalysis(files);

        Analysis analysisSaved = saveAnalysis(analysisResult, token);
        analysisSaved.setValidationDate(new Date());

        return AnalysisMapper.fromAnalysisToResponse(analysisSaved);
    }

    public AnalysisResponse createIaAnalysis(List<MultipartFile> files, String token) throws IOException, ExecutionException, InterruptedException {
        Optional<Analysis> analysisResult = aiAnalyzer.makeAnalysis(PromptsPath.ASSISTANT_ANALYZER_PROMPT_FILE, files);
        Analysis analysisSaved = saveAnalysis(analysisResult, token);
        return AnalysisMapper.fromAnalysisToResponse(analysisSaved);
    }

    private Analysis saveAnalysis(Optional<Analysis> analysisResult, String token) throws ExecutionException, InterruptedException, IOException {


        Optional<User> user = userService.getByEmail(jwtManager.extractEmail(jwtManager.getTokenFromBearer(token)));

        if(analysisResult.isPresent() && user.isPresent()){

            analysisResult.get().setFingerprintEmail(user.get().getEmail());
            AnalysisDB analysisSaved = analysisRepository.save(AnalysisMapper.fromAnalysisToDB(analysisResult.get()));

            userService.update(User.builder()
                            .uuid(user.get().getUuid())
                            .reports(List.of(analysisSaved.getUuid()))
                            .build());

            return analysisResult.get();
        }

        throw new RuntimeException("It was not possible to generate the analysis.");
    }


    public List<AnalysisResponse> getByUserEmail(String userToken) throws ExecutionException, InterruptedException {

        String userEmail = jwtManager.extractEmail(jwtManager.getTokenFromBearer(userToken));
        Optional<List<Analysis>> analysisList = analysisRepository.getByUserEmail(userEmail);

        return AnalysisMapper.fromEntitiesToResponse(analysisList.orElseGet(ArrayList::new));
    }

    public void delete(String hashId){
        analysisRepository.delete(hashId);
    }
}
