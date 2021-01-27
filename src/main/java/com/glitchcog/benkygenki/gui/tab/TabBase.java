package com.glitchcog.benkygenki.gui.tab;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

public abstract class TabBase extends JPanel
{
    private static final long serialVersionUID = 1L;

    private final String label;

    public TabBase(String label)
    {
        this.label = label;
        setLayout(new GridBagLayout());
    }

    public String getLabel()
    {
        return label;
    }
}
