package Caser_Enc_Dec;

//import java.security.spec.ECField;

public class Enc_Dec {
    int ShiftKey ;
    int DecShiftKey;
    public Enc_Dec() {
        this.ShiftKey = 0;
        this.DecShiftKey =0 ;
    }
    public Enc_Dec(int shiftKey) {
        this.ShiftKey = shiftKey;
        this.DecShiftKey = 26 - shiftKey;
    }

    public String Encrypt(String Text   ) {
        StringBuilder EncText = new StringBuilder();
        for (char ch : Text.toCharArray()) {
            if(Character.isLetter(ch)) {
                char base  = Character.isLowerCase(ch) ? 'a' : 'A';
                EncText.append((char) ((ch - base + ShiftKey) % 26 + base));
            }
            else {
                EncText.append(ch);
            }
        }
        return EncText.toString();

    }
    public String Decrypt(String Text) {
        StringBuilder DecText = new StringBuilder();

        for (char ch : Text.toCharArray()) {
            if(Character.isLetter(ch)) {
                char base  = Character.isLowerCase(ch) ? 'a' : 'A';
                DecText.append((char) ((ch - base + DecShiftKey) % 26 + base));
            }
            else {
                DecText.append(ch);
            }
        }
        return DecText.toString();

    }
    public static void main(String[] args) {
        Enc_Dec Encoder = new Enc_Dec(7);
        String EncText = Encoder.Encrypt("HelloWorldBitch");
        System.out.println("Cipher Text " + EncText);
        String DecText = Encoder.Decrypt(EncText);
        System.out.println("Decrypted Text  : " + DecText);
    }

}
