package com.example.appjavaenglish;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Controller {
    private ObservableList<String> observableList;
    @FXML
    private ListView<String> listView = new ListView<>(observableList);
    @FXML
    private BorderPane contentView;
    @FXML
    private WebView webView;
    @FXML
    private HTMLEditor htmlEdit;
    @FXML
    private WebEngine webEngine;
    @FXML
    private Button btnSpeaker, btnSpeaker1, btnSpeaker2;
    @FXML
    private Button btnDictionary,btnGame,btnTranslates,btnExit, btnX, btnAdd, btnEdit, btnDelete;
    @FXML
    private TextArea txtWord;
    @FXML
    private BorderPane bdpHome;
    @FXML
    private AnchorPane acpContent;
    @FXML
    private AnchorPane acpTranslate;
    @FXML
    private AnchorPane acpQuiz;
    @FXML
    private AnchorPane acpPicToWord;
    @FXML
    private AnchorPane acpHtmlEdit;
    @FXML
    private AnchorPane acpGame;
    @FXML
    private GridPane gridPaneBtn;
    @FXML
    private TextField txtSearch;
    @FXML
    private VBox vbxAnswer;
    @FXML
    private HBox hbxLabelWord;
    @FXML
    private BorderPane bdpGameSelect;
    @FXML
    private Label lblQuestion;
    @FXML
    private ImageView imgEngVi;
    @FXML
    private Label lblScore, lblLevel, lblTime, lblHighScore, lblQues, lblTimeQuiz;
    @FXML
    private Button btnNext, btnBack, btnSubmit;
    @FXML
    private ImageView imgPictureGame;
    @FXML
    private Button btnWord1, btnWord2, btnWord3, btnWord4, btnWord5, btnWord6, btnWord7, btnWord8, btnWord9, btnWord10;
    @FXML
    private TextArea txtAreaVi, txtAreaEn;
    @FXML
    private Button btnA1, btnV1, btnA2, btnV2;
    private Boolean english = true;
    private Label lblWord[] = new Label[10];
    private ArrayList<Dictionary> list = new ArrayList<>();
    private HashMap<String, String> map = new LinkedHashMap<>();
    private ToggleGroup[] toggleGroup = new ToggleGroup[10];
    private RadioButton[][] rbtnAnswer = new RadioButton[10][4];
    private ArrayList<Quiz> quizList = new QuizService().getAllQuiz();
    private ArrayList<PictureToWord> listPicture = new PicToWordService().getAllPic();
    private int count = 1;
    private int score =0, level=1, time = 0;
    private static int highScore = 0;
    private Timeline timeline = new Timeline();

    public void initialize() {
        showAllWord();
        btnSpeaker.setVisible(false);
        acpContent.setTopAnchor(contentView, 0.0);
        acpContent.setBottomAnchor(contentView, 0.0);
        acpContent.setLeftAnchor(contentView, 0.0);
        acpContent.setRightAnchor(contentView, 0.0);

//        DictionaryService dictionaryService = new DictionaryService();
//        ArrayList<String> listWord = new ArrayList<>();
//        for (Dictionary dictionary : dictionaryService.getAllWordAv()) {
//            listWord.add(dictionary.getWord());
//        }
//        observableList = FXCollections.observableArrayList(listWord);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            listView.getItems().clear();

            for (String item : observableList) {
                if (item.toLowerCase().contains(newValue.toLowerCase())) {
                    listView.getItems().add(item);
                }
            }
        });
    }

    public void showAllWord() {
        if (english) {
            DictionaryService dictionaryService = new DictionaryService();
            list = dictionaryService.getAllWordAv();
            ArrayList<String> listWord = new ArrayList<>();
            for (Dictionary dictionary : list) {
                listWord.add(dictionary.getWord());
                map.put(dictionary.getWord(), dictionary.getHtml());
            }
            observableList = FXCollections.observableArrayList(listWord);
            listView.getItems().clear();
            for (String item : observableList) {
                listView.getItems().add(item);
            }
        } else {
            DictionaryService dictionaryService = new DictionaryService();
            list = dictionaryService.getAllWordVa();
            ArrayList<String> listWord = new ArrayList<>();
            for (Dictionary dictionary : list) {
                listWord.add(dictionary.getWord());
                map.put(dictionary.getWord(), dictionary.getHtml());
            }
            observableList = FXCollections.observableArrayList(listWord);
            listView.getItems().clear();
            for (String item : observableList) {
                listView.getItems().add(item);
            }
        }
    }

    public void speakWords(String word, String language) throws Exception {
        TextToSpeech textToSpeech = new TextToSpeech();
        textToSpeech.speakWord(word, language);
    }

    public void quizGame() {
        level = 1;
        for (int i = 0; i < 10; i++) {
            toggleGroup[i] = new ToggleGroup();
            for (int j = 0; j < 4; j++) {
                rbtnAnswer[i][j] = new RadioButton();
                rbtnAnswer[i][j].setToggleGroup(toggleGroup[i]);
            }
        }
        count=1;

        int count1 = 300;
        timeline.stop();
        timeline = new Timeline();
        lblTimeQuiz.setText("Time: 300s");

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    int countTime = count1;
                    @Override
                    public void handle(ActionEvent event) {
                        countTime--;
                        if (countTime >= 0) {
                            lblTimeQuiz.setText("Time: " + countTime + "s");
                        } else if (countTime<0){
                            timeline.stop();
                            lblTimeQuiz.setText("Time: 0s");

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Score");
                            alert.setHeaderText(null);
                            alert.setContentText("Điểm của bạn là:" + scoreQuiz());
                            alert.show();
                            bdpGameSelect.setVisible(true);
                            acpQuiz.setVisible(false);
                        }
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void picToWordGame() {
        for (int i = 0; i < 10; i++) {
            lblWord[i] = new Label();
            int finalI = i;
            lblWord[i].setOnMouseClicked(mouseEvent -> {
                handleLbl(lblWord[finalI]);
            });
        }
        gridPaneBtn.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setOnAction(event -> {
                    String x = button.getText();

//                    button.setVisible(false);
                    handleBtn(x,button);
                });
            }
        });
    }

    private void handleBtn(String x, Button button) {

        StringBuilder word = new StringBuilder("");
        for (int i = 0; i < listPicture.get(count).getWordCorrect().length(); i++) {
            if (lblWord[i].getText().isEmpty()) {
                lblWord[i].setText(x);
                button.setVisible(false);
                break;
            }
        }
        for (int i = 0; i < listPicture.get(count).getWordCorrect().length(); i++) {
            word.append(lblWord[i].getText());
        }

        if (word.toString().equals(listPicture.get(count).getWordCorrect()) && score<100) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Score");
            alert.setHeaderText(null);
            alert.setContentText("Correct");
            alert.show();
            score +=10;
            if (highScore<100&&score>highScore)
                highScore =score;

            if (count == 9) {
                lblScore.setText(String.valueOf(score));
            }
            if (count < 9) {
                level++;
                count++;
                handleGamePicToWord();
            }
//            setVisible(true);
        } else if (!lblWord[listPicture.get(count).getWordCorrect().length() - 1].getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("No Correct Word");
            alert.show();
        }
    }
    private void handleLbl(Label lblWord) {
        if (lblWord.getText().equals(btnWord1.getText()) && !btnWord1.isVisible()) {
            btnWord1.setVisible(true);
        } else if (lblWord.getText().equals(btnWord2.getText()) && !btnWord2.isVisible()) {
            btnWord2.setVisible(true);
        } else if (lblWord.getText().equals(btnWord3.getText()) && !btnWord3.isVisible()) {
            btnWord3.setVisible(true);
        } else if (lblWord.getText().equals(btnWord4.getText()) && !btnWord4.isVisible()) {
            btnWord4.setVisible(true);
        } else if (lblWord.getText().equals(btnWord5.getText()) && !btnWord5.isVisible()) {
            btnWord5.setVisible(true);
        } else if (lblWord.getText().equals(btnWord6.getText()) && !btnWord6.isVisible()) {
            btnWord6.setVisible(true);
        } else if (lblWord.getText().equals(btnWord7.getText()) && !btnWord7.isVisible()) {
            btnWord7.setVisible(true);
        } else if (lblWord.getText().equals(btnWord8.getText()) && !btnWord8.isVisible()) {
            btnWord8.setVisible(true);
        } else if (lblWord.getText().equals(btnWord9.getText()) && !btnWord9.isVisible()) {
            btnWord9.setVisible(true);
        } else if (lblWord.getText().equals(btnWord10.getText()) && !btnWord10.isVisible()) {
            btnWord10.setVisible(true);
        }

        lblWord.setText("");
    }

    private void handleGamePicToWord() {
        lblLevel.setText("Level: " + level);
        int count1 = 60;
        timeline.stop();
        timeline = new Timeline();
        lblTime.setText("Time: 60s");

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    int countTime = count1;
                    @Override
                    public void handle(ActionEvent event) {
                        countTime--;
                        if (countTime >= 0) {
                            lblTime.setText("Time: " + countTime + "s");
                        } else if (countTime<0){
                            timeline.stop();
                            lblTime.setText("Time: 0s");

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Fail");
                            alert.setHeaderText(null);
                            alert.setContentText("You lose");
                            alert.show();
                            bdpGameSelect.setVisible(true);
                            acpPicToWord.setVisible(false);
                        }
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        lblHighScore.setText("HighScore: " + highScore);
        lblScore.setText("Score: " + score);
        PictureToWord pictureToWord = listPicture.get(count);
        String a = "file:/D:/AppEnglish/AppJavaEnglish/target/classes/com/example/appjavaenglish/"+pictureToWord.getImage();
        imgPictureGame.setImage(null);

        imgPictureGame.setImage(new Image(a));
        hbxLabelWord.getChildren().clear();
        for (int i = 0; i < pictureToWord.getWordCorrect().length(); i++) {
            lblWord[i] = new Label();
            int finalI = i;
            lblWord[i].setOnMouseClicked(mouseEvent -> {
                handleLbl(lblWord[finalI]);
            });
            hbxLabelWord.getChildren().add(lblWord[i]);
        }
        gridPaneBtn.getChildren().forEach(node ->{
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setVisible(true);
            }
        });
        btnWord1.setText(pictureToWord.getWord1());
        btnWord2.setText(pictureToWord.getWord2());
        btnWord3.setText(pictureToWord.getWord3());
        btnWord4.setText(pictureToWord.getWord4());
        btnWord5.setText(pictureToWord.getWord5());
        btnWord6.setText(pictureToWord.getWord6());
        btnWord7.setText(pictureToWord.getWord7());
        btnWord8.setText(pictureToWord.getWord8());
        btnWord9.setText(pictureToWord.getWord9());
        btnWord10.setText(pictureToWord.getWord10());

    }

    @FXML
    public void btnGameEvent(ActionEvent event) {

        acpGame.setVisible(true);
        contentView.setVisible(false);
        acpTranslate.setVisible(false);
        acpPicToWord.setVisible(false);
        bdpHome.setVisible(false);
        acpQuiz.setVisible(false);

    }

    @FXML
    public void quizEnglish(ActionEvent event) {

        quizGame();
        handleNextAndBack();
        acpQuiz.setVisible(true);
        acpGame.setVisible(false);
    }

    @FXML
    public void picToWord(ActionEvent event) {
        count = 0;
        level=1;
        score = 0;
        picToWordGame();
        handleGamePicToWord();

        acpGame.setVisible(false);
        acpPicToWord.setVisible(true);
    }

    @FXML
    public void nextEvent(ActionEvent event) {
        count++;
        level++;
        handleNextAndBack();
    }

    @FXML
    public void backEvent(ActionEvent event) {
        count--;
        level--;
        handleNextAndBack();
    }

    @FXML
    public void submitEvent(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Score");
        alert.setHeaderText(null);
        alert.setContentText("Chúc mừng bạn đạt được: " + scoreQuiz());
        alert.show();
        acpGame.setVisible(true);
        acpQuiz.setVisible(false);
    }

    public int scoreQuiz() {
        int score = 0;
        if (rbtnAnswer[0][1].isSelected()) {
            score++;
        }
        if (rbtnAnswer[1][0].isSelected()) {
            score++;
        }
        if (rbtnAnswer[2][3].isSelected()) {
            score++;
        }
        if (rbtnAnswer[3][3].isSelected()) {
            score++;
        }
        if (rbtnAnswer[4][2].isSelected()) {
            score++;
        }
        if (rbtnAnswer[5][3].isSelected()) {
            score++;
        }
        if (rbtnAnswer[6][3].isSelected()) {
            score++;
        }
        if (rbtnAnswer[7][2].isSelected()) {
            score++;
        }
        if (rbtnAnswer[8][0].isSelected()) {
            score++;
        }
        if (rbtnAnswer[9][3].isSelected()) {
            score++;
        }
        return score;
    }

    private void handleNextAndBack() {
        lblQues.setText("Question: "+level);
        Quiz quiz = quizList.get(count - 1);

        lblQuestion.setText(quiz.getId() + ": " + quiz.getQuestion());
        rbtnAnswer[count - 1][0].setText(quiz.getAnswer1());
        rbtnAnswer[count - 1][1].setText(quiz.getAnswer2());
        rbtnAnswer[count - 1][2].setText(quiz.getAnswer3());
        rbtnAnswer[count - 1][3].setText(quiz.getAnswer4());

        vbxAnswer.getChildren().clear();
        vbxAnswer.getChildren().addAll(rbtnAnswer[count - 1][0], rbtnAnswer[count - 1][1],
                rbtnAnswer[count - 1][2], rbtnAnswer[count - 1][3]);

        if (count > 1 && count < 10) {
            btnNext.setDisable(false);
            btnBack.setDisable(false);
        } else if (count == 1) {
            btnNext.setDisable(false);
            btnBack.setDisable(true);
        } else {
            btnNext.setDisable(true);
            btnBack.setDisable(false);
        }
    }


    @FXML
    public void list(MouseEvent event) {

        webEngine = webView.getEngine();

        Document document = Jsoup.parse(map.get(listView.getSelectionModel().getSelectedItem()));

        Elements h1 = document.select("h1");
        Elements h3 = document.select("h3");


        h1.remove();
        h3.remove();
        webEngine.loadContent(document.toString());

        txtWord.setText(listView.getSelectionModel().getSelectedItem() + "\n" + h3.text());
        btnSpeaker.setVisible(true);

    }

    @FXML
    public void btnDictionaryEvent(ActionEvent event) {
        timeline.stop();
        contentView.setVisible(true);
        acpTranslate.setVisible(false);
        acpQuiz.setVisible(false);
        acpGame.setVisible(false);
        acpPicToWord.setVisible(false);
        bdpHome.setVisible(false);
    }

    @FXML
    public void btnTranslateEvent(ActionEvent event) {
        timeline.stop();
        contentView.setVisible(false);
        acpTranslate.setVisible(true);
        acpQuiz.setVisible(false);
        acpGame.setVisible(false);
        acpPicToWord.setVisible(false);
        bdpHome.setVisible(false);
    }

    @FXML
    public void btnSpeakerEvent(ActionEvent event) throws Exception {
        String x = listView.getSelectionModel().getSelectedItem();
        if (english){
            speakWords(x, "en-gb");
        } else {
            speakWords(x, "vi-VN");
        }
    }

    @FXML
    public void btnDeleteEvent(ActionEvent event) {
        DictionaryService dictionaryService = new DictionaryService();
        dictionaryService.deleteDictionaryAv(listView.getSelectionModel().getSelectedItem());

        String selectedItem = listView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            observableList.remove(selectedItem);
        }

        listView.getItems().clear();
        listView.refresh();

        for (String item : observableList) {

            listView.getItems().add(item);
        }
        listView.getSelectionModel().clearSelection();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("DELETE");
        alert.setHeaderText(null);
        alert.setContentText("OK");
        alert.show();

    }

    @FXML
    public void btnAddEvent(ActionEvent event) {
        DictionaryService dictionaryService = new DictionaryService();
        Dictionary dictionary = new Dictionary();

        Document document = Jsoup.parse(htmlEdit.getHtmlText());
        Elements h1 = document.select("h1");
        Elements h3 = document.select("h3");


        dictionary.setWord(h1.text());
        dictionary.setHtml(htmlEdit.getHtmlText());

        dictionary.setPronounce(h3.text());
        map.put(h1.text(), htmlEdit.getHtmlText());
        observableList.add(h1.text());

        listView.getItems().clear();

        for (String item : observableList) {
            listView.getItems().add(item);
        }

        dictionaryService.addDictionaryAv(dictionary);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Dictionary");
        alert.setHeaderText(null);
        alert.setContentText("word");
        alert.show();
        acpHtmlEdit.setVisible(false);
    }

    @FXML
    public void btnAddUI(ActionEvent event) {
//        acpAdd.setVisible(true);
        acpHtmlEdit.setVisible(true);
        htmlEdit.setHtmlText("<html> <h1>Nhập từ:</h1><b><h3>Phát âm:</h3></b>"
                + "<ul><li><b><i> loại từ: </i></b><ul><li><font color='#cc0000'><b> Nghĩa thứ nhất: </b></font><ul></li></ul></ul></li></ul><ul><li><b><i>loại từ khác: </i></b><ul><li><font color='#cc0000'><b> Nghĩa thứ hai: </b></font></li></ul></li></ul></html>");
    }

    @FXML
    public void btnEditUI(ActionEvent event) {
        htmlEdit.setHtmlText(map.get(listView.getSelectionModel().getSelectedItem()));
        acpHtmlEdit.setVisible(true);
    }

    @FXML
    public void btnEditEvent(ActionEvent event) {
        DictionaryService dictionaryService = new DictionaryService();

        Document document = Jsoup.parse(htmlEdit.getHtmlText());
        Elements h1 = document.select("h1");
        Elements h3 = document.select("h3");

        Dictionary dictionary = new Dictionary();

        dictionary.setWord(h1.text());
        dictionary.setHtml(htmlEdit.getHtmlText());
//        dictionary.setDescription(txtDescription.getText());
        dictionary.setPronounce(h3.text());
        map.replace(h1.text(), htmlEdit.getHtmlText());
        dictionaryService.updateDictionaryAv(dictionary);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Dictionary");
        alert.setHeaderText(null);
        alert.setContentText("word");
        alert.show();
        acpHtmlEdit.setVisible(false);
    }
    @FXML
    public void translate(ActionEvent event) {
        ApiTextTranSlate apiTextTranslate = new ApiTextTranSlate();
        try {
            if(btnA1.getStyleClass().contains("btnNone")){
                String text = apiTextTranslate.googleTranslate("","en",txtAreaVi.getText());
                txtAreaEn.setText(text);
            } else if (btnA2.getStyleClass().contains("btnNone")) {
                String text = apiTextTranslate.googleTranslate("","vi",txtAreaVi.getText());
                txtAreaEn.setText(text);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void btnAvEvent(ActionEvent event) {
        if (!btnA1.getStyleClass().contains("btnAv")){
            btnA1.getStyleClass().add("btnAv");
            btnV2.getStyleClass().add("btnAv");
        }
        btnA1.getStyleClass().remove("btnNone");
        btnV2.getStyleClass().remove("btnNone");
        if (!btnA2.getStyleClass().contains("btnNone")){
            btnA2.getStyleClass().add("btnNone");
            btnV1.getStyleClass().add("btnNone");
        }

    }

    @FXML
    public void btnVaEvent(ActionEvent event) {
        if (!btnA2.getStyleClass().contains("btnAv")) {
            btnA2.getStyleClass().add("btnAv");
            btnV1.getStyleClass().add("btnAv");
        }
        btnA2.getStyleClass().remove("btnNone");
        btnV1.getStyleClass().remove("btnNone");
        if (!btnA1.getStyleClass().contains("btnNone")) {
            btnA1.getStyleClass().add("btnNone");
            btnV2.getStyleClass().add("btnNone");
        }
    }

    @FXML
    public void btnXEvent(ActionEvent event){
        acpHtmlEdit.setVisible(false);
    }

    @FXML
    public void btnSpeaker1(ActionEvent event) throws Exception {
        String word = txtAreaVi.getText();
        if (!btnA1.getStyleClass().contains("btnNone")) {
            speakWords(word, "en-gb");
        } else {
            speakWords(word, "vi-VN");
        }
    }

    @FXML
    public void btnSpeaker2(ActionEvent event) throws Exception {
        String word = txtAreaEn.getText();
        if (!btnV2.getStyleClass().contains("btnNone")) {
            speakWords(word, "vi-VN");
        } else {
            speakWords(word, "en-gb");
        }
    }

    @FXML
    public void handleEngToVi(MouseEvent event) {
        if (english){
            String a = "file:/D:/AppEnglish/AppJavaEnglish/target/classes/com/example/appjavaenglish/image/eng-viet.png";
            imgEngVi.setImage(new Image(a));
            english = false;
            btnDictionary.setText("Từ Điển");
            btnGame.setText("Trò Chơi");
            btnTranslates.setText("Dịch");
            btnAdd.setText("Thêm");
            btnDelete.setText("Xóa từ");
            btnEdit.setText("Sửa từ");
            txtSearch.setPromptText("Tìm kiếm");
            showAllWord();
        } else {
            english = true;
            String a = "file:/D:/AppEnglish/AppJavaEnglish/target/classes/com/example/appjavaenglish/image/viet-eng.png";
            imgEngVi.setImage(new Image(a));
            btnDictionary.setText("Dictionary");
            btnGame.setText("Game");
            btnTranslates.setText("Translate");
            btnAdd.setText("Add");
            btnDelete.setText("Delete");
            btnEdit.setText("Edit");
            txtSearch.setPromptText("Search");
            showAllWord();
        }


    }

    @FXML
    public void btnExitEvent(ActionEvent event) {
        Platform.exit();
    }
}
