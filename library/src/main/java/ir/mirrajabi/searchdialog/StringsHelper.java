package ir.mirrajabi.searchdialog;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

/**
 * Created by MADNESS on 5/16/2017.
 */

public class StringsHelper {

    public static SpannableStringBuilder highlightLCS(String text1, String text2,
                                                      int highlightColor) {
        // TODO fix exact match highlighting
        String lcs = lcs(text1.toLowerCase(), text2.toLowerCase());
        String tLower = text1.toLowerCase();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text1);
        for (int i = 0; i < tLower.length() && lcs.length() > 0; i++) {
            if (tLower.charAt(i) == lcs.charAt(0)) {
                builder.setSpan(new ForegroundColorSpan(highlightColor), i, i + 1, 0);
                lcs = lcs.substring(1);
            }
        }
        return builder;
    }

    public static String lcs(String text1, String text2) {
        int[][] lengths = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text1.length(); i++)
            for (int j = 0; j < text2.length(); j++)
                if (text1.charAt(i) == text2.charAt(j))
                    lengths[i + 1][j + 1] = lengths[i][j] + 1;
                else
                    lengths[i + 1][j + 1] =
                            Math.max(lengths[i + 1][j], lengths[i][j + 1]);
        StringBuffer sb = new StringBuffer();
        for (int x = text1.length(), y = text2.length();
             x != 0 && y != 0; ) {
            if (lengths[x][y] == lengths[x - 1][y])
                x--;
            else if (lengths[x][y] == lengths[x][y - 1])
                y--;
            else {
                assert text1.charAt(x - 1) == text2.charAt(y - 1);
                sb.append(text1.charAt(x - 1));
                x--;
                y--;
            }
        }
        return sb.reverse().toString();
    }
}
