package io.inspectia.app.model.infra.POJO.Enums.Entites;

public enum Severity {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    final String severity;

    Severity(String severity) {
        this.severity = severity;
    }
}
