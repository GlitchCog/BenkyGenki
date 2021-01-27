package com.glitchcog.benkygenki.gui.flash;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.glitchcog.benkygenki.gui.ColorButton;
import com.glitchcog.benkygenki.gui.view.ViewPanel;

public class FlashConfigPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    private static final String BL_KANA = "Kana";
    private static final String BL_KANJI = "Kanji";
    private static final String BL_ENGLISH = "English";
    private static final String BL_ROMANJI = "Romanji";
    private static final String BL_PARTICLE = "Particle";

    private static final String[] BOX_LABELS = new String[] { BL_KANA, BL_KANJI, BL_ENGLISH, BL_ROMANJI, BL_PARTICLE };

    private JCheckBox[] configBoxes;

    private ColorButton color;

    public FlashConfigPanel(Frame owner, String label, boolean sideBConfig, final ViewPanel vp)
    {
        setBorder(new TitledBorder(label));
        color = new ColorButton(owner, "Color", sideBConfig ? FlashCard.BG_COLOR : FlashCard.FG_COLOR, "Flash card side color", vp);
        setLayout(new GridLayout(BOX_LABELS.length + 1 / 2, 2));
        add(color);
        configBoxes = new JCheckBox[BOX_LABELS.length];

        ActionListener al = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                vp.updateFlashConfig();
            }
        };

        for (int i = 0; i < BOX_LABELS.length; i++)
        {
            configBoxes[i] = new JCheckBox(BOX_LABELS[i]);
            configBoxes[i].setSelected((sideBConfig && i == 2) || (!sideBConfig && i == 0));
            configBoxes[i].addActionListener(al);
            add(configBoxes[i]);
        }
    }

    public FlashConfig getFlashConfig()
    {
        return new FlashConfig(color.getColor(), configBoxes);
    }
}
