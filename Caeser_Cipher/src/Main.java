import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import Caser_Enc_Dec.*;

public class Main {

    private static void  Seq_enc_dec(int ShiftKey  , String fileContent , String FileName) {
        Enc_Dec Encode = new Enc_Dec(ShiftKey);
        long startTime = System.currentTimeMillis();
        String EncText = Encode.Encrypt(fileContent);
        long endTime = System.currentTimeMillis();
        long Encduration = (endTime - startTime);

        System.out.println("Sequential Encryption Time : " + Encduration + " ms");
        CWriteFile Writer = new CWriteFile("Cipher.txt");
        Writer.Write(EncText);

        startTime = System.currentTimeMillis();
        String DecText = Encode.Decrypt(EncText);
        endTime = System.currentTimeMillis();
        long Decduration = (endTime - startTime);

        System.out.println("Sequential Decryption Time : " + Decduration + " ms");
        System.out.println("Sequentual Total time : " + (Decduration + Encduration) + " ms");

        Writer.setFileName("Decrypted.txt");
        Writer.Write(DecText);
        Writer.setFileName("Log.txt");
        Writer.setIsAppend(true);

        Writer.Write("for file : " + FileName + "\n" + "shift key = " + ShiftKey + "\n"+ "Sequential Encryption Time : " + Encduration + " ms\n" + "Sequential Decryption Time : " + Decduration + " ms\n" + "Sequentual Total time : " + (Decduration + Encduration) + " ms\n");
    }

    private static void parallel_Enc_dec(int ShiftKey , String fileContent  , int numberOfChars, int numberofThreads , String FileName) {

        int size = numberOfChars / numberofThreads;
        int orThreads = numberofThreads;
        if(numberOfChars < numberofThreads)
        {
            numberofThreads = 1;
            size = numberOfChars;
        }
        Thread[] threads = new Thread[numberofThreads];
        String[] Enc_chunks = new String[numberofThreads];
        String[] Dec_chunks = new String[numberofThreads];
        String[] Orig_chunks = new String[numberofThreads];


        for (int i = 0; i < numberofThreads; i++) {
            int start = i * size;
            int end = (numberofThreads == (i + 1)) ? fileContent.length() - 1 : start + size - 1;
            Enc_chunks[i] = fileContent.substring(start, end + 1);
        }
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberofThreads; i++) {
            final int indx  =i ;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    Enc_Dec Encoder = new Enc_Dec(ShiftKey);
                    Dec_chunks[indx] = Encoder.Encrypt(Enc_chunks[indx]);
                 }
            });
            threads[i].start();
        }
        for (Thread thread : threads)
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread Join Error : " + e.getMessage());
            }
        }

        long Enctime = System.currentTimeMillis() - startTime;
        System.out.println("Parallel Encryption Time : " + ( System.currentTimeMillis()- startTime) + " ms");
        StringBuilder EncText = new StringBuilder();

        for (int i = 0; i < numberofThreads; i++) {
            EncText.append(Dec_chunks[i]);
        }

        CWriteFile Writer = new CWriteFile("Cipher_Parallel.txt");
        Writer.Write(EncText.toString());

        Thread [] decThreads = new Thread[numberofThreads];

        startTime = System.currentTimeMillis(); // start time for decryption
        for (int i = 0; i < numberofThreads; i++) {
            final int indx = i;
            decThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    Enc_Dec Encoder = new Enc_Dec(ShiftKey);
                    Orig_chunks[indx] = Encoder.Decrypt(Dec_chunks[indx]);
                }
            });
            decThreads[i].start();
        }

        for (Thread thread : decThreads)
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread Join Error : " + e.getMessage());
            }
        }
        long decTime = System.currentTimeMillis() - startTime;
        System.out.println("Parallel Decryption Time : " +  (System.currentTimeMillis() - startTime) + " ms");
        StringBuilder dec = new StringBuilder();
        System.out.println("Parallel Total time : " + (decTime + Enctime) + " ms");
        for (int i = 0; i < numberofThreads; i++) {
            dec.append(Orig_chunks[i]);
        }

        Writer.setFileName("Decrypted_Parallel.txt");
        Writer.Write(dec.toString());
        Writer.setFileName("Log.txt");
        Writer.setIsAppend(true);
        Writer.Write("parallel with number of threads : " + orThreads + "\n");
        Writer.Write("Parallel Encryption Time : " + Enctime + " ms\n" + "Parallel Decryption Time : " + decTime + " ms\n" + "Parallel Total time : " + (decTime + Enctime) + " ms\n");
        Writer.Write("----------------------------------------------------------------------------------------------------------\n");
    }





    public static void main(String[] args) throws IOException {
        // 1- file to
        Scanner scan = new Scanner(System.in);
        System.out.println("-------------------------------------------------------------");
        System.out.println("Welcome to the Caesar Cipher Encryption/Decryption Program");
        System.out.println("-------------------------------------------------------------");

        System.out.println("Please make sure to have the file you want to Encrypt/Decrypt in the same directory as the program");
        System.out.println("-------------------------------------------------------------");

        System.out.print("fileName you want Encrypt/Decrypt . Ex. input<fileSize>.txt , available sizes (1m , 5k ,25b , 10m , 30m) :");
        String FileName= scan.nextLine();
        System.out.println("-------------------------------------------------------------");

        System.out.print("enter the shift Key : ");
        int ShiftKey = scan.nextInt();
        System.out.println("-------------------------------------------------------------");

        System.out.print("enter number of threads you want to use in Parallel Encryption/Decryption : ");
        int numberOfThreads = scan.nextInt();
        CReadFile Reader = new CReadFile(FileName);
        int [] numberofChars = new int[1];
        String fileContent = Reader.Reader( numberofChars);

        Seq_enc_dec(ShiftKey, fileContent ,FileName);

        parallel_Enc_dec(ShiftKey, fileContent, numberofChars[0], numberOfThreads ,FileName);

        System.out.println("Done encrypting and decrypting the file : " + FileName);


    }
}