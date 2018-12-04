package com.dh.lt;

import org.junit.Test;

public class _12_IntegerToRoman {
    public String intToRoman(int num) {
        //将数字分解为千-百-十-个
        int si = num % 10;
        int ten = num / 10 % 10;
        int hu = num / 100 % 10;
        int th = num / 1000 % 10;

        StringBuffer res = new StringBuffer();
        if(th > 0){
            for (int i = 0; i < th; i++) {
                res.append('M');
            }
        }

        if(hu > 0){
            setValue(hu,'C','D','M',res);
        }

        if(ten > 0){
            setValue(ten,'X','L','C',res);
        }


        if(si > 0){
            setValue(si,'I','V','X',res);
        }
        return res.toString();
    }

    private void setValue(int num,char min,char mid ,char max,StringBuffer res){
        if(num > 0){
            if (num > 8) {
                for (int i = 8; i < num; i++) {
                    res.append(min);
                }
                res.append(max);
            } else if (num >= 5) {
                res.append(mid);
                for (int i = 5; i < num; i++) {
                    res.append(min);
                }
            } else if (num > 3) {
                for (int i = 3; i < num; i++) {
                    res.append(min);
                }
                res.append(mid);
            } else {
                for (int i = 0; i < num; i++) {
                    res.append(min);
                }
            }
        }
    }

    @Test
    public void intToRoman() {
        int input = 58;
        System.out.println(intToRoman(input));
    }
}
