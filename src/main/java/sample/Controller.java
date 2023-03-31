package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.nio.file.Files;

public class Controller {
    @FXML
    TextArea texts;
    @FXML
    TextField poisk;
    @FXML
    ComboBox <String> vText;
    @FXML
    Button npoisk;
    @FXML
    Button cosoyi;
    @FXML
    Button girn;
    @FXML
    ComboBox<String> save;
    @FXML
    Button podcherk;
    @FXML
    Button update_srif;
    @FXML
    TextField srif;

    @FXML
    void initialize() {
        ObservableList<String> langs = FXCollections.observableArrayList("TXT", "docx", "pdf");
        vText.getItems().add("TXT");
        vText.getItems().add("docx");
        vText.getItems().add("pdf");
        vText.setValue("TXT");
        vText.setOnAction(e -> {
            if (vText.getValue().equals("docx")) {
                FileChooser fileChooser = new FileChooser();
                // Открываем диалог выбора файла
                Stage stage = new Stage();
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    try {
                        FileInputStream stream = new FileInputStream(new File(String.valueOf(file)));
                        XWPFDocument document = new XWPFDocument(stream);
                        StringBuilder content = new StringBuilder();
                        for (XWPFParagraph paragraph : document.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                content.append(run.getText(0));
                            }
                            content.append("\n");
                        }
                        stream.close();
                        texts.setText(content.toString());
                        System.out.println(content.toString());

                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
            if (vText.getValue().equals("TXT")) {
                FileChooser fileChooser = new FileChooser();

                // Открываем диалог выбора файла
                Stage stage = new Stage();
                File file = fileChooser.showOpenDialog(stage);

                if (file != null) {
                    try {
                        // Читаем содержимое файла в строку
                        String content = new String(Files.readAllBytes(file.toPath()));

                        // Устанавливаем содержимое строки в TextArea
                        texts.setText(content);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            if (vText.getValue().equals("pdf")) {
                FileChooser fileChooser = new FileChooser();
                // Открываем диалог выбора файла
                Stage stage = new Stage();
                File file = fileChooser.showOpenDialog(stage);
                fileChooser.setTitle("Выберите файл PDF");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilter);

                if (file != null) {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        texts.setText(content);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        save.getItems().add("TXT");
        save.getItems().add("docx");
        save.getItems().add("pdf");
        save.setValue("TXT");
        save.setOnAction(e -> {
            if (save.getValue().equals("docx")) {
                FileChooser fileChooser = new FileChooser();
                // Открываем диалог выбора файла
                Stage stage = new Stage();
                File file = fileChooser.showSaveDialog(stage);
                // Открываем диалог сохранения файла
                String s=texts.getText();
                if (file != null) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(texts.getText());

                        // Создаем документ Word
                        // Создаем документ Word
                        XWPFDocument document = new XWPFDocument();
                        XWPFParagraph paragraph = document.createParagraph();
                        XWPFRun run = paragraph.createRun();
                        Font font = Font.font("Courier New", FontPosture.ITALIC, Double.parseDouble(srif.getText()));
                        texts.setFont(font);
                        run.setText(texts.getText());

                        // Сохраняем документ в файл
                        FileOutputStream out = new FileOutputStream(file);
                        document.write(out);
                        out.close();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            if (save.getValue().equals("TXT")) {
                FileChooser fileChooser = new FileChooser();

                // Открываем диалог выбора файла
                Stage stage = new Stage();
                fileChooser.setTitle("Save File");

                // Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                // Show save file dialog
                File file = fileChooser.showSaveDialog(new Stage());

                if (file != null) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(texts.getText());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
            }
            if (save.getValue().equals("pdf")) {
                FileChooser fileChooser = new FileChooser();
                // Открываем диалог выбора файла
                Stage stage = new Stage();
                File file = fileChooser.showSaveDialog(stage);
                fileChooser.setTitle("Выберите файл PDF");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilter);

                if (file != null) {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        texts.getText();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        update_srif.setOnAction(event -> {
            texts.setFont(Font.font("Arial", FontWeight.NORMAL, Double.parseDouble(srif.getText())));
        });
        npoisk.setOnAction(event -> {
            String searchText = poisk.getText();

            if (!searchText.isEmpty()) {
                texts.selectRange(0, 0);
                int index = texts.getText().indexOf(searchText);

                while (index != -1) {
                    texts.selectRange(index, index + searchText.length());
                    index = texts.getText().indexOf(searchText, index + searchText.length());
                }
            }
        });
        cosoyi.setOnAction(event -> {
            Font font = Font.font("Courier New", FontPosture.ITALIC, 16);
            texts.setFont(font);
        });
        girn.setOnAction(event -> {
            Font font = Font.font("Courier New", FontWeight.BOLD, 16);
            texts.setFont(font);
        });
//        save.setOnAction(event -> {

//

//
    }
    private void saveTextToFile(String content, File file) {
        try {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(texts.getText());

            // Сохраняем документ в файл
            FileOutputStream out = new FileOutputStream(file);
            document.write(out);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
         }
    }
}
