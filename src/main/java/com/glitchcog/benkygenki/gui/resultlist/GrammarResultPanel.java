package com.glitchcog.benkygenki.gui.resultlist;

import java.util.List;

import com.glitchcog.benkygenki.model.GrammarResult;

/**
 * @author Matt Yanos
 */
public class GrammarResultPanel extends ResultPanelBase
{
    private static final long serialVersionUID = 1L;

    private GrammarListModel model;

    public GrammarResultPanel(GrammarListModel model)
    {
        super(model);
        this.model = model;
    }

    public void updateResults(List<GrammarResult> results)
    {
        model.setResults(results);
        revalidateTable();
        model.fireTableDataChanged();
    }

    public GrammarResult getSelectedResult()
    {
        return model.getValueAt(resultsTable.getSelectedRow());
    }

}
