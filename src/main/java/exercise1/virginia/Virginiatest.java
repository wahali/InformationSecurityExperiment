package exercise1.virginia;

import java.util.ArrayList;
import java.util.HashMap;

public class Virginiatest {
    private String res;

    public void setRes(String res) {
        this.res = res;
    }

    public String getRes() {
        return res;
    }

    // Friedman测试法确定密钥长度
    public int Friedman(String ciphertext) {
        ciphertext = ciphertext.toUpperCase();
        int keyLength = 1; // 猜测密钥长度
        double[] Ic; // 重合指数
        double avgIc; // 平均重合指数
        ArrayList<String> cipherGroup; // 密文分组

        while (true) {
            Ic = new double[keyLength];
            cipherGroup = new ArrayList<String>();
            avgIc = 0;

            // 1 先根据密钥长度分组
            for (int i = 0; i < keyLength; ++i) {
                StringBuilder tempGroup = new StringBuilder();
                for (int j = 0; i + j * keyLength < ciphertext.length(); ++j) {
                    tempGroup.append(ciphertext.charAt(i + j * keyLength));
                }
                cipherGroup.add(tempGroup.toString());
            }

            // 2 再计算每一组的重合指数
            for (int i = 0; i < keyLength; ++i) {
                String subCipher = cipherGroup.get(i); // 子串
                HashMap<Character, Integer> occurrenceNumber = new HashMap<Character, Integer>(); // 字母及其出现的次数

                // 2.1 初始化字母及其次数键值对
                for (int h = 0; h < 26; ++h) {
                    occurrenceNumber.put((char) (h + 65), 0);
                }

                // 2.2 统计每个字母出现的次数
                for (int j = 0; j < subCipher.length(); ++j) {
                    if(occurrenceNumber.containsKey(subCipher.charAt(j))){
                        occurrenceNumber.put(subCipher.charAt(j), occurrenceNumber.get(subCipher.charAt(j)) + 1);
                    }
                    else {
                        occurrenceNumber.put(subCipher.charAt(j), 1);
                    }
                }

                // 2.3 计算重合指数
                double denominator = Math.pow((double) subCipher.length(), 2);
                for (int k = 0; k < 26; ++k) {
                    double o = (double) occurrenceNumber.get((char) (k + 65));
                    Ic[i] += o * (o - 1);
                }
                Ic[i] /= denominator;
            }

            // 3 判断退出条件,重合指数的平均值是否大于0.065
            for (int i = 0; i < keyLength; ++i) {
                avgIc += Ic[i];
            }
            avgIc /= (double) keyLength;
            if (avgIc >= 0.06) {
                break;
            } else {
                keyLength++;
            }
        } // while--end

        // 打印密钥长度，分组，重合指数，平均重合指数
        System.out.println("密钥长度为：" + String.valueOf(keyLength));
        System.out.println("\n密文分组及其重合指数为：");
        res = "";
        this.res += "密钥长度为：" + String.valueOf(keyLength) + "\n密文分组及其重合指数为：";
        for (int i = 0; i < keyLength; ++i) {
            this.res += '\n' + cipherGroup.get(i) + "   重合指数: " + Ic[i];
            System.out.println(cipherGroup.get(i) + "   重合指数: " + Ic[i]);
        }
        System.out.println("\n平均重合指数为： " + String.valueOf(avgIc));
        this.res += "\n平均重合指数为： " + String.valueOf(avgIc);
        return keyLength;
    }// Friedman--end


    // 再次使用重合指数法确定密钥
    public void decryptCipher(int keyLength, String ciphertext) {
        ciphertext = ciphertext.toUpperCase();
        int[] key = new int[keyLength];
        ArrayList<String> cipherGroup = new ArrayList<String>();
        double[] probability = new double[]{0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.02, 0.061, 0.07, 0.002, 0.008,
                0.04, 0.024, 0.067, 0.075, 0.019, 0.001, 0.06, 0.063, 0.091, 0.028, 0.01, 0.023, 0.001, 0.02, 0.001};

        // 1 先根据密钥长度分组
        for (int i = 0; i < keyLength; ++i) {
            StringBuilder temporaryGroup = new StringBuilder();
            for (int j = 0; i + j * keyLength < ciphertext.length(); ++j) {
                temporaryGroup.append(ciphertext.charAt(i + j * keyLength));
            }
            cipherGroup.add(temporaryGroup.toString());
        }

        // 2 确定密钥
        for (int i = 0; i < keyLength; ++i) {
            double MG; // 重合指数
            int flag; // 移动位置
            int g = 0; // 密文移动g个位置
            HashMap<Character, Integer> occurrenceNumber; // 字母出现次数
            String subCipher; // 子串

            while (true) {
                MG = 0;
                flag = 65 + g;
                subCipher = cipherGroup.get(i);
                occurrenceNumber = new HashMap<Character, Integer>();

                // 2.1 初始化字母及其次数
                for (int h = 0; h < 26; ++h) {
                    occurrenceNumber.put((char) (h + 65), 0);
                }

                // 2.2 统计字母出现次数
                for (int j = 0; j < subCipher.length(); ++j) {
                    if(occurrenceNumber.containsKey(subCipher.charAt(j))){
                        occurrenceNumber.put(subCipher.charAt(j), occurrenceNumber.get(subCipher.charAt(j)) + 1);
                    }
                    else{
                        occurrenceNumber.put(subCipher.charAt(j),1);
                    }
                }

                // 2.3 计算重合指数
                for (int k = 0; k < 26; ++k, ++flag) {
                    double p = probability[k];
                    flag = (flag == 91) ? 65 : flag;
                    double f = (double) occurrenceNumber.get((char) flag) / subCipher.length();
                    MG += p * f;
                }

                // 2.4 判断退出条件
                if (MG >= 0.055) {
                    key[i] = g;
                    break;
                } else {
                    ++g;
                }
            } // while--end
        } // for--end

        // 3 打印密钥
        StringBuilder keyString = new StringBuilder();
        for (int i = 0; i < keyLength; ++i) {
            keyString.append((char) (key[i] + 65));
        }
        System.out.println("\n密钥为: " + keyString.toString());
        this.res += "\n密钥为: " + keyString.toString();
        // 4 解密
        StringBuilder plainBuffer = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); ++i) {
            int keyFlag = i % keyLength;
            int change = (int) ciphertext.charAt(i) - 65 - key[keyFlag];
            char plainLetter = (char) ((change < 0 ? (change + 26) : change) + 65);
            plainBuffer.append(plainLetter);
        }
        this.res += "\n明文为：\n" + plainBuffer.toString().toLowerCase();
        System.out.println("\n明文为：\n" + plainBuffer.toString().toLowerCase());

    }


}

//测试样例
//KCCPKBGUFDPHQTYAVINRRTMVGRKDNBVFDETDGILT
//        XRGUDDKOTFMBPVGEGLTGCKQRACQCWDNAWCRXI
//    ZAKFTLEWRPTYCQKYVXCHKFTPONCQQRHJVAJUWE
//            TMCMSPKQDYHJVDAHCTRLSVSKCGCZQQDZXGSFRL
//    SWCWSJTBHAFSIASPRJAHKJRJUMVGKMITZHFPDISP
//            ZLVLGWTFPLKKEBDPGCEBSHCTJRWXBAFSPEZQNR
//    WXCVYCGAONWDDKACKAWBBIKFTIOVKCGGHJVLNHI
//            FFSQESVYCLACNVRWBBIREPBBVFEXOSCDYGZWPFD
//TKFQIYCWHJVLNHIQIBTKHJVNPIST

//参考https://blog.csdn.net/White_Idiot/article/details/61201864
