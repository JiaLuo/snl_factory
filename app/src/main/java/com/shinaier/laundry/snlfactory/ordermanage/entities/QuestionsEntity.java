package com.shinaier.laundry.snlfactory.ordermanage.entities;

import java.util.List;

/**
 * Created by 张家洛 on 2017/3/9.
 */

public class QuestionsEntity {
    private List<List<QuestionSettingEntity>> questions;

    public List<List<QuestionSettingEntity>> getQuestions() {
        return questions;
    }

    public void setQuestions(List<List<QuestionSettingEntity>> questions) {
        this.questions = questions;
    }
}
