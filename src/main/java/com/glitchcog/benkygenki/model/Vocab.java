package com.glitchcog.benkygenki.model;

import java.util.ArrayList;
import java.util.List;

public class Vocab extends VocabResult
{
    private int book;
    private int lesson;
    private String kana;
    private String kanji;
    private String romanji;
    private String english;
    private List<Particle> particles;

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

    public String getKana()
    {
        return kana;
    }

    @Override
    public String getJapanese()
    {
        return getKana();
    }

    public void setKana(String kana)
    {
        this.kana = kana;
    }

    public String getKanji()
    {
        return kanji;
    }

    public void setKanji(String kanji)
    {
        this.kanji = kanji;
    }

    public String getRomanji()
    {
        return romanji;
    }

    public void setRomanji(String romanji)
    {
        this.romanji = romanji;
    }

    @Override
    public String getEnglish()
    {
        return english;
    }

    public void setEnglish(String english)
    {
        this.english = english;
    }

    public boolean hasParticles()
    {
        return particles != null && !particles.isEmpty();
    }

    public void addParticle(Particle particle)
    {
        if (this.particles == null)
        {
            this.particles = new ArrayList<Particle>();
        }
        this.particles.add(particle);
    }

    public List<Particle> getParticles()
    {
        return particles;
    }

    public String getParticlesText()
    {
        String partStr = null;
        if (getParticles() != null && !getParticles().isEmpty())
        {
            partStr = "";
            for (Particle part : getParticles())
            {
                partStr += (partStr.isEmpty() ? "" : "\n") + (part.getObject() == null ? "" : " [" + part.getObject() + "] ") + part.getParticle();
            }
        }
        return partStr;
    }

    public String[] getParticlesTextLines()
    {
        return getParticlesText() == null || getParticlesText().isEmpty() ? new String[] {} : getParticlesText().split("\n");
    }

}