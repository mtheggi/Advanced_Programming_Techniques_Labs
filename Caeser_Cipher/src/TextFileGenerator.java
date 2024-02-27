import org.w3c.dom.Text;

import java.io.FileWriter;
import java.io.IOException;

//generate a text file with the given name and Size in kilo bytes
public class TextFileGenerator {
    public enum Size {
        B,
        KB, 
        MB, 
    }
    public static void generateFile(String fileName, int size , Size unit) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (long i = 0; i < size * (unit == Size.B ? 1 : (unit == Size.KB)? 1024 : (unit == Size.MB)? 1024 * 1024 : 0) ; i++) {
                writer.write(Character.toString((char) (Math.random() * 26 + 'a')));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String [] args ) {
        TextFileGenerator.generateFile("input30m.txt" , 30 , Size.MB); ;

    }

}