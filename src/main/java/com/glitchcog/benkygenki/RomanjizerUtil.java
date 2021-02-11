package com.glitchcog.benkygenki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Matt Yanos
 */
public class RomanjizerUtil
{
    private static List<Map<String, String>> K2R_PASS_MAPS;
    private static List<Map<String, String>> R2K_PASS_MAPS;

    static
    {
        K2R_PASS_MAPS = new ArrayList<>();
        R2K_PASS_MAPS = new ArrayList<>();

        Map<String, String> youon = new HashMap<>();
        Map<String, String> youonReverse = new HashMap<>();
        loadMaps(youon, youonReverse, "きゃきゅきょキャキュキョ", 2, "kyakyukyokyakyukyo", 3);
        loadMaps(youon, youonReverse, "しゃしゅしょシャシュショ", 2, "shyashyushyoshyashyushyo", 4);
        loadMaps(youon, youonReverse, "ちゃちゅちょチャチュチョ", 2, "chyachyuchyochyachyuchyo", 4);
        loadMaps(youon, youonReverse, "にゃにゅにょニャニュニョ", 2, "nyanyunyonyanyunyo", 3);
        loadMaps(youon, youonReverse, "ひゃひゅひょヒャヒュヒョ", 2, "hyahyuhyohyahyuhyo", 3);
        loadMaps(youon, youonReverse, "みゃみゅみょミャミュミョ", 2, "myamyumyomyamyumyo", 3);
        loadMaps(youon, youonReverse, "りゃりゅりょリャリュリョ", 2, "ryaryuryoryaryuryo", 3);
        loadMaps(youon, youonReverse, "ぎゃぎゅぎょギャギュギョ", 2, "gyagyugyogyagyugyo", 3);
        loadMaps(youon, youonReverse, "じゃじゅじょジャジュジョ", 2, "jyajyujyojyajyujyo", 3);
        loadMaps(youon, youonReverse, "ぢゃぢゅぢょヂャヂュヂョ", 2, "dyadyudyodyadyudyo", 3);
        loadMaps(youon, youonReverse, "びゃびゅびょビャビュビョ", 2, "byabyubyobyabyubyo", 3);
        loadMaps(youon, youonReverse, "ぴゃぴゅぴょピャピュピョ", 2, "pyapyupyopyapyupyo", 3);
        loadMaps(youon, youonReverse, "ファフィフゥフェフォ", 2, "fuafuifuufuefuo", 3);
        loadMaps(youon, youonReverse, "うぃうぇウィウェ", 2, "wiwewiwe", 2);
        K2R_PASS_MAPS.add(youon);
        R2K_PASS_MAPS.add(youonReverse);

        Map<String, String> stdChars = new HashMap<>();
        Map<String, String> stdCharsReverse = new HashMap<>();
        loadMaps(stdChars, stdCharsReverse, "・「」（）！０１２３４５６７８９〜～＿’『』　＠＃＄％＾＆＊／", 1, "/\"\"()!0123456789~~_[] @#$%^&*/", 1);
        loadMaps(stdChars, stdCharsReverse, "。、：；", 1, ".,:;", 1);
        loadMaps(stdChars, stdCharsReverse, "あいうえおんアイウエオン", 1, "aiueonaiueon", 1);
        loadMaps(stdChars, stdCharsReverse, "かきくけこカキクケコ", 1, "kakikukekokakikukeko", 2);
        loadMaps(stdChars, stdCharsReverse, "さすせそサスセソ", 1, "sasusesosasuseso", 2);
        loadMaps(stdChars, stdCharsReverse, "しシ", 1, "shishi", 3);
        loadMaps(stdChars, stdCharsReverse, "たてとタテト", 1, "tatetotateto", 2);
        loadMaps(stdChars, stdCharsReverse, "ちつチツ", 1, "chitsuchitsu", 3);
        loadMaps(stdChars, stdCharsReverse, "なにぬねのナニヌネノ", 1, "naninunenonaninuneno", 2);
        loadMaps(stdChars, stdCharsReverse, "はひふへほハヒフヘホ", 1, "hahifuhehohahifuheho", 2);
        loadMaps(stdChars, stdCharsReverse, "まみむめもマミムメモ", 1, "mamimumemomamimumemo", 2);
        loadMaps(stdChars, stdCharsReverse, "やゆよヤユヨ", 1, "yayuyoyayuyo", 2);
        loadMaps(stdChars, stdCharsReverse, "らりるれろラリルレロ", 1, "rarirurerorarirurero", 2);
        loadMaps(stdChars, stdCharsReverse, "わをワヲ", 1, "wawonwawon", 2);
        loadMaps(stdChars, stdCharsReverse, "がぎぐげごガギグゲゴ", 1, "gagigugegogagigugego", 2);
        loadMaps(stdChars, stdCharsReverse, "ざじずぜぞザジズゼゾ", 1, "zajizuzezozajizuzezo", 2);
        loadMaps(stdChars, stdCharsReverse, "だぢでどダヂデド", 1, "dadidedodadidedo", 2);
        loadMaps(stdChars, stdCharsReverse, "づヅ", 1, "dzudzu", 3);
        loadMaps(stdChars, stdCharsReverse, "ばびぶべぼバビブベボ", 1, "babibubebobabibubebo", 2);
        loadMaps(stdChars, stdCharsReverse, "ぱぴぷぺぽパピプペポ", 1, "papipupepopapipupepo", 2);
        K2R_PASS_MAPS.add(stdChars);
        R2K_PASS_MAPS.add(stdCharsReverse);
    }

