import { Quality } from "@app/core/models/Analysis/quality.model";

export interface AnalysisFile {
  filename: string;
  qualities: Quality[];
}
