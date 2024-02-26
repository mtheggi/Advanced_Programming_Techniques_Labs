import java.awt.*;
import java.io.File;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import Caser_Enc_Dec.*;

public class Main {

    private static void  Seq_enc_dec(int ShiftKey  , String fileContent) {
        Enc_Dec Encode = new Enc_Dec(ShiftKey);
        long startTime = System.currentTimeMillis();
        String EncText = Encode.Encrypt(fileContent);
        long endTime = System.currentTimeMillis();
        long Encduration = (endTime - startTime);

        System.out.println("Encryption Time : " + Encduration + " ms");
        CWriteFile Writer = new CWriteFile("Cipher.txt");
        Writer.Write(EncText);

        startTime = System.currentTimeMillis();
        String DecText = Encode.Decrypt(EncText);
        endTime = System.currentTimeMillis();
        long Decduration = (endTime - startTime);

        System.out.println("Decryption Time : " + Decduration + " ms");

        Writer.setFileName("Decrypted.txt");
        Writer.Write(DecText);
    }

    private static void parallel_Enc_dec(int ShiftKey , String fileContent , int numberOfLines , int numberofThreads){




    }





    public static void main(String[] args) {
        // 1- file to
        Scanner scan = new Scanner(System.in);
        System.out.print("fileName you want Encrypt/Decrypt . Ex. input<fileSize>.txt , available sizes (1m , 5k ,25b , 10m) :");
        String FileName= scan.nextLine();
        System.out.print("enter the shift Key : ");
        int ShiftKey = scan.nextInt();
        System.out.print("enter number of threads you want to use in Parallel Encryption/Decryption : ");
        int numberOfThreads = scan.nextInt();
        CReadFile Reader = new CReadFile(FileName);
        int [] numberOfLines = new int[1];
        String fileContent = Reader.Reader(numberOfLines);

        Seq_enc_dec(ShiftKey, fileContent);

        parallel_Enc_dec(ShiftKey, fileContent, numberOfLines[0], numberOfThreads);

    }
}