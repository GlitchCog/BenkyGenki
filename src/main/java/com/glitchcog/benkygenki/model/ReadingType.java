package com.glitchcog.benkygenki.model;

public enum ReadingType
{
    ONYOMI("onyomi"), KUNYOMI("kunyomi");

    private final String label;

    private ReadingType(String label)
    {
        this.label = label;
    }

    @Override
    public String toString()
    {
        return label;
    }

    public static ReadingType getByLabel(String label)
    {
        for (ReadingType t : ReadingType.values())
        {
            if (t.toString().equals(label))
            {
                return t;
            }
        }
        return null;
    }
}