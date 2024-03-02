package Caser_Enc_Dec;

import java.io.*;

public class CReadFile {
    String FileName ;
    public CReadFile() {
        this.FileName= "null";
    }
    public CReadFile(String FileName ) {
        this.FileName = FileName;
    }
    public String Reader(int[] numberOfLines , int[] numberofChars){
        System.out.println(FileName);
        try(BufferedReader br = new BufferedReader(new FileReader(FileName))) {
            StringBuilder fileContent = new StringBuilder();
            String line = br.readLine();
            numberOfLines[0] =1 ;
            if(line == null ) {
                numberOfLines[0] =0 ;
                return "Empty";
            }else {
                numberofChars[0]= line.length();
            }
            while (line != null) {
                fileContent.append(line).append("\n");
                line = br.readLine();
                if (line != null) {numberOfLines[0]++; numberofChars[0]+=line.length();}

            }
            return fileContent.toString();

        }catch (IOException e ){
            System.out.println("File not find error : " + e.getMessage());
            return "Error";
        }

    }
//    public static void  main(String [] args ) {
//        CReadFile Reader = new CReadFile("input5k.txt");
//        int [] numberOfLines = new int[1];
//        int [] numberOfChars = new int[1];
//
//        String fileContent = Reader.Reader(numberOfLines,numberOfChars);
//        System.out.println(fileContent);
//        System.out.println("Number of lines : " + numberOfLines[0]);
//        System.out.println("Number of chars  : " + numberOfChars[0]);
//
//
//    }

}
