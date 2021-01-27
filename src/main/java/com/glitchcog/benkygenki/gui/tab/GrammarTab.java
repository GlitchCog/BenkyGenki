package com.glitchcog.benkygenki.gui.tab;

import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.glitchcog.benkygenki.BenkyGenkiData;
import com.glitchcog.benkygenki.gui.BGUtils;
import com.glitchcog.benkygenki.gui.RangeSlider;
import com.glitchcog.benkygenki.gui.resultlist.GrammarListModel;
import com.glitchcog.benkygenki.gui.resultlist.GrammarResultPanel;
import com.glitchcog.benkygenki.model.GrammarResult;

/**
 * @author Matt Yanos
 */
public class GrammarTab extends TabBase
{
    private static final long serialVersionUID = 1L;

    public GrammarTab()
    {
        super("Grammar [Not Yet Ready...]");
        build(BenkyGenkiData.getMinLesson(), BenkyGenkiData.getMaxLesson());
    }

    private JLabel lessonRange;

    private RangeSlider lessonSpanner;

    private JTextField filterInput;

    final GrammarResultPanel resultPanel = new GrammarResultPanel(new GrammarListModel());

    public void build(final int minLesson, final int maxLesson)
    {
        GridBagConstraints gbc = BGUtils.getGbc();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;

        lessonSpanner = new RangeSlider(minLesson, maxLesson);
        lessonSpanner.setValue(minLesson);
        lessonSpanner.setUpperValue(maxLesson);

        lessonRange = new JLabel("Lesson " + lessonSpanner.getValue() + "-" + lessonSpanner.getUpperValue());
        add(lessonRange, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.9;
        gbc.gridx++;
        add(lessonSpanner, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(new JScrollPane(resultPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weighty = 0.0;
        gbc.gridy++;

        filterInput = new JTextField();
        filterInput.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                update(e);
            }

            private void update(DocumentEvent e)
            {
                final String query = filterInput.getText();
                List<GrammarResult> results = BenkyGenkiData.lookupGrammar(query);
                resultPanel.updateResults(results);
            }
        });

        add(filterInput, gbc);

    }
}
