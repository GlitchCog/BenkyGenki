package com.glitchcog.benkygenki.model;

public enum VocabType
{
    // @formatter:off
    NOUN("Noun"), 
    I_ADJ("い-Adj"), 
    NA_ADJ("な-Adj"), 
    U_VERB("う-Verb"), 
    RU_VERB("る-Verb"), 
    IRREG_VERB("Irregular Verb", "Irreg Verb"), 
    ADVERB("Adverb"), 
    EXPRESSION("Expression"), 
    LOCATION("Location Words", "Location", false), 
    POINT("Words that Point", "Pointer", false), 
    COUNTER("Counter", false), 
    KANJI("Kanji", false);
    // @formatter:on

    private final String name;
    private final String label;
    private final boolean defaultChecked;

    private VocabType(final String name)
    {
        this(name, true);
    }

    private VocabType(final String name, final boolean defaultChecked)
    {
        this(name, name, defaultChecked);
    }

    private VocabType(final String name, final String label)
    {
        this(name, label, true);
    }

    private VocabType(final String name, final String label, final boolean defaultChecked)
    {
        this.name = name;
        this.label = label;
        this.defaultChecked = defaultChecked;
    }

    public boolean isDefaultChecked()
    {
        return defaultChecked;
    }

    public String getLabel()
    {
        return label;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public static VocabType getByName(String name)
    {
        for (VocabType t : VocabType.values())
        {
            if (t.toString().equals(name))
            {
                return t;
            }
        }
        return null;
    }
}