package org.json;
import java.net.*;
import java.io.*;

public class Test{
  
  public static void main(String[] a){

  }
  
  public static Question getQuestion(){
    Question q= null;
            try{
    URL oracle = new URL("http://pod2-1.cs.purdue.edu:3000/random");
    BufferedReader in = new BufferedReader(
      new InputStreamReader(oracle.openStream()));

    String s = " ";
        String inputLine="";
                System.out.println(inputLine);
                while ((inputLine = in.readLine()) != null){
            s+=inputLine;
        //System.out.println(inputLine);
                }
        in.close();

    JSONObject object = new JSONObject(s);
    String[] a = new String[4];
    String question = object.getString("question");
    a[0] = object.getString("correctAnswer");
    a[1] = object.getString("incorrect1");
    a[2] = object.getString("incorrect2");
    a[3] = object.getString("incorrect3");
    q = new Question(question,a);
    }catch(Exception e){e.printStackTrace();}
    return q;
  }
  
}