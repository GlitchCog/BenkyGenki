package com.glitchcog.benkygenki.model;

public enum KanaType
{
    HIRAGANA("Hiragana", "ひらがな"), KATAKANA("Katakana", "カタカナ");

    private final String label;
    private final String kana;

    private KanaType(String label, String kana)
    {
        this.label = label;
        this.kana = kana;
    }

    public String getLabel()
    {
        return label;
    }

    public String getKana()
    {
        return kana;
    }

    public static KanaType getByName(String name)
    {
        for (KanaType t : KanaType.values())
        {
            if (t.toString().equals(name))
            {
                return t;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return getLabel();
    }
}
