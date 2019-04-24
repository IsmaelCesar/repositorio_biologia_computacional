package br.ufrpe.biocomp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        HBox root = new HBox();
//        Scene scene = new Scene(root, 300, 150);
//        stage.setScene(scene);
//        stage.setTitle("");
//
//        ToggleGroup group = new ToggleGroup();
//        RadioButton button1 = new RadioButton("select first");
//        button1.setToggleGroup(group);
//        button1.setSelected(true);
//        RadioButton button2 = new RadioButton("select second");
//        button2.setToggleGroup(group);
//
//        root.getChildren().add(button1);
//        root.getChildren().add(button2);
//
//        scene.setRoot(root);
//        stage.show();
        Pane pane = FXMLLoader.load(getClass().getResource("seqalignscene.fxml"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}