    protected static void printSwapMaps()
    {
        for (Map<String, String> map : K2R_PASS_MAPS)
        {
            for (Entry<String, String> e : map.entrySet())
            {
                System.out.println(e.getKey() + "\t" + e.getValue());
            }
        }
    }

    public static String romanjize(String kana)
    {
        String romanji = kana;

        for (Map<String, String> map : K2R_PASS_MAPS)
        {
            for (Entry<String, String> e : map.entrySet())
            {
                romanji = romanji.replaceAll(e.getKey(), e.getValue());
            }
        }

        final char cyouonpu = 'ー';
        // Purge dupes
        while (romanji.indexOf(cyouonpu + "" + cyouonpu) > -1)
        {
            romanji = romanji.replaceAll(cyouonpu + "" + cyouonpu, "" + cyouonpu);
        }
        while (romanji.indexOf(cyouonpu) > -1)
        {
            int i = romanji.indexOf(cyouonpu);
            romanji = romanji.substring(0, i) + (i <= 0 ? "-" : romanji.charAt(i - 1)) + romanji.substring(i + 1, romanji.length());
        }

        final char sokuon = 'っ';
        final char sokuonKatakana = 'ッ';
        // Make all of them hiragana
        romanji = romanji.replaceAll("" + sokuonKatakana, "" + sokuon);
        // Purge dupes
        while (romanji.indexOf(sokuon + "" + sokuon) > -1)
        {
            romanji = romanji.replaceAll(sokuon + "" + sokuon, "" + sokuon);
        }
        while (romanji.indexOf(sokuon) > -1)
        {
            int i = romanji.indexOf(sokuon);
            romanji = romanji.substring(0, i) + (i >= romanji.length() - 1 ? "-" : romanji.charAt(i + 1)) + romanji.substring(i + 1, romanji.length());
        }

        // Put a space after some punctuation
        romanji = romanji.replaceAll("\\.", ". ");
        romanji = romanji.replaceAll(",", ", ");
        romanji = romanji.replaceAll(":", ": ");
        romanji = romanji.replaceAll(";", "; ");

        return romanji.trim();
    }

    private static void loadMaps(Map<String, String> map, Map<String, String> revMap, String keys, int keyLength, String values, int valueLength)
    {
        for (int i = 0; i < Math.min(keys.length() / keyLength, values.length() / valueLength); i++)
        {
            String key = keys.substring(i * keyLength, (i + 1) * keyLength);
            String val = values.substring(i * valueLength, (i + 1) * valueLength);
            map.put(key, val);
            revMap.put(val, key);
        }
    }

    private RomanjizerUtil()
    {
    }
}
