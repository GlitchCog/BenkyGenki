package com.glitchcog.benkygenki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import com.glitchcog.benkygenki.model.GrammarResult;
import com.glitchcog.benkygenki.model.Kana;
import com.glitchcog.benkygenki.model.KanaType;
import com.glitchcog.benkygenki.model.Kanji;
import com.glitchcog.benkygenki.model.KanjiReading;
import com.glitchcog.benkygenki.model.KanjiVocab;
import com.glitchcog.benkygenki.model.Particle;
import com.glitchcog.benkygenki.model.ReadingType;
import com.glitchcog.benkygenki.model.Vocab;
import com.glitchcog.benkygenki.model.VocabResult;
import com.glitchcog.benkygenki.model.VocabType;

public class BenkyGenkiData
{
    private BenkyGenkiData()
    {
    }

    private static String dbUrl;

    private static Map<Integer, KanaType> kanaTypes;
    private static List<Kana> kana;
    private static Map<Integer, VocabType> vocabTypes;
    private static List<Vocab> vocab;
    private static Map<Integer, Kanji> kanji;
    private static List<KanjiVocab> kanjiVocab;
    /**
     * Kanji Vocab where duplicates due to multiple source kanji in the vocab are removed
     */
    private static List<KanjiVocab> kanjiVocabDistinct;
    private static Integer minLesson;
    private static Integer maxLesson;

    public static Map<Integer, KanaType> getKanaTypes()
    {
        return kanaTypes;
    }

    public static List<Kana> getKana()
    {
        return kana;
    }

    public static Map<Integer, VocabType> getVocabTypes()
    {
        return vocabTypes;
    }

    public static List<Vocab> getVocab()
    {
        return vocab;
    }

    public static Map<Integer, Kanji> getKanji()
    {
        return kanji;
    }

    public static List<KanjiVocab> getKanjiVocab()
    {
        return kanjiVocab;
    }

    public static List<KanjiVocab> getDistinctKanjiVocab()
    {
        return kanjiVocabDistinct;
    }

    public static int getMinLesson()
    {
        return minLesson == null ? 0 : minLesson;
    }

    public static int getMaxLesson()
    {
        return maxLesson == null ? 0 : maxLesson;
    }

