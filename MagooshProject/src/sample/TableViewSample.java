package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TableViewSample extends Application {

    private TableView<Answer> table = new TableView<Answer>();
    private final ObservableList<Answer> data =
            FXCollections.observableArrayList(
                    new Answer("Jacob", "Smith", "jacob.smith@example.com","a","s","e"),
                    new Answer("Isabella", "Johnson", "isabella.johnson@example.com","w","r","w")
                   // new Answer("Ethan", "Williams", "ethan.williams@example.com"),
                    //new Answer("Emma", "Jones", "emma.jones@example.com"),
                    //new Answer("Michael", "Brown", "michael.brown@example.com")
            );

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Answer table");
        stage.setWidth(750);
        stage.setHeight(500);

        final Label label = new Label("Answer");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("Result");
        ansCol.setMinWidth(100);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<Answer, String>("Result"));

        TableColumn qttlcol = new TableColumn("Q title");
        qttlcol.setMinWidth(100);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<Answer, String>("Qtitle"));

        TableColumn seccol = new TableColumn("Section");
        seccol.setMinWidth(100);
        seccol.setCellValueFactory(
                new PropertyValueFactory<Answer, String>("Section"));

        TableColumn subcol = new TableColumn("Subject");
        subcol.setMinWidth(100);
        subcol.setCellValueFactory(
                new PropertyValueFactory<Answer, String>("sub"));

        TableColumn diffcol = new TableColumn("Difficulty");
        diffcol.setMinWidth(100);
        diffcol.setCellValueFactory(
                new PropertyValueFactory<Answer, String>("difficulty"));

        TableColumn pacecol = new TableColumn("Pace");
        pacecol.setMinWidth(100);
        pacecol.setCellValueFactory(
                new PropertyValueFactory<Answer, String>("pace"));



        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol,seccol,subcol,diffcol,pacecol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static class Answer {

        public final SimpleStringProperty Result;
        public final SimpleStringProperty Qtitle;
        public final SimpleStringProperty Section;
        public final SimpleStringProperty sub;
        public final SimpleStringProperty difficulty;
        public final SimpleStringProperty pace;
       // public final SimpleStringProperty otherspace;

        public Answer(SimpleStringProperty result, SimpleStringProperty qtitle, SimpleStringProperty section, SimpleStringProperty sub, SimpleStringProperty difficulty, SimpleStringProperty pace) {
            Result = result;
            Qtitle = qtitle;
            Section = section;
            this.sub = sub;
            this.difficulty = difficulty;
            this.pace = pace;
            //this.otherspace = otherspace;
        }

        public Answer(String Result, String Qtitle, String Section,String sub,String difficulty,String pace) {
            this.Result = new SimpleStringProperty(Result);
            this.Qtitle = new SimpleStringProperty(Qtitle);
            this.Section = new SimpleStringProperty(Section);
            this.sub = new SimpleStringProperty(sub);
            this.difficulty = new SimpleStringProperty(difficulty);
            this.pace = new SimpleStringProperty(pace);
            //this.otherspace = new SimpleStringProperty(otherspace);
        }

        public String getResult() {
            return Result.get();
        }

        public void setResult(String Qtitle) {
            Result.set(Qtitle);
        }

        public String getQtitle() {
            return Qtitle.get();
        }

        public void setQtitle(String fName) {
            Qtitle.set(fName);
        }

        public String getSection() {
            return Section.get();
        }

        public void setSection(String fName) {
            Section.set(fName);
        }

        public String getSub() {
            return sub.get();
        }

        public void setSub(String fName) {
            Section.set(fName);
        }

        public String getDifficulty() {
            return difficulty.get();
        }

        public void setDifficulty(String fName) {
            difficulty.set(fName);
        }

        public String getPace() {
            return pace.get();
        }

        public void setPace(String fName) {
            pace.set(fName);
        }




    }

    public static class lessonss {

        public final SimpleStringProperty title;
        public final SimpleStringProperty subject;


        public lessonss(SimpleStringProperty title_, SimpleStringProperty sub) {
            title = title_;
            subject = sub;

        }

        public lessonss(String t, String sub) {
            this.title = new SimpleStringProperty(t);
            this.subject = new SimpleStringProperty(sub);

        }

        public String getTitle() {
            return title.get();
        }

        public void setTitle(String t) {
            title.set(t);
        }

        public String getSubject() {
            return subject.get();
        }

        public void setSubject(String s) {
            subject.set(s);
        }
    }
}
