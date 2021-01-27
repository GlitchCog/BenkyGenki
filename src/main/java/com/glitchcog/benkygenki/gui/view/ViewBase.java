package com.glitchcog.benkygenki.gui.view;

import java.awt.Color;

import javax.swing.JPanel;

public abstract class ViewBase extends JPanel
{
    private static final long serialVersionUID = 1L;

    private int id;

    private final String label;

    private Color bgColor;

    public ViewBase(int id, String label, Color bgColor)
    {
        this.id = id;
        this.label = label;
        this.bgColor = bgColor;
    }

    public String getLabel()
    {
        return label;
    }

    public void setBgColor(Color bgColor)
    {
        this.bgColor = bgColor;
    }

    public Color getBgColor()
    {
        return bgColor;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
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
        ViewBase other = (ViewBase) obj;
        if (id != other.id)
            return false;
        if (label == null)
        {
            if (other.label != null)
                return false;
        }
        else if (!label.equals(other.label))
        {
            return false;
        }
        return true;
    }

}
