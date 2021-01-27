package com.glitchcog.benkygenki.gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import com.glitchcog.benkygenki.gui.BGUtils;
import com.glitchcog.benkygenki.model.GrammarResult;

public class GrammarView extends ViewBase
{
    private static final long serialVersionUID = 1L;

    private GrammarResult gr;

    public GrammarView(GrammarResult gr, final ViewPanel viewPanel)
    {
        super(gr.getId(), "Grammar", Color.GRAY.brighter());
        this.gr = gr;
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getBgColor());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setFont(BGUtils.getJapaneseFont());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawString(gr.getName(), 20, 20);
        g2d.drawRect(0, 0, getWidth(), 200);
    }
}
