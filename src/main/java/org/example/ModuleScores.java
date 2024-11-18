package org.example;

public class ModuleScores {
    private final int questionsScore;
    private final int exercisesScore;
    private final int practiceScore;

    public ModuleScores(int questionsScore, int exercisesScore, int practiceScore) {
        this.questionsScore = questionsScore;
        this.exercisesScore = exercisesScore;
        this.practiceScore = practiceScore;
    }

    public int getQuestionsScore() {
        return questionsScore;
    }

    public int getExercisesScore() {
        return exercisesScore;
    }

    public int getPracticeScore() {
        return practiceScore;
    }
}
