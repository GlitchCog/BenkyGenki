package com.glitchcog.benkygenki.model;

public abstract class VocabResult implements Comparable<VocabResult>
{
    protected int id;
    protected VocabType type;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public VocabType getType()
    {
        return type;
    }

    public void setType(VocabType type)
    {
        this.type = type;
    }

    public abstract int getBook();

    public abstract int getLesson();

    public abstract String getJapanese();

    public abstract String getEnglish();

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + getBook();
        result = prime * result + ((getEnglish() == null) ? 0 : getEnglish().hashCode());
        result = prime * result + id;
        result = prime * result + ((getJapanese() == null) ? 0 : getJapanese().hashCode());
        result = prime * result + getLesson();
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VocabResult other = (VocabResult) obj;
        if (getBook() != other.getBook())
            return false;
        if (getEnglish() == null)
        {
            if (other.getEnglish() != null)
                return false;
        }
        else if (!getEnglish().equals(other.getEnglish()))
            return false;
        if (id != other.id)
            return false;
        if (getJapanese() == null)
        {
            if (other.getJapanese() != null)
                return false;
        }
        else if (!getJapanese().equals(other.getJapanese()))
            return false;
        if (getLesson() != other.getLesson())
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public int compareTo(VocabResult o)
    {
        if (getLesson() == o.getLesson())
        {
            if (getType() == o.getType())
            {
                return getJapanese().compareTo(o.getJapanese());
            }
            else
            {
                return getType().name().compareTo(o.getType().name());
            }
        }
        else
        {
            return Integer.valueOf(getLesson()).compareTo(Integer.valueOf(o.getLesson()));
        }
    }
}