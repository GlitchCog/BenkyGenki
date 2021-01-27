package com.glitchcog.benkygenki.model;

public class KanjiReading
{
    private int id;
    private ReadingType type;
    private String kana;
    private String romanji;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public ReadingType getType()
    {
        return type;
    }

    public void setType(ReadingType type)
    {
        this.type = type;
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
}