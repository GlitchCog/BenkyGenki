package com.glitchcog.benkygenki.gui.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.glitchcog.benkygenki.gui.BGUtils;
import com.glitchcog.benkygenki.gui.flash.FlashCard;
import com.glitchcog.benkygenki.gui.flash.FlashConfigPanel;
import com.glitchcog.benkygenki.model.VocabResult;

/**
 * The collection of VocabViews displayed in a list on the bottom portion of the vocab tab
 * 
 * @author Matt Yanos
 */
public class ViewPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    private LinkedBlockingQueue<VocabView> views;

    private JPanel content;

    private JScrollPane scroll;

    private JPanel fill;

    private List<VocabResult> results;

    private FlashCard previewCard;

    private FlashConfigPanel configPanelSideA;

    private FlashConfigPanel configPanelSideB;

    public ViewPanel(FlashCard previewCard)
    {
        this.previewCard = previewCard;
        this.results = new ArrayList<VocabResult>();
        this.fill = new JPanel();
        this.views = new LinkedBlockingQueue<VocabView>();
        content = new JPanel();
        scroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setLayout(new GridLayout(1, 1));
        add(scroll);
        refresh();
    }

    public void setConfigPanels(FlashConfigPanel configPanelSideA, FlashConfigPanel configPanelSideB)
    {
        this.configPanelSideA = configPanelSideA;
        this.configPanelSideB = configPanelSideB;
    }

    public void updateFlashConfig()
    {
        VocabResult result = selectedVocab == null ? null : selectedVocab.getVocabResult();
        previewCard.setSideA(configPanelSideA.getFlashConfig().getSide(result));
        previewCard.setSideB(configPanelSideB.getFlashConfig().getSide(result));
        previewCard.setColorA(configPanelSideA.getFlashConfig().getColor());
        previewCard.setColorB(configPanelSideB.getFlashConfig().getColor());
        previewCard.repaint();
    }

    private VocabView selectedVocab;

    public void setSelectedVocab(VocabView selectedVocab)
    {
        if (this.selectedVocab != null)
        {
            this.selectedVocab.setBackground(VocabView.BG_COLOR);
        }
        this.selectedVocab = selectedVocab;
        if (this.selectedVocab != null)
        {
            this.selectedVocab.setBackground(VocabView.SELECTED_COLOR);
        }
        updateFlashConfig();
    }

    private boolean matchSelectionExactly = false;

    public List<VocabResult> getResults()
    {
        return results;
    }

    public void syncView(List<VocabResult> results)
    {
        List<VocabView> updatedViews = resultsToViews(results);
        if (matchSelectionExactly)
        {
            views.clear();
            views.addAll(updatedViews);
        }
        else
        {
            for (VocabView vb : updatedViews)
            {
                if (views.contains(vb))
                {
                    views.remove(vb);
                }
                views.add(vb);
            }
        }
        refresh();
    }

    private List<VocabView> resultsToViews(List<VocabResult> results)
    {
        List<VocabView> views = new ArrayList<VocabView>();
        this.results.clear();
        for (VocabResult r : results)
        {
            this.results.add(r);
            views.add(new VocabView(r, this));
        }
        return views;
    }

    private void refresh()
    {
        for (Component c : content.getComponents())
        {
            content.remove(c);
        }

        content.setLayout(new GridBagLayout());
        int count = views.size();
        GridBagConstraints gbc = BGUtils.getGbc();
        gbc.ipady = 12;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weighty = 1.0;
        gbc.gridy = count--;
        content.add(fill, gbc);
        gbc.weighty = 0.0;
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        results.clear();
        for (VocabView view : views)
        {
            gbc.gridy = count--;
            content.add(view, gbc);
            results.add(view.getVocabResult());
        }

        scroll.revalidate();
        scroll.repaint();
    }

    public void reset()
    {
        views.clear();
        refresh();
    }
}
