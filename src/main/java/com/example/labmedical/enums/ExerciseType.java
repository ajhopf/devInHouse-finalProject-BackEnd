package com.example.labmedical.enums;

public enum ExerciseType {
    AEROBIC_ENDURANCE("Resistência Aeróbica"),
    MUSCULAR_ENDURANCE("Resistência Muscular"),
    FLEXIBILITY("Flexibilidade"),
    STRENGTH("Força"),
    AGILITY("Agilidade"),
    OTHER("Outro");

    private final String value;

    ExerciseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
