package com.example.appjavaenglish;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class QuizService extends MySqlQuiz {
    public ArrayList<Quiz> getAllQuiz() {
        ArrayList<Quiz> quizArrayList = new ArrayList<>();
        try {
            String msql = "select * from quizeasy";
            PreparedStatement pstm = connection.prepareStatement(msql);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setId(resultSet.getString(1));
                quiz.setQuestion(resultSet.getString(2));
                quiz.setAnswer1(resultSet.getString(3));
                quiz.setAnswer2(resultSet.getString(4));
                quiz.setAnswer3(resultSet.getString(5));
                quiz.setAnswer4(resultSet.getString(6));
                quiz.setCorrectAnswer(resultSet.getString(7));
                quizArrayList.add(quiz);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizArrayList;
    }
}
