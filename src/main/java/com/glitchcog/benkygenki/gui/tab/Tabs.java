package com.glitchcog.benkygenki.gui.tab;

import java.awt.Frame;

import javax.swing.JTabbedPane;

import com.glitchcog.benkygenki.gui.view.ViewPanel;

/**
 * @author Matt Yanos
 */
public class Tabs extends JTabbedPane
{
    private static final long serialVersionUID = 1L;

    private TabBase[] tabs;

    private VocabTab vocabTab;

    private TabBase grammarTab;

    /**
     * 
     */
    public Tabs(Frame owner)
    {
        super(TOP, SCROLL_TAB_LAYOUT);
        build(owner);
        setEnabledAt(1, false); // TODO
    }

    public void build(Frame owner)
    {
        vocabTab = new VocabTab(owner);
        grammarTab = new GrammarTab();
        tabs = new TabBase[2];
        tabs[0] = vocabTab;
        tabs[1] = grammarTab;

        for (int i = 0; i < tabs.length; i++)
        {
            addTab(tabs[i].getLabel(), tabs[i]);
        }
    }

    public void toggleTab(TabBase tab)
    {
        int tabIndex = indexOfComponent(tab);
        if (tabIndex < 0)
        {
            addTab(tab.getLabel(), tab);
        }
        else
        {
            removeTabAt(tabIndex);
        }
    }

    public ViewPanel getViewPanel()
    {
        return vocabTab.getViewPanel();
    }
}
