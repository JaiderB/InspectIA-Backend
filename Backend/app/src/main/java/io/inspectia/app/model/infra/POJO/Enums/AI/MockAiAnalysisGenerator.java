package io.inspectia.app.model.infra.POJO.Enums.AI;

import io.inspectia.app.model.infra.DTO.Entities.Analysis;
import io.inspectia.app.model.infra.DTO.Entities.AnalysisFile;
import io.inspectia.app.model.infra.DTO.Entities.Defect;
import io.inspectia.app.model.infra.DTO.Entities.Quality;
import io.inspectia.app.model.infra.POJO.Enums.Entites.QualityCategory;
import io.inspectia.app.model.infra.POJO.Enums.Entites.Severity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Servicio de simulación para generar análisis de IA sin llamar a un modelo real.
 */
@Component("mockAiAnalysisGenerator")
public class MockAiAnalysisGenerator {

    private static final Random random = new Random();

    /**
     * Genera un objeto de Análisis simulado basado en una lista de archivos.
     *
     * @param files Lista de archivos MultipartFile a analizar.
     * @return Un objeto Analysis completamente poblado con datos simulados.
     * @throws IOException Si ocurre un error al leer los archivos.
     */
    public static Analysis generateAnalysis(List<MultipartFile> files) throws IOException {
        Analysis analysis = new Analysis();
        analysis.setValidationDate(Instant.now().toString());
        int maxDefects = 40;
        int minDefects = 1;

        List<AnalysisFile> analysisFiles = new ArrayList<>();
        double totalScoreSum = 0;
        int totalQualityCategories = 0;

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            AnalysisFile analysisFile = new AnalysisFile();
            analysisFile.setFilename(file.getOriginalFilename());

            long totalLines = new String(file.getBytes()).lines().count();
            if (totalLines == 0) totalLines = 1;

            List<Quality> qualities = new ArrayList<>();
            for (QualityCategory category : QualityCategory.values()) {
                Quality quality = new Quality();
                quality.setQualityCategory(category);

                int defectCount = random.nextInt(maxDefects - minDefects + 1) + 1;
                defectCount = (int) Math.min(defectCount, totalLines);

                Set<Integer> usedLines = new HashSet<>();
                List<Defect> defects = new ArrayList<>();
                for (int i = 0; i < defectCount; i++) {
                    int lineNumber;
                    do {
                        lineNumber = random.nextInt((int) totalLines) + 1;
                    } while (usedLines.contains(lineNumber));
                    usedLines.add(lineNumber);

                    Severity severity = Severity.values()[random.nextInt(Severity.values().length)];
                    defects.add(new Defect(lineNumber, severity));
                }
                quality.setDefects(defects);

                double score = 10.0 - (((double) defects.size() / totalLines)* 10.0) ;
                BigDecimal bigDecimalScore = new BigDecimal(Double.toString(score)).setScale(2, RoundingMode.HALF_UP);
                quality.setScore(bigDecimalScore.doubleValue());
                qualities.add(quality);

                totalScoreSum += quality.getScore();
                totalQualityCategories++;
            }
            analysisFile.setQualities(qualities);
            analysisFiles.add(analysisFile);
        }

        double averageScore = totalScoreSum / totalQualityCategories;
        BigDecimal bigDecimalAverageScore = new BigDecimal(Double.toString(averageScore)).setScale(2, RoundingMode.HALF_UP);
        analysis.setScore(bigDecimalAverageScore.doubleValue());
        analysis.setFiles(analysisFiles);

        return analysis;
    }
}