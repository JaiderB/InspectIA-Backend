package io.inspectia.app.controller.Analyze;

import io.inspectia.app.model.infra.DTO.Entities.Analysis;
import io.inspectia.app.service.Analyze.AnalysisService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/analyze")
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/create")
    public ResponseEntity<Analysis> create(@RequestHeader("Authorization") String token, @RequestBody() List<MultipartFile> files) throws ExecutionException, InterruptedException, IOException {

        return ResponseEntity.ok(analysisService.createAnalysis(files, token));
    }

    @PostMapping("/ia/create")
    public ResponseEntity<Analysis> iaCreate(@RequestHeader("Authorization") String token, @RequestBody() List<MultipartFile> files) throws ExecutionException, InterruptedException, IOException {

        return ResponseEntity.ok(analysisService.createIaAnalysis(files, token));
    }
}
