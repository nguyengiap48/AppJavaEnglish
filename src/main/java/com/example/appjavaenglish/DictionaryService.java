package com.example.appjavaenglish;

import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DictionaryService extends Mysql {
    public ArrayList<Dictionary> getAllWordAv() {
        ArrayList<Dictionary> list = new ArrayList<>();
        try {
            String sql = "select * from av";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Dictionary dictionary = new Dictionary();
                dictionary.setId(rs.getInt(1));
                dictionary.setWord(rs.getString(2));
                dictionary.setHtml(rs.getString(3));
                dictionary.setDescription(rs.getString(4));
                dictionary.setPronounce(rs.getString(5));
                list.add(dictionary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Dictionary searchByWordAv(String s) {
        Dictionary dictionary = new Dictionary();
        try {
            String msql = "select * from av where word = ?";
            PreparedStatement pstm = connection.prepareStatement(msql);
            pstm.setString(1, s);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                dictionary.setId(resultSet.getInt(1));
                dictionary.setWord(resultSet.getString(2));
                dictionary.setHtml(resultSet.getString(3));
                dictionary.setDescription(resultSet.getString(4));
                dictionary.setPronounce(resultSet.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }
    public void updateDictionaryAv(Dictionary dictionary) {
        String msql = "UPDATE av SET id = ?, html = ?, description=?, pronounce = ? WHERE word = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(msql);
            pstm.setInt(1, dictionary.getId());
            pstm.setString(2, dictionary.getHtml());
            pstm.setString(3, dictionary.getDescription());
            pstm.setString(4, dictionary.getPronounce());
            pstm.setString(5, dictionary.getWord());
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteDictionaryAv(String word) {
        String msql = "DELETE FROM av WHERE word = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(msql);
            pstm.setString(1, word);
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addDictionaryAv(Dictionary dictionary) {
        String msql = "INSERT INTO av VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(msql);
            pstm.setInt(1, dictionary.getId());
            pstm.setString(2, dictionary.getWord());
            pstm.setString(3, dictionary.getHtml());
            pstm.setString(4, dictionary.getDescription());
            pstm.setString(5, dictionary.getPronounce());
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Dictionary> getAllWordVa() {
        ArrayList<Dictionary> dictionaries = new ArrayList<>();
        try {
            String msql = "SELECT * FROM va";
            PreparedStatement pstm = connection.prepareStatement(msql);
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                Dictionary dictionary = new Dictionary();
                dictionary.setId(resultSet.getInt(1));
                dictionary.setWord(resultSet.getString(2));
                dictionary.setHtml(resultSet.getString(3));
                dictionary.setDescription(resultSet.getString(4));
                dictionaries.add(dictionary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaries;
    }

    public void updateDictionaryVa(Dictionary dictionary) {
        String msql = "UPDATE va SET id = ?, html = ?, description=? WHERE word = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(msql);
            pstm.setInt(1, dictionary.getId());
            pstm.setString(2, dictionary.getHtml());
            pstm.setString(3, dictionary.getDescription());
            pstm.setString(4, dictionary.getWord());
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDictionaryVa(String word) {
        String msql = "DELETE FROM va WHERE word = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(msql);
            pstm.setString(1, word);
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDictionaryVa(Dictionary dictionary) {
        String msql = "INSERT INTO va VALUES (?,?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(msql);
            pstm.setInt(1, dictionary.getId());
            pstm.setString(2, dictionary.getWord());
            pstm.setString(3, dictionary.getHtml());
            pstm.setString(4, dictionary.getDescription());
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
