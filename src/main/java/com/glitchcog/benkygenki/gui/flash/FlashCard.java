package com.glitchcog.benkygenki.gui.flash;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import com.glitchcog.benkygenki.gui.BGUtils;

public class FlashCard extends JPanel
{
    private static final long serialVersionUID = 1L;

    private boolean toggle;

    public static final Color BG_COLOR = new Color(32, 32, 32);
    public static final Color FG_COLOR = new Color(220, 220, 220);

    private Color bgColor = BG_COLOR;
    private Color fgColor = FG_COLOR;

    private Font font;
    private Font weightFont;

    private Queue<String> sideA;
    private Queue<String> sideB;

    /**
     * null if it shouldn't be shown
     */
    private String weightText;

    public FlashCard()
    {
        this(72.0f);
    }

    public FlashCard(float initFontSize)
    {
        this.font = BGUtils.getJapaneseFont(initFontSize);
        this.weightFont = new Font(Font.DIALOG, Font.PLAIN, 14);
        this.sideA = new LinkedBlockingQueue<String>();
        this.sideB = new LinkedBlockingQueue<String>();
        MouseInputAdapter mia = new MouseInputAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                toggle = true;
                repaint();
            }

            public void mouseReleased(MouseEvent e)
            {
                toggle = false;
                repaint();
            }

            private static final int MIN_FONT_SIZE = 12;
            private static final int MAX_FONT_SIZE = 256;

            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                int delta = e.getWheelRotation();
                int size = font.getSize() - delta;
                if (size < MIN_FONT_SIZE)
                {
                    size = MIN_FONT_SIZE;
                }
                else if (size > MAX_FONT_SIZE)
                {
                    size = MAX_FONT_SIZE;
                }
                font = BGUtils.getJapaneseFont(size);
                repaint();
            }
        };
        addMouseListener(mia);
        addMouseWheelListener(mia);
    }

    public void setWeightText(String weightText)
    {
        this.weightText = weightText;
    }

    public void setColorA(Color bgColor)
    {
        this.bgColor = bgColor;
    }

    public void setColorB(Color fgColor)
    {
        this.fgColor = fgColor;
    }

    public void setSideA(String... sides)
    {
        setSide(sideA, sides);
    }

    public void setSideB(String... sides)
    {
        setSide(sideB, sides);
    }

    private void setSide(Queue<String> sideQueue, String... sides)
    {
        sideQueue.clear();
        for (String sa : sides)
        {
            sideQueue.add(sa);
        }
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final Queue<String> lines = toggle ? sideB : sideA;
        g2d.setFont(font);

        g2d.setColor(toggle ? fgColor : bgColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(toggle ? bgColor : fgColor);

        Integer initY = null;
        int y = 0;
        for (String line : lines)
        {
            Rectangle2D r = g2d.getFontMetrics().getStringBounds(line, g2d);
            if (initY == null)
            {
                // Assumes all lines are the same height
                initY = (int) (getHeight() - (r.getHeight() * lines.size())) / 2;
                y = initY;
            }
            int x = (int) (getWidth() - r.getWidth()) / 2;
            y += r.getHeight();
            g2d.drawString(line, x, y - g2d.getFontMetrics().getDescent());
        }

        if (weightText != null)
        {
            g2d.setFont(weightFont);
            Rectangle2D weightTextBounds = g2d.getFontMetrics().getStringBounds(weightText, g2d);
            g2d.drawString(weightText, (int) (getWidth() - weightTextBounds.getWidth()) / 2, (int) weightTextBounds.getHeight());
        }
    }

}
