package com.glitchcog.benkygenki.gui.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.border.StrokeBorder;

import com.glitchcog.benkygenki.gui.BGUtils;
import com.glitchcog.benkygenki.model.KanjiVocab;
import com.glitchcog.benkygenki.model.Vocab;
import com.glitchcog.benkygenki.model.VocabResult;

/**
 * Represents a single line of the selected vocab search results to be drawn on the bottom portion of the vocab tab
 * 
 * @author Matt Yanos
 */
public class VocabView extends ViewBase
{
    private static final long serialVersionUID = 1L;

    private VocabResult vr;

    private VocabView me;

    public static final Color BG_COLOR = new Color(238, 238, 238);

    public static final Color SELECTED_COLOR = new Color(255, 238, 238);

    public VocabView(VocabResult vocabResult, final ViewPanel viewPanel)
    {
        super(vocabResult.getId(), "Vocab", Color.WHITE);
        setBackground(BG_COLOR);
        me = this;
        setLayout(new GridLayout(1, 2));
        setBorder(new StrokeBorder(new BasicStroke(1)));
        this.vr = vocabResult;
        String textLeft;
        String textMid;
        String textRight;
        if (vr instanceof Vocab)
        {
            Vocab v = (Vocab) vr;
            if (v.getKana().equals(v.getKanji()))
            {
                textLeft = v.getKanji();
                textMid = "";
            }
            else
            {
                textLeft = v.getKana();
                textMid = v.getKanji();
            }
            textRight = v.getEnglish();
        }
        else if (vr instanceof KanjiVocab)
        {
            KanjiVocab kv = (KanjiVocab) vr;
            textLeft = kv.getKanji();
            textMid = kv.getKana();
            textRight = kv.getEnglish();
        }
        else
        {
            textLeft = "Unknown result type";
            textMid = "";
            textRight = "" + (vr == null ? "null obj" : vr.getClass());
        }
        JLabel labelLeft = new JLabel(textLeft, JLabel.LEFT);
        labelLeft.setFont(BGUtils.getJapaneseFont(24.0f));
        JLabel labelMiddle = new JLabel(textMid, JLabel.LEFT);
        labelMiddle.setFont(BGUtils.getJapaneseFont(24.0f));
        JLabel labelRight = new JLabel(textRight, JLabel.LEFT);
        labelRight.setFont(BGUtils.getJapaneseFont(16.0f));
        labelRight.setForeground(Color.GRAY);

        add(labelLeft);
        add(labelMiddle);
        add(labelRight);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                viewPanel.setSelectedVocab(me);
            }
        });
    }

    public VocabResult getVocabResult()
    {
        return vr;
    }
}
