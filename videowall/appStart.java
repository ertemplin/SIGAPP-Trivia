import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.event.*;
import java.util.*;


public class appStart extends quizApp implements ActionListener{

  public appStart(){
    quizApp ap = new quizApp();
    ap.start();
    try{
      ap.join();
    }catch(Exception e){}
  }
  
  public static void main(String[] args){
    new appStart();
  }
}