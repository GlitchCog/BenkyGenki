package com.glitchcog.benkygenki.model;

public class KanjiVocab extends VocabResult
{
    private Kanji parentKanji;
    private String kanji;
    private String kana;
    private String romanji;
    private String english;

    @Override
    public VocabType getType()
    {
        return type == null ? VocabType.KANJI : type;
    }

    public Kanji getParentKanji()
    {
        return parentKanji;
    }

    public void setParentKanji(Kanji parentKanji)
    {
        parentKanji.addKanjiVocab(this);
        this.parentKanji = parentKanji;
    }

    public String getKanji()
    {
        return kanji;
    }

    public void setKanji(String kanji)
    {
        this.kanji = kanji;
    }

    public String getKana()
    {
        return kana;
    }

    public void setKana(String kana)
    {
        this.kana = kana;
    }

    public String getRomanji()
    {
        return romanji;
    }

    public void setRomanji(String romanji)
    {
        this.romanji = romanji;
    }

    public String getEnglish()
    {
        return english;
    }

    public void setEnglish(String english)
    {
        this.english = english;
    }

    @Override
    public int getBook()
    {
        return getParentKanji() == null ? 0 : getParentKanji().getBook();
    }

    @Override
    public int getLesson()
    {
        return getParentKanji() == null ? 0 : getParentKanji().getLesson();
    }

    @Override
    public String getJapanese()
    {
        return kanji + " (" + kana + ")";
    }
}