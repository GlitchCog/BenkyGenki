package com.glitchcog.benkygenki;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.glitchcog.benkygenki.gui.BenkyGenkiWindow;
import com.glitchcog.benkygenki.gui.tab.Tabs;

/**
 * @author Matt Yanos
 */
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
            BenkyGenkiWindow window = new BenkyGenkiWindow("Benky Genki");
            Tabs tabs = new Tabs(window);
            window.setCopySource(tabs.getViewPanel());
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.add(tabs);
            window.setMinimumSize(new Dimension(480, 480));
            window.setSize(1024, 640);
            window.setVisible(true);
        }
    }
}
