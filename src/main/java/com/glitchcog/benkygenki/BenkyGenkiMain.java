package com.glitchcog.benkygenki;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.glitchcog.benkygenki.gui.tab.Tabs;

public class BenkyGenkiMain
{
    public static void main(String[] args)
    {
        String databaseFilename = "benkygenki.db";
        if (args != null && args.length > 0)
        {
            databaseFilename = args[0];
        }
        else
        {
            databaseFilename = ":resource:" + BenkyGenkiData.class.getResource("/benkygenki.db");
        }

        boolean dataLoadSuccess = BenkyGenkiData.loadData(databaseFilename);
        if (dataLoadSuccess)
        {
            JFrame window = new JFrame("Benky Genki");
            Tabs tabs = new Tabs(window);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.add(tabs);
            window.pack();
            window.setMinimumSize(new Dimension(window.getWidth(), window.getHeight() / 2));
            window.setSize(1024, 768);
            window.setVisible(true);
        }
    }

}
