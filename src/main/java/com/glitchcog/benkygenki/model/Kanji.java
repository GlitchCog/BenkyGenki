package com.glitchcog.benkygenki.model;

import java.util.ArrayList;
import java.util.List;

public class Kanji
{
    private int id;
    private int book;
    private int lesson;
    private String kanji;
    private int strokes;
    private String meaning;
    private List<KanjiReading> readings;
    private List<KanjiVocab> vocabs;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getBook()
    {
        return book;
    }

    public void setBook(int book)
    {
        this.book = book;
    }

    public int getLesson()
    {
        return lesson;
    }

    public void setLesson(int lesson)
    {
        this.lesson = lesson;
    }

    public String getKanji()
    {
        return kanji;
    }

    public void setKanji(String kanji)
    {
        this.kanji = kanji;
    }

    public int getStrokes()
    {
        return strokes;
    }

    public void setStrokes(int strokes)
    {
        this.strokes = strokes;
    }

    public String getMeaning()
    {
        return meaning;
    }

    public void setMeaning(String meaning)
    {
        this.meaning = meaning;
    }

    public boolean hasReadings()
    {
        return readings != null && !readings.isEmpty();
    }

    public void addReading(KanjiReading reading)
    {
        if (this.readings == null)
        {
            this.readings = new ArrayList<KanjiReading>();
        }
        this.readings.add(reading);
    }

    public List<KanjiReading> getReadings()
    {
        return readings;
    }

    public boolean hasVocab()
    {
        return vocabs != null && !vocabs.isEmpty();
    }

    public void addKanjiVocab(KanjiVocab vocab)
    {
        if (this.vocabs == null)
        {
            this.vocabs = new ArrayList<KanjiVocab>();
        }
        this.vocabs.add(vocab);
    }

    public List<KanjiVocab> getVocabs()
    {
        return vocabs;
    }
}