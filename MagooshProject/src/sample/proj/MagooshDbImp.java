package sample.proj;

import sample.Main;
import sample.TableViewSample;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MagooshDbImp extends DbAdapter {
   /* private TableView<TableViewSample.Answer> table = new TableView<TableViewSample.Answer>();
    private ObservableList<TableViewSample.Answer> data;
*/


    public void viewsubject() {
        String sub;
        String c;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM public.subjects");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sub = rs.getString(1);
                c = rs.getString(2);
                System.out.println(sub + " " + c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //System.out.println("number of rows "+nrows);
    }

    public int nstd() {
        int c=0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM public.student");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                c = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void del_std(int a) {
        int c=0;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM public.student WHERE \"ID\"=a;");
            ResultSet rs = ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int n_tutoring() {
        int c=0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM public.tutoring");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                c = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public int n_fb() {
        int c=0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM public.videos");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                c = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }


    public String Question(String subj)
    {
        String ques;
        String a,b,c,d;
        String ans = "";
        try {
            PreparedStatement ps=conn.prepareStatement("SELECT * FROM public.questions where subject=?");
            ps.setString(1,subj);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                ques=rs.getString(2);
                a=rs.getString(3);
                b=rs.getString(4);
                c=rs.getString(5);
                d=rs.getString(6);
                ans=rs.getString(7);
                System.out.println("Q. " +ques);
                System.out.println("A. " +a);
                System.out.println("B. "+b);
                System.out.println("C. "+c);
                System.out.println("D. "+d);
                break;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return ans;

        //System.out.println("number of rows "+nrows);
    }

    public int LogIn(String email,String pass){
       int i=0;
        String s=new String();
        try {
            CallableStatement st=conn.prepareCall("{call login(?, ?)}");
            st.setString(1,email);
            st.setString(2,pass);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                i=rs.getInt(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;


    }

    public void rating(int vid){
        String i=new String();
        String j=new String();
        int v=0;

        try {
            PreparedStatement ps=conn.prepareStatement("UPDATE videos SET likes=0 where vid_id=?;");
            ps.setInt(1,vid);
            ResultSet rs=ps.executeQuery();
            } catch (SQLException e1) {
            e1.printStackTrace();
        }

        try {
            PreparedStatement ps=conn.prepareStatement("select vid_id, title from public.\"Question Video\"");
            ResultSet rs=ps.executeQuery();

            while (rs.next())
            {
                v=rs.getInt(1);
                j=rs.getString(2);
                i = Integer.toString(v);
                Main.list.add(new TableViewSample.lessonss(i,j));
            }
            rs.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }



    public String FindName(String email,String pass){
        String s=new String();

        try {
            PreparedStatement ps=conn.prepareStatement("SELECT \"First_name\"||' '||\"Last_name\",\"ID\" from public.student where email=? AND password=?");
            ps.setString(1,email);
            ps.setString(2,pass);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                s=rs.getString(1);
                Main.id=rs.getInt(2);
            }
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return s;

    }

   public int correctPercentage(){
        int i=0;
        try {
            CallableStatement st=conn.prepareCall("{call correctpercent(?)}");
            st.setInt(1,Main.id);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                i=rs.getInt(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;


    }

    public String AvgPace(){
        String i=new String();
        try {
            CallableStatement st=conn.prepareCall("{call avgtime(?, ?)}");
            st.setInt(1,Main.id);
            st.setInt(2,0);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                i=rs.getString(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;


    }

    public int avs(){
        int i=0;
        try {
            CallableStatement st=conn.prepareCall("{call avg_score(?, ?)}");
            st.setInt(1,Main.id);
            st.setInt(2,0);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                i=rs.getInt(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;


    }


    public String othersAvgPace(){
        String s=new String();
        try {
            CallableStatement st=conn.prepareCall("{call others_avgtime()}");

            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                s=rs.getString(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return s;


    }

    public int Answered(){
        int i=0;
        try {
            CallableStatement st=conn.prepareCall("{call howmanyans(?)}");
            st.setInt(1,Main.id);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                i=rs.getInt(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    public String vr(){
        String s=new String();
        try {
            CallableStatement st=conn.prepareCall("{call likepercent(?)}");
            st.setInt(1,2);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                s=rs.getString(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return s;
    }



    public int correctAnswered(){
        int i=0;
        try {
            CallableStatement st=conn.prepareCall("{call howmanycorrect(?)}");
            st.setInt(1,Main.id);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                i=rs.getInt(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    public int mathExpected(){
        int i=0;
        try {
            CallableStatement st=conn.prepareCall("{call expectedscore_math(?)}");
            st.setInt(1,Main.id);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                i=rs.getInt(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;

    }

    public int verbalExpected(){
        int i=0;
        try {
            CallableStatement st=conn.prepareCall("{call expectedscore_verbal(?)}");
            st.setInt(1,Main.id);
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                i=rs.getInt(1);
                break;
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;

    }

    public void tutoring(){
        String sub, tname, sdate, edate, tid, fb, fn, ln;
        try {
            PreparedStatement ps=conn.prepareStatement("select * from public.tutoring as aa, public.tutor as b where aa.\"Student id\"=? and aa.\"Tutor id\"=b.\"ID\"");
            ps.setInt(1,Main.id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                sub=rs.getString(1);
                fn=rs.getString(10);
                ln=rs.getString(11);
                tname = fn+ln;
                sdate=rs.getString(2);
                edate=rs.getString(3);
                tid=rs.getString(6);
                fb=rs.getString(4);

               Main.list.add(new TableViewSample.Answer(sub,tname,sdate,edate,fb,tid));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void answer(){
    int i,j;
    String correct,qt,sec,sub,diff;
    String pace;
    String c;
        try {
        PreparedStatement ps=conn.prepareStatement("select * from public.answered as aa,public.questions as b where aa.std_id=? and aa.q_id=b.\"ID\"");
        ps.setInt(1,Main.id);
        ResultSet rs=ps.executeQuery();
        while (rs.next()){
            i=rs.getInt(4);
            if(i==1)
                correct="  Right";
            else
                correct="  Wrong";
            qt=rs.getString(8);
            c=rs.getString(5);
            if(c.equals("m"))
                sec=" Math";
            else
                sec=" Verbal";
            sub=" "+rs.getString(14);
            diff=" "+rs.getString(15);
            j=rs.getInt(6);
            pace=" "+Integer.toString(j)+"sec";

            Main.list.add(new TableViewSample.Answer(correct,qt,sec,sub,diff,pace));
        }

    }catch (SQLException e){
        e.printStackTrace();
    }

}

    public String lessonTitle(){
        String i=new String();
        String j = new String();
        try {
                PreparedStatement ps=conn.prepareStatement("select subject,title from public.answered as aa,public.lessons");
                ResultSet rs=ps.executeQuery();

                while (rs.next())
                {
                    i=rs.getString(2);
                    j=rs.getString(1);
                    Main.list.add(new TableViewSample.lessonss(i,j));
                }
                rs.close();

            }catch (SQLException e){
                e.printStackTrace();
        }
        return i;
    }

    public String qnotes(){
        String i=new String();
        String j = new String();
        try {
            PreparedStatement ps=conn.prepareStatement("select \"Question\",note from public.\"Question Notes\" as q, public.questions as b where std_id=? and q.q_id=b.\"ID\";");
            ps.setInt(1,Main.id);
            ResultSet rs=ps.executeQuery();

            while (rs.next())
            {
                i=rs.getString(1);
                j=rs.getString(2);
                Main.list.add(new TableViewSample.lessonss(i,j));
            }
            rs.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }


    public String lnotes(){
        String i=new String();
        String j = new String();
        try {
            PreparedStatement ps=conn.prepareStatement("select title,note from public.\"Lesson Notes\" as q, public.lessons as b where std_id=? and q.lesson_id=b.\"ID\";");
            ps.setInt(1,Main.id);
            ResultSet rs=ps.executeQuery();

            while (rs.next())
            {
                i=rs.getString(1);
                j=rs.getString(2);
                Main.list.add(new TableViewSample.lessonss(i,j));
            }
            rs.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    public void student(){
        int i,j;
        String id,dbirth,fname,lname,email,pass;
       // String pace;
        String c;
        try {
            PreparedStatement ps=conn.prepareStatement("select * from public.student");
            //ps.setInt(1,Main.id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                i=rs.getInt(1);
                id=Integer.toString(i);

                dbirth=rs.getString(2);
                fname=rs.getString(3);
                lname=rs.getString(4);
                email=rs.getString(5);

                pass=rs.getString(6);


                //TableViewSample.Answer a=new TableViewSample.Answer(correct,qt,sec,sub,diff,pace);
                Main.list.add(new TableViewSample.Answer(id,dbirth,fname,lname,email,pass));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void tutor(){
        int i,j;
        String id,dbirth,fname,lname,sub,email;
        String pace;
        String c;
        try {
            PreparedStatement ps=conn.prepareStatement("select * from public.tutor");
            //ps.setInt(1,Main.id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                i=rs.getInt(1);
                id=Integer.toString(i);

                dbirth=rs.getString(2);
                fname=rs.getString(3);
                lname=rs.getString(4);
                c=rs.getString(5);
                if(c.equals("m"))
                    sub=" Math";
                else
                    sub=" Verbal";
                email=rs.getString(6);


                //TableViewSample.Answer a=new TableViewSample.Answer(correct,qt,sec,sub,diff,pace);
                Main.list.add(new TableViewSample.Answer(id,dbirth,fname,lname,sub,email));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void greScore(){
        int i,j,k,l,m,n;
        String id,math,verbal,emath,everbal,year;
        String pace;
        String c;
        try {
            PreparedStatement ps=conn.prepareStatement("select * from public.grescores");
            //ps.setInt(1,Main.id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                i=rs.getInt(2);
                j=rs.getInt(3);
                k=rs.getInt(4);
                l=rs.getInt(5);
                m=rs.getInt(6);
                n=rs.getInt(7);
                id=Integer.toString(i);
                math=Integer.toString(j);
                verbal=Integer.toString(k);
                emath=Integer.toString(l);
                everbal=Integer.toString(m);
                year=Integer.toString(n);

                Main.list.add(new TableViewSample.Answer(id,math,verbal,emath,everbal,year));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
