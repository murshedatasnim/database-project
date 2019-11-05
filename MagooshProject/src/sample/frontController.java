package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class frontController {
    @FXML
    private Main main;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField std;
    @FXML
    private Button button;
    public String email;
    public String pass;
    public String s;
    public int v;

    @FXML
    private Button reviewButton;


    @FXML
    public void goClicked(ActionEvent event) throws Exception{
        email=textField1.getText();
        pass=textField2.getText();
        try {
            main.showNStudents(email,pass);
        }catch (NullPointerException e){

        }

    }

     public void reviewClicked(ActionEvent actionEvent)throws Exception
    {
        try {
            main.review();
        }catch (Exception e){

        }


    }
    @FXML
    private Button dashboard;
    public void dashboardClicked(ActionEvent actionEvent)throws Exception{
        main.Dashboard();
    }

    @FXML
    private Button answer;
    public void answerClicked(ActionEvent actionEvent)throws Exception{
        main.ans();
    }

    @FXML
    private Button tutoringId;
    public void tutoringClicked(ActionEvent actionEvent)throws Exception{
        main.tutoring();
    }

    @FXML
    private Button Lessons;
    public void lessonsClicked(ActionEvent actionEvent)throws Exception{
        main.lessons();
    }
    @FXML
    private Button notesId;
    public void notesClicked(ActionEvent actionEvent)throws Exception{
        main.qNotes();
    }

    @FXML
    private Button lessonnote;
    public void notes2Clicked(ActionEvent actionEvent)throws Exception{
        main.lnotes();
    }

    @FXML
    private Button nst;
    public void nstClicked(ActionEvent actionEvent)throws Exception{
        main.nst();
    }

    @FXML
    private Button hma;
    public void hmaClicked(ActionEvent actionEvent)throws Exception{
        main.hmaClicked();
    }

    @FXML
    private Button correctAns;
    public void corrAnsClicked(ActionEvent actionEvent)throws Exception{
        main.corrAnsClicked();
    }

    @FXML
    private Button tutor;
    public void tutorClicked(ActionEvent actionEvent)throws Exception{
        main.tutor();
    }

    @FXML
    private Button student;
    public void studentClicked(ActionEvent actionEvent)throws Exception{
        main.student();
    }

    @FXML
    private Button gres;
    public void gresClicked(ActionEvent actionEvent)throws Exception{
        main.gres();
    }

    @FXML
    private Button ems;
    public void emsClicked(ActionEvent actionEvent)throws Exception{
        main.ems();
    }

    @FXML
    private Button evs;
    public void evsClicked(ActionEvent actionEvent)throws Exception{
        main.evs();
    }

    @FXML
    private Button vr;
    public void vrClicked(ActionEvent actionEvent)throws Exception{
        main.vr();
    }

    @FXML
    private Button avs;
    public void avsClicked(ActionEvent actionEvent)throws Exception{
        main.avs();
    }

    @FXML
    private Button avt;
    public void avtClicked(ActionEvent actionEvent)throws Exception{
        main.avt();
    }


    @FXML
    private Button exp;
    public void expClicked(ActionEvent actionEvent)throws Exception{
        main.exp();
    }

    @FXML
    private Button rating;
    public void ratingClicked(ActionEvent actionEvent)throws Exception{
        main.rat(8);
    }

    @FXML
    private Button delst;
    public void delstClicked(ActionEvent actionEvent)throws Exception{
        s = std.getText();
        v=Integer.parseInt(s);
        main.delst(v);
    }

    void setMain(Main main) {
        this.main = main;
    }



}