    public static boolean loadData(String dbPath)
    {
        boolean initialized = initDbDriver();

        if (!initialized)
        {
            String errorMessage = "Unable to initialize database driver.";
            System.err.println(errorMessage);
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        dbUrl = "jdbc:sqlite:" + dbPath;
        try (Connection connection = DriverManager.getConnection(dbUrl))
        {
            kanaTypes = loadKanaTypes(connection);
            kana = loadKana(connection);
            vocabTypes = loadVocabTypes(connection);
            vocab = loadVocab(connection);
            kanji = loadKanji(connection);
            kanjiVocab = loadKanjiVocab(connection);
            return true;
        }
        catch (Exception e)
        {
            String errorMessage = "Error accessing data at " + dbPath;
            System.err.println(errorMessage);
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private static boolean initDbDriver()
    {
        try
        {
            Class<?> driver = Class.forName("org.sqlite.JDBC");
            driver.getDeclaredConstructor().newInstance();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private static Map<Integer, KanaType> loadKanaTypes(Connection connection) throws SQLException
    {
        Map<Integer, KanaType> kanaTypes = null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT id, name FROM kana_type"))
        {
            try (ResultSet rs = ps.executeQuery())
            {
                kanaTypes = new HashMap<Integer, KanaType>();
                while (rs.next())
                {
                    final int id = rs.getInt("id");
                    final String name = rs.getString("name");
                    kanaTypes.put(id, KanaType.getByName(name));
                }
            }
        }
        return kanaTypes;
    }

    private static Map<Integer, VocabType> loadVocabTypes(Connection connection) throws SQLException
    {
        Map<Integer, VocabType> vocabTypes = null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT id, name FROM type"))
        {
            try (ResultSet rs = ps.executeQuery())
            {
                vocabTypes = new HashMap<Integer, VocabType>();
                while (rs.next())
                {
                    final int id = rs.getInt("id");
                    final String name = rs.getString("name");
                    vocabTypes.put(id, VocabType.getByName(name));
                }
            }
        }
        return vocabTypes;
    }

    private static List<Kana> loadKana(Connection connection) throws SQLException
    {
        List<Kana> kana = null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT kana.id kid, kana, sound, name kana_type, dakuten, handakuten, chiisai, youon FROM kana INNER JOIN kana_type ON kana_type.id = fk_kana_type"))
        {
            try (ResultSet rs = ps.executeQuery())
            {
                kana = new ArrayList<Kana>();
                while (rs.next())
                {
                    Kana k = new Kana();
                    k.setId(rs.getInt("kid"));
                    k.setKana(rs.getString("kana"));
                    k.setSound(rs.getString("sound"));
                    k.setType(KanaType.getByName(rs.getString("kana_type")));
                    k.setDakuten(rs.getBoolean("dakuten"));
                    k.setHandakuten(rs.getBoolean("handakuten"));
                    k.setChiisai(rs.getBoolean("chiisai"));
                    k.setYouon(rs.getBoolean("youon"));
                    kana.add(k);
                }
            }
        }
        return kana;
    }

    private static List<Vocab> loadVocab(Connection connection) throws SQLException
    {
        List<Vocab> vocab = null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT vocab.id vid, book, lesson, name, kana, kanji, romanji, english FROM vocab INNER JOIN type ON vocab.fk_type = type.id"))
        {
            try (ResultSet rs = ps.executeQuery())
            {
                vocab = new ArrayList<Vocab>();
                while (rs.next())
                {
                    Vocab voc = new Vocab();
                    voc.setId(rs.getInt("vid"));
                    voc.setBook(rs.getInt("book"));
                    int lesson = rs.getInt("lesson");
                    voc.setLesson(lesson);
                    if (maxLesson == null || lesson > maxLesson)
                    {
                        maxLesson = lesson;
                    }
                    if (minLesson == null || lesson < minLesson)
                    {
                        minLesson = lesson;
                    }
                    voc.setType(VocabType.getByName(rs.getString("name")));
                    voc.setKana(rs.getString("kana"));
                    voc.setKanji(rs.getString("kanji"));
                    voc.setRomanji(rs.getString("romanji"));
                    voc.setEnglish(rs.getString("english"));
                    try (PreparedStatement pps = connection.prepareStatement("SELECT id, particle, object FROM vocab_particle WHERE fk_vocab = ?"))
                    {
                        pps.setInt(1, voc.getId());
                        try (ResultSet prs = pps.executeQuery())
                        {
                            while (prs.next())
                            {
                                Particle part = new Particle();
                                part.setId(prs.getInt("id"));
                                part.setParticle(prs.getString("particle"));
                                part.setObject(prs.getString("object"));
                                voc.addParticle(part);
                            }

                        }
                    }
                    vocab.add(voc);
                }
            }
        }
        return vocab;
    }

    private static Map<Integer, Kanji> loadKanji(Connection connection) throws SQLException
    {
        Map<Integer, Kanji> kanji = null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT id, book, lesson, kanji, strokes, meaning FROM kanji"))
        {
            try (ResultSet rs = ps.executeQuery())
            {
                kanji = new HashMap<Integer, Kanji>();
                while (rs.next())
                {
                    Kanji k = new Kanji();
                    k.setId(rs.getInt("id"));
                    k.setBook(rs.getInt("book"));
                    int lesson = rs.getInt("lesson");
                    k.setLesson(lesson);
                    if (maxLesson == null || lesson > maxLesson)
                    {
                        maxLesson = lesson;
                    }
                    if (minLesson == null || lesson < minLesson)
                    {
                        minLesson = lesson;
                    }
                    k.setKanji(rs.getString("kanji"));
                    k.setStrokes(rs.getInt("strokes"));
                    k.setMeaning(rs.getString("meaning"));
                    try (PreparedStatement pps = connection.prepareStatement("SELECT kanji_reading.id krid, name, kana, romanji FROM kanji_reading INNER JOIN reading_type ON kanji_reading.fk_reading_type = reading_type.id WHERE kanji_reading.fk_kanji = ?"))
                    {
                        pps.setInt(1, k.getId());
                        try (ResultSet prs = pps.executeQuery())
                        {
                            while (prs.next())
                            {
                                KanjiReading reading = new KanjiReading();
                                reading.setId(prs.getInt("krid"));
                                reading.setType(ReadingType.getByLabel(prs.getString("name")));
                                reading.setKana(prs.getString("kana"));
                                reading.setRomanji(prs.getString("romanji"));
                                k.addReading(reading);
                            }

                        }
                    }
                    kanji.put(k.getId(), k);
                }
            }
        }
        return kanji;
    }

    private static List<KanjiVocab> loadKanjiVocab(Connection connection) throws SQLException
    {
        Set<KanjiVocab> kanjiVocab = null;
        try (PreparedStatement ps = connection.prepareStatement("SELECT id, fk_kanji, kanji, kana, romanji, english FROM kanji_vocab INNER JOIN kanji_x_vocab ON kanji_vocab.id = kanji_x_vocab.fk_kanji_vocab"))
        {
            try (ResultSet rs = ps.executeQuery())
            {
                kanjiVocab = new HashSet<KanjiVocab>();
                while (rs.next())
                {
                    KanjiVocab kv = new KanjiVocab();
                    kv.setId(rs.getInt("id"));
                    kv.setParentKanji(kanji.get(rs.getInt("fk_kanji")));
                    kv.setKanji(rs.getString("kanji"));
                    kv.setKana(rs.getString("kana"));
                    kv.setRomanji(rs.getString("romanji"));
                    kv.setEnglish(rs.getString("english"));
                    kanjiVocab.add(kv);
                }
            }
        }
        return new ArrayList<KanjiVocab>(kanjiVocab);
    }

    public static List<GrammarResult> lookupGrammar(String query)
    {
        System.out.println("TODO - lookup grammar");
        return null;
    }

    public static List<VocabResult> lookupVocab(int minLesson, int maxLesson, List<VocabType> vocabTypes, String query)
    {
        List<VocabResult> results = new ArrayList<VocabResult>();
        for (Vocab v : getVocab())
        {
            // @formatter:off
            if (v.getLesson() >= minLesson && v.getLesson() <= maxLesson && vocabTypes.contains(v.getType()) && 
                (
                    v.getEnglish().toUpperCase().contains(query.toUpperCase()) || 
                    v.getKana().contains(query) || 
                    v.getKanji().contains(query) || 
                    v.getRomanji().toUpperCase().contains(query.toUpperCase()))
                )
            {
                results.add(v);
            }
            // @formatter:on
        }
        for (KanjiVocab kv : getKanjiVocab())
        {
            // @formatter:off
            if (kv.getLesson() >= minLesson && kv.getLesson() <= maxLesson && vocabTypes.contains(kv.getType()) &&
                ( 
                    kv.getEnglish().toUpperCase().contains(query.toUpperCase()) || 
                    kv.getKana().contains(query) || 
                    kv.getKanji().contains(query) || 
                    kv.getRomanji().toUpperCase().contains(query.toUpperCase()))
                )
            {
                results.add(kv);
            }
            // @formatter:on
        }
        return results;
    }

}
