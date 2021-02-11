package com.glitchcog.benkygenki.gui.tab;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.glitchcog.benkygenki.BenkyGenkiData;
import com.glitchcog.benkygenki.gui.BGUtils;
import com.glitchcog.benkygenki.gui.ColorButton;
import com.glitchcog.benkygenki.gui.RangeSlider;
import com.glitchcog.benkygenki.gui.flash.FlashCard;
import com.glitchcog.benkygenki.gui.flash.FlashConfigPanel;
import com.glitchcog.benkygenki.gui.flash.FlashDialog;
import com.glitchcog.benkygenki.gui.flash.FlashStack;
import com.glitchcog.benkygenki.gui.resultlist.VocabListModel;
import com.glitchcog.benkygenki.gui.resultlist.VocabResultPanel;
import com.glitchcog.benkygenki.gui.view.ViewPanel;
import com.glitchcog.benkygenki.model.VocabResult;
import com.glitchcog.benkygenki.model.VocabType;

/**
 * @author Matt Yanos
 */
public class VocabTab extends TabBase
{
    private static final long serialVersionUID = 1L;

    public VocabTab(Frame owner)
    {
        super("Vocabulary");
        typeBoxes = new ArrayList<JCheckBox>();
        build(owner, BenkyGenkiData.getMinLesson(), BenkyGenkiData.getMaxLesson());
        updateResults();
        viewPanel.reset();
    }

    private JLabel lessonLabel;

    private RangeSlider lessonSpanner;

    private JTextField filterInput;

    private VocabResultPanel resultPanel;

    private ViewPanel viewPanel;

    private VocabListModel resultsModel;

    private List<JCheckBox> typeBoxes;

    /**
     * The panel to the bottom right containing the flash card configuration, preview, and associated buttons, including
     * the clear button that empties the VocabPanel of all its VocabViews
     */
    private JPanel flashCardSetupPanel;

    private FlashDialog flashDialog;

    private FlashCard previewCard;

    private FlashConfigPanel configPanelSideA;

    private FlashConfigPanel configPanelSideB;

