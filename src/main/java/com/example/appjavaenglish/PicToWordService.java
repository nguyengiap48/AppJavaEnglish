package com.example.appjavaenglish;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PicToWordService extends MySqlGame {
    public ArrayList<PictureToWord> getAllPic() {
        ArrayList<PictureToWord> list = new ArrayList<>();
        try {
            String msql = "select * from picturetoword";
            PreparedStatement pstm = connection.prepareStatement(msql);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                PictureToWord pictureToWord = new PictureToWord();
                pictureToWord.setId(resultSet.getInt(1));
                pictureToWord.setImage(resultSet.getString(2));
                pictureToWord.setWord1(resultSet.getString(3));
                pictureToWord.setWord2(resultSet.getString(4));
                pictureToWord.setWord3(resultSet.getString(5));
                pictureToWord.setWord4(resultSet.getString(6));
                pictureToWord.setWord5(resultSet.getString(7));
                pictureToWord.setWord6(resultSet.getString(8));
                pictureToWord.setWord7(resultSet.getString(9));
                pictureToWord.setWord8(resultSet.getString(10));
                pictureToWord.setWord9(resultSet.getString(11));
                pictureToWord.setWord10(resultSet.getString(12));
                pictureToWord.setWordCorrect(resultSet.getString(13));
                list.add(pictureToWord);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
