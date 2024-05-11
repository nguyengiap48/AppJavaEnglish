package com.example.appjavaenglish;

import com.example.appjavaenglish.Dictionary;
import com.example.appjavaenglish.DictionaryService;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private WebEngine webEngine;
    @FXML
    private Button btnSpeaker;
    @FXML
    private Button btnDictionary,btnGame,btnTranslates,btnExit;
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
    private GridPane gridPaneBtn;
    @FXML
    private TextField txtSearch;
    @FXML
    private VBox vbxAnswer;
    @FXML
    private HBox hbxLabelWord;
    @FXML
    private AnchorPane acpAdd;
    @FXML
    private Button btnX;
    @FXML
    private BorderPane bdpGameSelect;
    @FXML
    private RadioButton answerA, answerB, answerC, answerD;
    @FXML
    private Label lblQuestion,lblScore;
    @FXML
    private TextField txtId, txtWords, txtHtml, txtDescription, txtPronounce;
    @FXML
    private Button btnNext, btnBack, btnSubmit;
    @FXML
    private ImageView imgPictureGame;
    @FXML
    private Button btnWord1, btnWord2, btnWord3, btnWord4, btnWord5, btnWord6, btnWord7, btnWord8, btnWord9, btnWord10;
    @FXML
    private TextArea txtAreaVi, txtAreaEn;
    private Label lblWord[] = new Label[10];
    private ArrayList<Dictionary> list = new ArrayList<>();
    private HashMap<String, String> map = new LinkedHashMap<>();
    private ToggleGroup[] toggleGroup = new ToggleGroup[10];
    private RadioButton[][] rbtnAnswer = new RadioButton[10][4];
    private ArrayList<Quiz> quizList = new QuizService().getAllQuiz();
    private ArrayList<PictureToWord> listPicture = new PicToWordService().getAllPic();
    int count = 1;
    int score =0;

    public void initialize() {
        showAllWord();
        btnSpeaker.setVisible(false);
        acpContent.setTopAnchor(contentView, 0.0);
        acpContent.setBottomAnchor(contentView, 0.0);
        acpContent.setLeftAnchor(contentView, 0.0);
        acpContent.setRightAnchor(contentView, 0.0);

        DictionaryService dictionaryService = new DictionaryService();
        ArrayList<String> listWord = new ArrayList<>();
        for (Dictionary dictionary : dictionaryService.getAllWordAv()) {
            listWord.add(dictionary.getWord());
        }
        observableList = FXCollections.observableArrayList(listWord);

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

        DictionaryService dictionaryService = new DictionaryService();
        list = dictionaryService.getAllWordAv();
        ArrayList<String> listWord = new ArrayList<>();
        for (Dictionary dictionary : list) {
            listWord.add(dictionary.getWord());
            map.put(dictionary.getWord(), dictionary.getHtml());
        }
        observableList = FXCollections.observableArrayList(listWord);
        listView.setItems(observableList);


//        map.put(list.get(index).getWord(), list.get(index).getHtml());
    }

    public void speakWord(String word) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
