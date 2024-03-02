package Caser_Enc_Dec;
import java.io.*;

public class CWriteFile {
    public String FileName;
    public boolean isAppend;
    public CWriteFile(){
        FileName = "null";
        isAppend = false;
    }
    public void setFileName(String FileName){
        this.FileName = FileName;
    }
    public void setIsAppend(boolean isAppend){
        this.isAppend= isAppend;
    }
    public   CWriteFile(String  FileName) {
        this.FileName = FileName;
    }
    public void Write(String Text){
        if(FileName.equals("null")) {
            System.out.println("File name not set");
        }
        else {
            try {
                java.io.FileWriter fw = new java.io.FileWriter(FileName,isAppend );
                fw.write(Text);
                fw.close();
            } catch (java.io.IOException e) {
                System.out.println("Error writing to file");
            }
        }
    }
//    public static void main(String[] args) {
//            CWriteFile Writer = new CWriteFile("testappend");
//            Writer.setIsAppend(true);
//
//            String Txt = "helloWorld\nhellobitch as nigger\n";
//            Writer.Write(Txt);
//            String Txt2 = "lineeeeeeeeeeeeeeeeeeeeeeeeeeeeee2\n";
//            Writer.Write(Txt2);
//
//    }


}