    public void build(final Frame owner, final int minLesson, final int maxLesson)
    {
        flashDialog = new FlashDialog(owner);
        resultsModel = new VocabListModel();
        resultPanel = new VocabResultPanel(resultsModel);
        previewCard = new FlashCard(24.0f);

        viewPanel = new ViewPanel(previewCard);
        ColorButton colorA = new ColorButton(owner, "COLOR", FlashCard.FG_COLOR, "Flash card side A color", viewPanel);
        ColorButton colorB = new ColorButton(owner, "COLOR", FlashCard.BG_COLOR, "Flash card side B color", viewPanel);
        colorA.setOtherButton(colorB);
        colorB.setOtherButton(colorA);
        configPanelSideA = new FlashConfigPanel(owner, "Side A", false, colorA, viewPanel);
        configPanelSideB = new FlashConfigPanel(owner, "Side B", true, colorB, viewPanel);
        viewPanel.setConfigPanels(configPanelSideA, configPanelSideB);

        resultPanel.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting())
                {
                    viewPanel.syncView(resultPanel.getSelectedResults());
                }
            }
        });

        filterInput = new JTextField();
        filterInput.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                updateResults();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                updateResults();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                updateResults();
            }
        });

        JPanel lessonPanel = new JPanel(new GridBagLayout());
        lessonSpanner = new RangeSlider(minLesson, maxLesson);
        lessonSpanner.setValue(minLesson);
        lessonSpanner.setUpperValue(maxLesson);
        lessonLabel = new JLabel(getLessonLabelValue())
        {
            private static final long serialVersionUID = 1L;

            @Override
            public Dimension getPreferredSize()
            {
                Dimension orig = super.getPreferredSize();
                return new Dimension(Math.max(110, orig.width), orig.height);
            }
        };
        lessonSpanner.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                lessonLabel.setText(getLessonLabelValue());
                updateResults();
            }
        });

        GridBagConstraints lpGbc = BGUtils.getGbc();
        lpGbc.fill = GridBagConstraints.NONE;
        lpGbc.anchor = GridBagConstraints.WEST;
        lpGbc.weightx = 0.0;
        lpGbc.gridx = 0;
        lessonPanel.add(lessonLabel, lpGbc);
        lpGbc.fill = GridBagConstraints.HORIZONTAL;
        lpGbc.anchor = GridBagConstraints.EAST;
        lpGbc.weightx = 1.0;
        lpGbc.gridx++;
        lessonPanel.add(lessonSpanner, lpGbc);

        JPanel typePanel = new JPanel();
        ActionListener tcbl = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                updateResults();
            }
        };
        for (VocabType type : VocabType.values())
        {
            JCheckBox tcb = new JCheckBox(type.getLabel());
            tcb.putClientProperty(VocabType.class, type);
            tcb.addActionListener(tcbl);
            tcb.setSelected(type.isDefaultChecked());
            typeBoxes.add(tcb);
            typePanel.add(tcb);
        }

        final JButton clearButton = new JButton("Clear");
        final JButton flashButton = new JButton("Flash");
        ActionListener fbl = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getSource() == clearButton)
                {
                    viewPanel.reset();
                    viewPanel.setSelectedVocab(null);
                    updateResults();
                }
                else if (e.getSource() == flashButton)
                {
                    if (viewPanel.getResults() == null || viewPanel.getResults().isEmpty())
                    {
                        JOptionPane.showMessageDialog(owner, "You must first select at least one result", "Requirement", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        FlashStack stack = new FlashStack(viewPanel.getResults(), configPanelSideA.getFlashConfig(), configPanelSideB.getFlashConfig());
                        flashDialog.setStack(stack);
                        // owner.setVisible(false);
                        flashDialog.setVisible(true);
                        // owner.setVisible(true);
                    }
                }
            }
        };
        clearButton.addActionListener(fbl);
        flashButton.addActionListener(fbl);

        JPanel configPanels = new JPanel(new GridBagLayout());
        GridBagConstraints cpGbc = BGUtils.getGbc();
        cpGbc.fill = GridBagConstraints.BOTH;
        cpGbc.anchor = GridBagConstraints.WEST;
        cpGbc.weightx = 0.3;
        cpGbc.weighty = 1.0;
        configPanels.add(new JPanel(), cpGbc);
        cpGbc.gridx++;
        cpGbc.weightx = 0.05;
        cpGbc.fill = GridBagConstraints.VERTICAL;
        configPanels.add(configPanelSideA, cpGbc);
        cpGbc.gridx++;
        cpGbc.anchor = GridBagConstraints.CENTER;
        cpGbc.fill = GridBagConstraints.BOTH;
        cpGbc.weightx = 0.3;
        configPanels.add(new JPanel(), cpGbc);
        cpGbc.gridx++;
        cpGbc.anchor = GridBagConstraints.EAST;
        cpGbc.fill = GridBagConstraints.VERTICAL;
        cpGbc.weightx = 0.05;
        configPanels.add(configPanelSideB, cpGbc);
        cpGbc.gridx++;
        cpGbc.fill = GridBagConstraints.BOTH;
        cpGbc.weightx = 0.3;
        cpGbc.weighty = 1.0;
        configPanels.add(new JPanel(), cpGbc);
        cpGbc.gridx++;

        flashCardSetupPanel = new JPanel(new GridBagLayout());
        GridBagConstraints fcspGbc = BGUtils.getGbc();
        fcspGbc.anchor = GridBagConstraints.NORTH;
        fcspGbc.fill = GridBagConstraints.HORIZONTAL;
        fcspGbc.weightx = 1.0;
        fcspGbc.weighty = 0.0;
        flashCardSetupPanel.add(new JLabel("Flash Card Preview: "), fcspGbc);
        fcspGbc.gridy++;
        fcspGbc.fill = GridBagConstraints.BOTH;
        fcspGbc.weightx = 1.0;
        fcspGbc.weighty = 1.0;
        flashCardSetupPanel.add(previewCard, fcspGbc);
        fcspGbc.gridy++;
        fcspGbc.weightx = 1.0;
        fcspGbc.weighty = 0.0;
        fcspGbc.anchor = GridBagConstraints.NORTH;
        fcspGbc.fill = GridBagConstraints.HORIZONTAL;
        flashCardSetupPanel.add(clearButton, fcspGbc);
        fcspGbc.gridy++;
        fcspGbc.fill = GridBagConstraints.NONE;
        fcspGbc.anchor = GridBagConstraints.CENTER;
        // flashCardSetupPanel.add(new JScrollPane(configPanels, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        // JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), fcspGbc);
        flashCardSetupPanel.add(configPanels, fcspGbc);
        fcspGbc.gridy++;
        fcspGbc.anchor = GridBagConstraints.SOUTH;
        fcspGbc.fill = GridBagConstraints.HORIZONTAL;
        flashCardSetupPanel.add(flashButton, fcspGbc);
        fcspGbc.gridy++;

        JSplitPane bottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewPanel, flashCardSetupPanel);
        bottomPane.setResizeWeight(1.0);

        GridBagConstraints gbc = BGUtils.getGbc();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0.0;
        gbc.gridy++;
        add(filterInput, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy++;
        add(lessonPanel, gbc);

        gbc.gridy++;
        gbc.weighty = 0.05;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane typePanelScroll = new JScrollPane(typePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        typePanelScroll.setMinimumSize(new Dimension(1, 36));
        add(typePanelScroll, gbc);

        gbc.gridy++;
        gbc.weighty = 0.95;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.BOTH;

        JSplitPane topBottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, resultPanel, bottomPane);
        topBottomSplitPane.setResizeWeight(0.5);

        add(topBottomSplitPane, gbc);
    }

    private String getLessonLabelValue()
    {
        int min = lessonSpanner.getValue();
        int max = lessonSpanner.getUpperValue();
        return "Lesson" + (min == max ? "" : "s") + " " + min + (min == max ? "" : "-" + max);
    }

    private void updateResults()
    {
        final String query = filterInput.getText();
        List<VocabType> selectedTypes = new ArrayList<VocabType>();
        for (JCheckBox tBox : typeBoxes)
        {
            if (tBox.isSelected())
            {
                selectedTypes.add((VocabType) tBox.getClientProperty(VocabType.class));
            }
        }
        List<VocabResult> results = BenkyGenkiData.lookupVocab(lessonSpanner.getValue(), lessonSpanner.getUpperValue(), selectedTypes, query);
        resultPanel.updateResults(results);
    }

    public ViewPanel getViewPanel()
    {
        return viewPanel;
    }
}
