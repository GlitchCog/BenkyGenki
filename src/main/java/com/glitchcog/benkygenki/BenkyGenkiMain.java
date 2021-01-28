package com.glitchcog.benkygenki;

import java.awt.Dimension;
import java.io.File;
import java.net.URLDecoder;

import javax.swing.JFrame;

import com.glitchcog.benkygenki.gui.tab.Tabs;

public class BenkyGenkiMain
{
    public static void main(String[] args) throws Exception
    {
        String databaseFilename = "benkygenki.db";
        if (args != null && args.length > 0)
        {
            databaseFilename = args[0];
        }
        else
        {
            if (!new File(databaseFilename).exists())
            {
                String path = BenkyGenkiMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                String decodedPath = URLDecoder.decode(path, "UTF-8");
                decodedPath = decodedPath.substring(0, path.lastIndexOf("/") + 1);
                databaseFilename = decodedPath + databaseFilename;
            }
        }
        BenkyGenkiData.loadData(databaseFilename);
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