//        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_time_awb.AlanVoiceDirectory");
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16");
//        Voice voice = voiceManager.getVoice("alan");

        if (voice != null) {
            voice.allocate();
            voice.speak(word);
        } else {
            System.err.println("Cannot find the specified voice.");
        }
    }

    public void quizGame() {
        for (int i = 0; i < 10; i++) {
            toggleGroup[i] = new ToggleGroup();
            for (int j = 0; j < 4; j++) {
                rbtnAnswer[i][j] = new RadioButton();
                rbtnAnswer[i][j].setToggleGroup(toggleGroup[i]);
            }
        }
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
                    button.setVisible(false);
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
            if (count == 9) {
                lblScore.setText(String.valueOf(score));
            }
            if (count < 9) {
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
        lblScore.setText(String.valueOf(score));
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
//        btnGame.setDisable(true);
        bdpGameSelect.setVisible(true);
        contentView.setVisible(false);
        acpTranslate.setVisible(false);
        acpPicToWord.setVisible(false);
        bdpHome.setVisible(false);
//        quizGame();
//        handleNextAndBack();
    }

    @FXML
    public void quizEnglish(ActionEvent event) {
        quizGame();
        handleNextAndBack();
        acpQuiz.setVisible(true);
        bdpGameSelect.setVisible(false);
    }

    @FXML
    public void picToWord(ActionEvent event) {
        count = 0;
        score = 0;
        picToWordGame();
        handleGamePicToWord();
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("DELETE");
//        alert.setHeaderText(null);
//        alert.setContentText(imgPictureGame.getImage().getUrl());
//        alert.show();

        bdpGameSelect.setVisible(false);
        acpPicToWord.setVisible(true);
    }

    @FXML
    public void nextEvent(ActionEvent event) {
        count++;
        handleNextAndBack();
    }

    @FXML
    public void backEvent(ActionEvent event) {
        count--;
        handleNextAndBack();
    }

    @FXML
    public void submitEvent(ActionEvent event) {
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Score");
        alert.setHeaderText(null);
        alert.setContentText("Chúc mừng bạn đạt được: " + score);
        alert.show();
        bdpGameSelect.setVisible(true);
        acpQuiz.setVisible(false);
    }

    private void handleNextAndBack() {
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
        contentView.setVisible(true);
        acpTranslate.setVisible(false);
        acpQuiz.setVisible(false);
        bdpGameSelect.setVisible(false);
        acpPicToWord.setVisible(false);
        bdpHome.setVisible(false);
    }

    @FXML
    public void btnTranslateEvent(ActionEvent event) {
        contentView.setVisible(false);
        acpTranslate.setVisible(true);
        acpQuiz.setVisible(false);
        bdpGameSelect.setVisible(false);
        acpPicToWord.setVisible(false);
        bdpHome.setVisible(false);
    }

    @FXML
    public void btnSpeakerEvent(ActionEvent event) {
        int x = listView.getSelectionModel().getSelectedIndex();
        speakWord(list.get(x).getWord());
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
        dictionary.setId(Integer.parseInt(txtId.getText()));
        dictionary.setWord(txtWords.getText());
        dictionary.setHtml(txtHtml.getText());
        dictionary.setDescription(txtDescription.getText());
        dictionary.setPronounce(txtPronounce.getText());
        map.put(txtWords.getText(), txtHtml.getText());
        observableList.add(txtWords.getText());

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
        acpAdd.setVisible(false);
    }

    @FXML
    public void btnAddUI(ActionEvent event) {
        acpAdd.setVisible(true);
    }

    @FXML
    public void btnXEvent(ActionEvent event) {
        acpAdd.setVisible(false);
        contentView.setVisible(true);
    }

    @FXML
    public void btnEditUI(ActionEvent event) {
        DictionaryService dictionaryService = new DictionaryService();
        Dictionary dictionary = dictionaryService.searchByWordAv(listView.getSelectionModel().getSelectedItem());
        txtId.setText(String.valueOf(dictionary.getId()));
        txtWords.setText(dictionary.getWord());
        txtHtml.setText(dictionary.getHtml());
        txtDescription.setText(dictionary.getDescription());
        txtPronounce.setText(dictionary.getPronounce());
        acpAdd.setVisible(true);
    }

    @FXML
    public void btnEditEvent(ActionEvent event) {
        DictionaryService dictionaryService = new DictionaryService();
        Dictionary dictionary = new Dictionary();
        dictionary.setId(Integer.parseInt(txtId.getText()));
        dictionary.setWord(txtWords.getText());
        dictionary.setHtml(txtHtml.getText());
        dictionary.setDescription(txtDescription.getText());
        dictionary.setPronounce(txtPronounce.getText());
        map.replace(txtWords.getText(), txtHtml.getText());
        dictionaryService.updateDictionaryAv(dictionary);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Dictionary");
        alert.setHeaderText(null);
        alert.setContentText("word");
        alert.show();
        acpAdd.setVisible(false);
    }
    @FXML
    public void translate(ActionEvent event) {
        ApiTextTranSlate apiTextTranslate = new ApiTextTranSlate();
        try {
            String text = apiTextTranslate.translate(txtAreaVi.getText());
            txtAreaEn.setText(text);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    @FXML
    public void btnExitEvent(ActionEvent event) {

    }
}
