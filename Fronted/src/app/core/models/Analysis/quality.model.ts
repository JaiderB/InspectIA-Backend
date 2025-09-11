import { Defect } from "@app/core/models/Analysis/defect.model";
import { QualityCategory } from "assets/Typescript-generalities/Types";

export interface Quality {
  qualityCategory: QualityCategory;
  score: number;
  defects: Defect[];
}
