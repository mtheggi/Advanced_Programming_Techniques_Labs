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
    public String Reader(int[] numberOfLines){
        System.out.println(FileName);
        try(BufferedReader br = new BufferedReader(new FileReader(FileName))) {
            StringBuilder fileContent = new StringBuilder();
            numberOfLines[0] =1 ;
            String line = br.readLine();
            if(line == null ) {
                numberOfLines[0] =0 ;
                return "Empty";
            }
            while (line != null) {
                fileContent.append(line).append("\n");
                line = br.readLine();
                if (line != null) numberOfLines[0]++;

            }
            return fileContent.toString();

        }catch (IOException e ){
            System.out.println("File not find error : " + e.getMessage());
            return "Error";
        }

    }
//    public static void  main(String [] args ) {
//        CReadFile Reader = new CReadFile("Input.txt");
//        int [] numberOfLines = new int[1];
//        String fileContent = Reader.Reader(numberOfLines);
//        System.out.println(fileContent);
//        System.out.println("Number of lines : " + numberOfLines[0]);
//    }

}
