package exercise1.virginia;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String ciper =sc.next();
        Virginiatest virginiatest = new Virginiatest();
        virginiatest.decryptCipher(virginiatest.Friedman(ciper),ciper);
    }
}
