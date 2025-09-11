import { AnalysisFile } from "@app/core/models/Analysis/analysis-file.model";

export interface Analysis {
  uuid: string;
  validationDate: Date;
  score: number;
  files: AnalysisFile[];
  fingerprintEmail: string;
}
