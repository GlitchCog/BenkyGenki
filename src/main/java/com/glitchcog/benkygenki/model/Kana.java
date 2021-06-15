package com.glitchcog.benkygenki.model;

public class Kana
{
    private int id;
    private String kana;
    private String sound;
    private KanaType type;
    private boolean dakuten;
    private boolean handakuten;
    private boolean chiisai;
    private boolean youon;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getKana()
    {
        return kana;
    }

    public void setKana(String kana)
    {
        this.kana = kana;
    }

    public String getSound()
    {
        return sound;
    }

    public void setSound(String sound)
    {
        this.sound = sound;
    }

    public KanaType getType()
    {
        return type;
    }

    public void setType(KanaType type)
    {
        this.type = type;
    }

    public boolean isDakuten()
    {
        return dakuten;
    }

    public void setDakuten(boolean dakuten)
    {
        this.dakuten = dakuten;
    }

    public boolean isHandakuten()
    {
        return handakuten;
    }

    public void setHandakuten(boolean handakuten)
    {
        this.handakuten = handakuten;
    }

    public boolean isChiisai()
    {
        return chiisai;
    }

    public void setChiisai(boolean chiisai)
    {
        this.chiisai = chiisai;
    }

    public boolean isYouon()
    {
        return youon;
    }

    public void setYouon(boolean youon)
    {
        this.youon = youon;
    }

    /**
     * This represents an exclusive selection, such that having a property will preclude your selection unless it is
     * specified. This means that not having a given criterion is inclusive by default.
     * 
     * @param selectedCriteria
     * @return whether the kana is selected given the specified criteria
     */
    public boolean isSelected(Kana selectedCriteria)
    {
        // @formatter:off
        return (selectedCriteria.isDakuten()    || !isDakuten()    ) 
            && (selectedCriteria.isHandakuten() || !isHandakuten() ) 
            && (selectedCriteria.isChiisai()    || !isChiisai()    ) 
            && (selectedCriteria.isYouon()      || !isYouon()      );
        // @formatter:on
    }
}