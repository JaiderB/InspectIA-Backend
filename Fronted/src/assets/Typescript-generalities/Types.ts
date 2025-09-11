
export type typeSize = 'XS' | 'S' | 'M' | 'L' | 'XL' | 'XXL' | 'XXXL';
export type color = 'red' | 'gray' | 'yellow' | 'green' | 'default';
export type modalTypes = 'error' | 'success' | 'warning' | 'info' | 'delete' | 'wrongCredentials' | 'unableOption' | 'emptyFields' | '';
export type state = {
  id: number,
  name: 'Activo' | 'Inactivo' | "Seleccione"
}

export type typeOperationsForm = "deleteAnalysis" | "generateAnalysis" | "logout" | "none" | "analysisGenerated";
export type crudEntitys = "Analysis";
export type Severity = 'HIGH' | 'MEDIUM' | 'LOW';
export type QualityCategory = 'SYNTAX_ERROR' | 'STYLE_AND_CONVENTIONS' | 'CYCLOMATIC_COMPLEXITY' | 'DUPLICATED_CODE';
