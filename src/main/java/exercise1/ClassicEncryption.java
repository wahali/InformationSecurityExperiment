package exercise1;

import exercise1.virginia.Virginiatest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static java.lang.Math.abs;

public class ClassicEncryption {

    private JTextField Plaintext;
    private JTextField Key;
    private JTextField Ciphertext;
    private JButton 加密Button;
    private JButton 解密Button;
    private JLabel 明文;
    private JLabel 密文;
    private JLabel 密钥;
    private JPanel rootPanel;
    private JButton 维吉尼亚加密Button;
    private JButton 维吉尼亚破解Button;
    private JTabbedPane tabbedPane1;
    private JTextArea textArea1;
    private JTabbedPane tabbedPane2;

    public String shiftEncryption(String Plaintext, int dieta){
        char [] arr = Plaintext.toCharArray();
        for(int i = 0;i < arr.length;++i){
            if(arr[i]>='a'&&arr[i]<='z'){
                arr[i] = (char) ('a' + (arr[i] -'a' + dieta) % 26);
            }
            else if(arr[i]>='A'&&arr[i]<='Z'){
                arr[i] = (char) ('A' + (arr[i] -'A' + dieta) % 26);
            }
        }
        return new String(arr);
    }

    public String shiftDecryption(String Ciphertext, int dieta){
        char [] arr = Ciphertext.toCharArray();
        for(int i = 0;i < arr.length;++i){
            if(arr[i]>='a'&&arr[i]<='z'){
                arr[i] = (char) ('a' + (arr[i] - 'a'- dieta + 26) % 26);
            }
            else if(arr[i]>='A'&&arr[i]<='Z'){
                arr[i] = (char) ('A' + (arr[i]  - 'A'- dieta + 26) % 26);
            }
        }
        return new String(arr);
    }

    public String virginiaEncryption(String Plaintext,String Key){
        Key = Key.toLowerCase();
        char [] arr = Plaintext.toCharArray();
        char [] key = Key.toCharArray();
        int cnt = 0;
        for(int i = 0;i < arr.length;++i){
            if(cnt == key.length) { cnt = 0; }
            if(arr[i]>='a'&&arr[i]<='z'){
                arr[i] = (char) ('a' + (arr[i] - 'a' + (key[cnt] - 'a')) % 26);
                cnt ++;
            }
            else if(arr[i]>='A' && arr[i] <= 'Z'){
                arr[i] = (char) ('A' + (arr[i] - 'A' + (key[cnt] - 'a')) % 26);
                cnt ++;
            }
        }
        return new String(arr);
    }


    public ClassicEncryption() {
        加密Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String plaintext = Plaintext.getText();
                int Key = Integer.parseInt(ClassicEncryption.this.Key.getText());
                Ciphertext.setText(shiftEncryption(plaintext,Key));
            }
        });
        解密Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ciphertext = Ciphertext.getText();
                int Key= Integer.parseInt(ClassicEncryption.this.Key.getText());
                Plaintext.setText(shiftDecryption(ciphertext,Key));
            }
        });
        维吉尼亚加密Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String plaintext = Plaintext.getText();
                String  Key = ClassicEncryption.this.Key.getText();
                Ciphertext.setText(virginiaEncryption(plaintext,Key));
            }
        });
        维吉尼亚破解Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ciphertext = Ciphertext.getText();
                Virginiatest virginiatest = new Virginiatest();
                int len = virginiatest.Friedman(ciphertext);
                virginiatest.decryptCipher(len,ciphertext);
                textArea1.setText(virginiatest.getRes());
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

//        JFrame frm = new JFrame("身份验证注册");
//        frm.setSize(450, 300);
//        frm.setLocationRelativeTo(null);
//        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frm.setResizable(false);              //设置窗口不能调整大小
//        GridBagLayout lay = new GridBagLayout();
//        frm.setLayout(lay);
//        frm.setVisible(true);
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("经典密码的实现");
        frame.setContentPane(new ClassicEncryption().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
