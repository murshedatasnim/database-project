package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.proj.MagooshDbImp;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static int id=2;
    public static List list;

    MagooshDbImp magooshDbImp=new MagooshDbImp();


    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("welcome to magoosh");


        magooshDbImp.connect();
        //showNStudents("karim@gmail.com","2345");
        showStartPage();


    }

    public void showStartPage() throws Exception {
        // XML Loading using FXMLLoader
        Stage stage=new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("front.fxml"));
        Parent root = loader.load();

        // Loading the controller
        frontController controller = loader.getController();

        controller.setMain(this);

        // Set the primary stage
        stage.setTitle("MagooshGRE");
        stage.setScene(new Scene(root, 1200, 650));
        stage.show();
    }


    public void showNStudents(String s1,String s2)throws Exception{
        Stage stage=new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 200);
        //scene.setFill(new ImagePattern(bg, 0, 0, 1, 1, true));

        Label label=new Label();
        label.setTextFill(Color.BLACK);
        int a=magooshDbImp.LogIn(s1,s2);
        String s=new String();
        if(a==0){
            label.setText("Does not exist");
        }
        else {
            s=magooshDbImp.FindName(s1,s2);
            label.setText(" Welcome "+s);

        }
        label.setFont(Font.font(18));
        label.setLayoutX(100);
        label.setLayoutY(80);
        root.getChildren().add(label);

        stage.setTitle("Student's query");
        stage.setScene(scene);
        stage.show();

    }


    public void rat(int a)throws Exception{
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.rating(a);

        TableView<TableViewSample.lessonss> table = new TableView<TableViewSample.lessonss>();
        final ObservableList<TableViewSample.lessonss> data =FXCollections.observableList(list);

        //a = 1;
        Scene scene = new Scene(new Group());
        stage.setTitle("Vid Update");
        stage.setWidth(650);
        stage.setHeight(700);

        final Label qq = new Label("QUERY:UPDATE videos SET likes=0 where vid_id=?;\n"
                + "TRIGGER:\nsBEGIN  \n" +
                "\n" +
                " IF TG_OP='UPDATE' THEN\n" +
                "DELETE FROM public.\"Question Video\" where vid_id=NEW.vid_id AND like_per(NEW.vid_id) < 25;\n" +
                "DELETE FROM lessons where vid_id=NEW.vid_id AND like_per(NEW.vid_id) < 25;\n" +
                "DELETE FROM videos where vid_id=NEW.vid_id AND like_per(NEW.vid_id) < 25;\n" +
                "\n" +
                "RETURN NEW;\n" +
                "\n" +
                "  END IF;\n" +
                "END;\n\n");
        qq.setFont(new Font("Arial", 11));

        final Label label = new Label("Updated Video ID list");
        label.setFont(new Font("Arial", 18));
        table.setEditable(true);

        TableColumn ansCol = new TableColumn("Vid ID");
        ansCol.setMinWidth(300);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.lessonss, String>("title"));

        TableColumn qttlcol = new TableColumn("Subject");
        qttlcol.setMinWidth(300);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.lessonss, String>("subject"));


        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }

    public void review()throws Exception{
        Stage stage=new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 200);
        //scene.setFill(new ImagePattern(bg, 0, 0, 1, 1, true));

        Label label=new Label();
        label.setTextFill(Color.BLACK);
        int a=magooshDbImp.correctPercentage();
        String b=magooshDbImp.AvgPace();
        String c=magooshDbImp.othersAvgPace();
        label.setText("Percentage of correct answer : "+a+"%\n"+"Average pace : "+b+"\n"+"Others' Average pace : "+c);
        label.setFont(Font.font(18));
        label.setLayoutX(50);
        label.setLayoutY(50);
        root.getChildren().add(label);

        stage.setTitle("Student's review");
        stage.setScene(scene);
        stage.show();

    }
    public void delst(int x){
        Stage stage=new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 600);

        Label label=new Label();
        label.setTextFill(Color.BLACK);
        int a=magooshDbImp.n_tutoring();
        magooshDbImp.del_std(a);
        int b=magooshDbImp.n_tutoring(); //run after trigger

        label.setText("QUERY:\nDELETE FROM public.student WHERE \"ID\"=a;" +
                "TRIGGER:\nCREATE TRIGGER gsg\n" +
                "    BEFORE DELETE\n" +
                "    ON public.student\n" +
                "    FOR EACH ROW\n" +
                "    EXECUTE PROCEDURE public.delstd();"+
        "BEGIN  \n" +
                "\n" +
                " IF TG_OP='DELETE' THEN\n" +
                "DELETE FROM tutoring where \"Student id\"=OLD.\"ID\";\n" +
                "\n" +
                "RETURN OLD;\n" +
                "\n" +
                "  END IF;\n" +
                "END;\n\n"+
        "\nRows in \"tutoring\" table before deleting student: " + a
        +"\nRows in \"tutoring\" table after deleting student: " + b);
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Trigger 1");
        stage.setScene(scene);
        stage.show();
    }


    public void Dashboard(){
        Stage stage=new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 200);
        //scene.setFill(new ImagePattern(bg, 0, 0, 1, 1, true));

        Label label=new Label();
        label.setTextFill(Color.BLACK);
        int a=magooshDbImp.Answered();
        String b=magooshDbImp.AvgPace();
        String c=magooshDbImp.othersAvgPace();
        int d=magooshDbImp.correctAnswered();
        int e=a-d;
        int f=magooshDbImp.mathExpected();
        int g=magooshDbImp.verbalExpected();

        label.setText("Question answered : "+a+"\nCorrect answers : "+d+ "\nIncorrect answers : "+e+
                "\nAverage pace : "+b+"\nOthers Average pace : "+c+"\n"+
                "Expected Math Score : "+f+"/170\nExpected Verbal Score : "+g+"/170");
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Student's Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public void ans()throws Exception{
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.answer();

        TableView<TableViewSample.Answer> table = new TableView<TableViewSample.Answer>();
        final ObservableList<TableViewSample.Answer> data =FXCollections.observableList(list);

        Scene scene = new Scene(new Group());
        stage.setTitle("Answer table");
        stage.setWidth(750);
        stage.setHeight(500);

        final Label qq = new Label("QUERY:\nselect * from public.answered as aa, public.questions as b \nwhere aa.std_id=std and aa.q_id=b.\"ID\"\n\n");
        qq.setFont(new Font("Arial", 18));

        final Label label = new Label("Answer History");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("Result");
        ansCol.setMinWidth(100);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Result"));

        TableColumn qttlcol = new TableColumn("Question Title");
        qttlcol.setMinWidth(200);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Qtitle"));

        TableColumn seccol = new TableColumn("Section");
        seccol.setMinWidth(100);
        seccol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Section"));

        TableColumn subcol = new TableColumn("Subject");
        subcol.setMinWidth(100);
        subcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("sub"));

        TableColumn diffcol = new TableColumn("Difficulty");
        diffcol.setMinWidth(100);
        diffcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("difficulty"));

        TableColumn pacecol = new TableColumn("Pace");
        pacecol.setMinWidth(100);
        pacecol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("pace"));



        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol,seccol,subcol,diffcol,pacecol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }

    public void tutoring()throws Exception{
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.tutoring();

        TableView<TableViewSample.Answer> table = new TableView<TableViewSample.Answer>();
        final ObservableList<TableViewSample.Answer> data =FXCollections.observableList(list);

        Scene scene = new Scene(new Group());
        stage.setTitle("Tutoring table");
        stage.setWidth(750);
        stage.setHeight(500);

        final Label qq = new Label("QUERY:\nselect * from public.tutoring as aa, public.tutor as b \nwhere aa.\"Student id\"=std and aa.\"Tutor id\"=b.\"ID\"\n\n");
        qq.setFont(new Font("Arial", 18));

        final Label label = new Label("Tutoring History");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("Subject");
        ansCol.setMinWidth(100);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Result"));

        TableColumn qttlcol = new TableColumn("Tutor Name");
        qttlcol.setMinWidth(200);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Qtitle"));

        TableColumn seccol = new TableColumn("Start Date");
        seccol.setMinWidth(100);
        seccol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Section"));

        TableColumn subcol = new TableColumn("End Date");
        subcol.setMinWidth(100);
        subcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("sub"));

        TableColumn diffcol = new TableColumn("Feedback");
        diffcol.setMinWidth(100);
        diffcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("difficulty"));

        TableColumn pacecol = new TableColumn("Tutor ID");
        pacecol.setMinWidth(100);
        pacecol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("pace"));



        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol,seccol,subcol,diffcol,pacecol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }

    public void lessons(){
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.lessonTitle();

        TableView<TableViewSample.lessonss> table = new TableView<TableViewSample.lessonss>();
        final ObservableList<TableViewSample.lessonss> data =FXCollections.observableList(list);

        Scene scene = new Scene(new Group());
        stage.setTitle("Lessons");
        stage.setWidth(650);
        stage.setHeight(500);

        final Label qq = new Label("QUERY:\nselect subject,title from public.answered as aa,public.lessons\n\n");
        qq.setFont(new Font("Arial", 18));

        final Label label = new Label("Lesson Videos");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("Lesson Title");
        ansCol.setMinWidth(300);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.lessonss, String>("title"));

        TableColumn qttlcol = new TableColumn("Subject");
        qttlcol.setMinWidth(300);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.lessonss, String>("subject"));


        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void qNotes(){
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.qnotes();

        TableView<TableViewSample.lessonss> table = new TableView<TableViewSample.lessonss>();
        final ObservableList<TableViewSample.lessonss> data =FXCollections.observableList(list);

        Scene scene = new Scene(new Group());
        stage.setTitle("Question Notes");
        stage.setWidth(750);
        stage.setHeight(500);

        final Label qq = new Label("QUERY:\nselect \"Question\", note from public.\"Question Notes\" as q, public.questions as b \nwhere std_id=std and q.q_id=b.\"ID\";\n\n");
        qq.setFont(new Font("Arial", 18));

        final Label label = new Label("Question Notes");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("Question");
        ansCol.setMinWidth(450);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.lessonss, String>("title"));

        TableColumn qttlcol = new TableColumn("Note");
        qttlcol.setMinWidth(200);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.lessonss, String>("subject"));


        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void lnotes(){
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.lnotes();

        TableView<TableViewSample.lessonss> table = new TableView<TableViewSample.lessonss>();
        final ObservableList<TableViewSample.lessonss> data =FXCollections.observableList(list);

        Scene scene = new Scene(new Group());
        stage.setTitle("Lesson Notes");
        stage.setWidth(450);
        stage.setHeight(500);

        final Label qq = new Label("QUERY:\nselect title,note from public.\"Lesson Notes\" as q, public.lessons as b \nwhere std_id=std and q.lesson_id=b.\"ID\";\n\n");
        qq.setFont(new Font("Arial", 18));

        final Label label = new Label("Lesson Notes");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("Lesson Title");
        ansCol.setMinWidth(200);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.lessonss, String>("title"));

        TableColumn qttlcol = new TableColumn("Note");
        qttlcol.setMinWidth(200);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.lessonss, String>("subject"));


        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void nst() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 200);
        //scene.setFill(new ImagePattern(bg, 0, 0, 1, 1, true));

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        int g = magooshDbImp.nstd();

        label.setText("QUERY:\nselect count(*) from public.student;\n\nNumber of Students : "+ g);
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Student Number");
        stage.setScene(scene);
        stage.show();
    }

    public void ems() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 200);
        //scene.setFill(new ImagePattern(bg, 0, 0, 1, 1, true));

        final Label qq = new Label("QUERY:\nselect avg(grescore_math) from grescores, students \nwhere math_expected>=(avg_score(std,1)-5) \nand math_expected<=(avg_score(std,1)+5);\n\n\n");
        qq.setFont(new Font("Arial", 18));

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        int g = magooshDbImp.mathExpected();

        label.setText("QUERY:\nselect avg(grescore_math) from grescores, students \nwhere math_expected>=(avg_score(std,1)-5) and \nmath_expected<=(avg_score(std,1)+5);\n\n\nExpected GRE Score In Maths : "+ g + "/170");
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Expected Math Sore");
        stage.setScene(scene);
        stage.show();
    }

    public void evs() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 200);
        //scene.setFill(new ImagePattern(bg, 0, 0, 1, 1, true));

        final Label qq = new Label("QUERY:\nselect avg(grescore_verbal) from grescores, students \nwhere verbal_expected>=(avg_score(std,2)-5) and verbal_expected<=(avg_score(std,2)+5);\n\n\n");
        qq.setFont(new Font("Arial", 18));

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        int g = magooshDbImp.verbalExpected();

        label.setText("QUERY:\nselect avg(grescore_verbal) from grescores, students \nwhere verbal_expected>=(avg_score(std,2)-5) \nand verbal_expected<=(avg_score(std,2)+5);\n\n\nExpected GRE Score In Verbal : "+ g + "/170");
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Expected Verbal Sore");
        stage.setScene(scene);
        stage.show();
    }

    public void vr() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500);

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        String g = magooshDbImp.vr();

        label.setText("QUERY:\n   DECLARE\n" +
                "     perc real;\n" +
                "\t n real;\n" +
                "\t summ real;\n" +
                "\t str character(10);\n" +
                "   BEGIN\n" +
                "      select count(*) into n from videos where vid_id=vid;\n" +
                "\t  select sum(likes) into summ from videos where vid_id=vid;\n" +
                "\t  select (summ*100/n) into perc;\n" +
                "\t  select (perc||'%') into str;\n" +
                "      RETURN str;\n" +
                "   END;\n\n\nVideo Rating : "+ g);
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Video Rating");
        stage.setScene(scene);
        stage.show();
    }



    public void avs() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 700);

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        int g = magooshDbImp.avs();

        label.setText("FUNCTION:\n   DECLARE\n" +
                "     tm real;\n" +
                "\t n real;\n" +
                "\t av real;\n" +
                "   BEGIN\n" +
                "     if (sub=0)\n" +
                "\t  then\n" +
                "\t  select howmanyans(std) into n;\n" +
                "\t  select sum(correct) into tm from answered where std_id=std;\n" +
                "\tend if;\n" +
                "\t if (sub=1)\n" +
                "\t  then\n" +
                "\t  select count(*) into n from answered where std_id=std and subj='m';\n" +
                "\t  select sum(correct) into tm from answered where std_id=std and subj='m';\n" +
                "\tend if;\n" +
                "\t   if (sub=2)\n" +
                "      then\n" +
                "\t  select count(*) into n from answered where std_id=std and subj='v';\n" +
                "\t  select sum(correct) into tm from answered where std_id=std and subj='v';\n" +
                "\tend if;\n" +
                "\t  \n" +
                "\t  select (tm*170/n) into av;\n" +
                "\t  RETURN av;\n" +
                "\t  \n" +
                "   END;\n\n\nAverage Score : "+ g);
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Average Score");
        stage.setScene(scene);
        stage.show();
    }

    public void avt() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 700);

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        String g = magooshDbImp.AvgPace();

        label.setText("FUNCTION:\n   DECLARE\n" +
                "     tm INTEGER;\n" +
                "\t n INTEGER;\n" +
                "\t mint integer;\n" +
                "\t sec integer;\n" +
                "\t str character(20);\n" +
                "   BEGIN\n" +
                "   if (sub=0)\n" +
                "\t  then\n" +
                "\t  select avg(time_sec) into tm from answered where std_id=std;\n" +
                "\tend if;\n" +
                "\t if (sub=1)\n" +
                "\t  then\n" +
                "\t  select avg(time_sec) into tm from answered where std_id=std and subj='m';\n" +
                "\tend if;\n" +
                "\t   if (sub=2)\n" +
                "      then\n" +
                "\t  select howmanyans(std) into n;\n" +
                "\t  select avg(time_sec) into tm from answered where std_id=std and subj='v';\n" +
                "\tend if;\n" +
                "\t  \n" +
                "      mint = tm/60;\n" +
                "\t  sec = tm%60;\n" +
                "\t  select (mint || ' minutes ' || sec || ' seconds') into str;\n" +
                "\t  RETURN str;\n" +
                "   END;" +
                "\t  RETURN av;\n" +
                "\t  \n" +
                "   END;\n\n\nAverage Time : "+ g);
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Average Time");
        stage.setScene(scene);
        stage.show();
    }

    public void exp() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 700);

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        int g = magooshDbImp.mathExpected();
        int h = magooshDbImp.verbalExpected();
        g = g+h;

        label.setText("FUNCTION:\nDECLARE\n" +
                "\texp_ integer;\n" +
                "\texp1 integer;\n" +
                "\texp2 integer;\n" +
                "\tn integer;\n" +
                "\town real;\n" +
                "\ty real;\n" +
                "BEGIN\n" +
                "\tselect avg_score(std,1) into own;\n" +
                "\tselect avg(grescore_math) from grescores where math_expected>=(own-5) \nand math_expected<=(own+5) into exp1;\n" +
                "\t\tselect avg_score(std,2) into own;\n" +
                "\n" +
                "\t\tselect avg(grescore_verbal) from grescores where verbal_expected>=(own-5) \nand verbal_expected<=(own+5) into exp2;\n" +
                "RETURN exp1+exp2;\n" +
                "END;\n" +
                "   END;\n\n\nExpected GRE Score : "+ g);
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Exp GRE Score");
        stage.setScene(scene);
        stage.show();
    }

    public void hmaClicked() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 200);
        //scene.setFill(new ImagePattern(bg, 0, 0, 1, 1, true));

        final Label qq = new Label("QUERY:\nselect count(*) into crr from public.answered where std_id=std;\n\n");
        qq.setFont(new Font("Arial", 18));

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        int g = magooshDbImp.Answered();

        label.setText("QUERY:\nselect count(*) into crr from public.answered where std_id=std;\n\nNumber of Answer : "+ g);
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Answer  Number");
        stage.setScene(scene);
        stage.show();
    }

    public void corrAnsClicked() {
        Stage stage = new Stage();

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 200);
        //scene.setFill(new ImagePattern(bg, 0, 0, 1, 1, true));

        final Label qq = new Label("QUERY:\nselect count(*) into crr from public.answered \nwhere std_id=std and correct=1;\n\n");
        qq.setFont(new Font("Arial", 18));

        Label label = new Label();
        label.setTextFill(Color.BLACK);
        int g = magooshDbImp.correctAnswered();

        label.setText("QUERY:\nselect count(*) into crr from public.answered \nwhere std_id=std and correct=1;\n\nNumber of Correct Answer : "+ g);
        label.setFont(Font.font(14));
        label.setLayoutX(30);
        label.setLayoutY(30);
        root.getChildren().add(label);

        stage.setTitle("Correct answer Number");
        stage.setScene(scene);
        stage.show();
    }


    public void tutor()throws Exception{
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.tutor();

        TableView<TableViewSample.Answer> table = new TableView<TableViewSample.Answer>();
        final ObservableList<TableViewSample.Answer> data =FXCollections.observableList(list);

        Scene scene = new Scene(new Group());
        stage.setTitle("Tutor table");
        stage.setWidth(750);
        stage.setHeight(500);

        final Label qq = new Label("QUERY:\nselect * from public.tutor;\n\n");
        qq.setFont(new Font("Arial", 18));

        final Label label = new Label("Tutor");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("ID");
        ansCol.setMinWidth(100);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Result"));

        TableColumn qttlcol = new TableColumn("Date of Birth");
        qttlcol.setMinWidth(200);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Qtitle"));

        TableColumn seccol = new TableColumn("First Name");
        seccol.setMinWidth(100);
        seccol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Section"));

        TableColumn subcol = new TableColumn("Last Name");
        subcol.setMinWidth(100);
        subcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("sub"));

        TableColumn diffcol = new TableColumn("Subject");
        diffcol.setMinWidth(100);
        diffcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("difficulty"));

        TableColumn pacecol = new TableColumn("Email");
        pacecol.setMinWidth(100);
        pacecol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("pace"));



        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol,seccol,subcol,diffcol,pacecol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }

    public void student()throws Exception{
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.student();

        TableView<TableViewSample.Answer> table = new TableView<TableViewSample.Answer>();
        final ObservableList<TableViewSample.Answer> data =FXCollections.observableList(list);

        //System.out.println(list.get(1));

        Scene scene = new Scene(new Group());
        stage.setTitle("Student table");
        stage.setWidth(750);
        stage.setHeight(500);

        final Label qq = new Label("QUERY:\nselect * from public.student;\n\n");
        qq.setFont(new Font("Arial", 18));

        final Label label = new Label("Student");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("ID");
        ansCol.setMinWidth(100);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Result"));

        TableColumn qttlcol = new TableColumn("Date of Birth");
        qttlcol.setMinWidth(200);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Qtitle"));

        TableColumn seccol = new TableColumn("First Name");
        seccol.setMinWidth(100);
        seccol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Section"));

        TableColumn subcol = new TableColumn("Last Name");
        subcol.setMinWidth(100);
        subcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("sub"));

        TableColumn diffcol = new TableColumn("Email");
        diffcol.setMinWidth(100);
        diffcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("difficulty"));

        TableColumn pacecol = new TableColumn("Password");
        pacecol.setMinWidth(100);
        pacecol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("pace"));



        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol,seccol,subcol,diffcol,pacecol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }

    public void gres()throws Exception{
        list=new ArrayList();
        Stage stage=new Stage();
        magooshDbImp.greScore();

        TableView<TableViewSample.Answer> table = new TableView<TableViewSample.Answer>();
        final ObservableList<TableViewSample.Answer> data =FXCollections.observableList(list);

        //System.out.println(list.get(1));

        Scene scene = new Scene(new Group());
        stage.setTitle("GRE score table");
        stage.setWidth(750);
        stage.setHeight(500);


        final Label qq = new Label("QUERY:\nselect * from public.grescores;\n\n");
        qq.setFont(new Font("Arial", 18));

        final Label label = new Label("GRE Score");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn ansCol = new TableColumn("ID");
        ansCol.setMinWidth(100);
        ansCol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Result"));

        TableColumn qttlcol = new TableColumn("Math_expected");
        qttlcol.setMinWidth(200);
        qttlcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Qtitle"));

        TableColumn seccol = new TableColumn("Verbal_expected");
        seccol.setMinWidth(100);
        seccol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("Section"));

        TableColumn subcol = new TableColumn("GREScore_math");
        subcol.setMinWidth(100);
        subcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("sub"));

        TableColumn diffcol = new TableColumn("GREScore_verbal");
        diffcol.setMinWidth(100);
        diffcol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("difficulty"));

        TableColumn pacecol = new TableColumn("Year");
        pacecol.setMinWidth(100);
        pacecol.setCellValueFactory(
                new PropertyValueFactory<TableViewSample.Answer, String>("pace"));



        table.setItems(data);
        table.getColumns().addAll(ansCol,qttlcol,seccol,subcol,diffcol,pacecol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(qq, label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {

        launch(args);


    }
}
