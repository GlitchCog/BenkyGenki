package com.glitchcog.benkygenki.gui.flash;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.glitchcog.benkygenki.gui.BGUtils;
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
    private static final String BL_TYPE = "Type";

    private static final String[] BOX_LABELS = new String[] { BL_KANA, BL_KANJI, BL_ENGLISH, BL_ROMANJI, BL_PARTICLE, BL_TYPE };

    private JCheckBox[] configBoxes;

    private ColorButton color;

    public FlashConfigPanel(Frame owner, String label, boolean sideBConfig, ColorButton color, final ViewPanel vp)
    {
        this.color = color;
        setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1), label, TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = BGUtils.getGbc();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 2;
        add(color, gbc);
        gbc.gridy++;

        configBoxes = new JCheckBox[BOX_LABELS.length];
        ActionListener al = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                vp.updateFlashConfig();
            }
        };

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        for (int i = 0; i < BOX_LABELS.length; i++)
        {
            gbc.gridx = i % 2;
            configBoxes[i] = new JCheckBox(BOX_LABELS[i]);
            configBoxes[i].setSelected((sideBConfig && i == 2) || (!sideBConfig && i == 0));
            configBoxes[i].addActionListener(al);
            add(configBoxes[i], gbc);
            gbc.gridy += i % 2 == 0 ? 0 : 1;
        }
    }

    public FlashConfig getFlashConfig()
    {
        return new FlashConfig(color.getColor(), configBoxes);
    }
}
