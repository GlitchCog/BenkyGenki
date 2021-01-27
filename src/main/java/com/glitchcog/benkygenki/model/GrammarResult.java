package com.glitchcog.benkygenki.model;

public class GrammarResult implements Comparable<GrammarResult>
{
    protected int id;

    private int book;

    private int lesson;

    protected String name;

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

    public int getLesson()
    {
        return lesson;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int compareTo(GrammarResult o)
    {
        if (getLesson() == o.getLesson())
        {
            return getName().compareTo(o.getName());
        }
        else
        {
            return Integer.valueOf(getLesson()).compareTo(Integer.valueOf(o.getLesson()));
        }
    }
}