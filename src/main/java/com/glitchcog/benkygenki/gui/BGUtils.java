package com.glitchcog.benkygenki.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class BGUtils
{
    private static Font japaneseFont;

    static
    {
        try (InputStream fontInputStream = BGUtils.class.getClassLoader().getResourceAsStream("rounded-mgenplus-1c-bold.ttf"))
        {
            japaneseFont = Font.createFont(Font.TRUETYPE_FONT, fontInputStream);
        }
        catch (IOException | FontFormatException e)
        {
            japaneseFont = new Font(Font.DIALOG, Font.PLAIN, 24);
            e.printStackTrace();
        }
    }

    public static Font getJapaneseFont()
    {
        return japaneseFont;
    }

    public static Font getJapaneseFont(float size)
    {
        return japaneseFont.deriveFont(size);
    }

    public static GridBagConstraints getGbc()
    {
        return new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
    }

    public static Border getBaseBorder()
    {
        return BorderFactory.createLineBorder(Color.GRAY);
    }

    public static String getTextPrev()
    {
        return Character.toString((char) 8592);
    }

    public static String getTextNext()
    {
        return Character.toString((char) 8594);
    }

    public static String getTextCorrect()
    {
        return Character.toString((char) 0x2714);
    }

    public static String getTextIncorrect()
    {
        return Character.toString((char) 0x2717);
    }
}
