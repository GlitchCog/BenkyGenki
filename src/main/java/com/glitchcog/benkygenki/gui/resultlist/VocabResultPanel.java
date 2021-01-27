package com.glitchcog.benkygenki.gui.resultlist;

import java.util.ArrayList;
import java.util.List;

import com.glitchcog.benkygenki.model.VocabResult;

/**
 * @author Matt Yanos
 */
public class VocabResultPanel extends ResultPanelBase
{
    private static final long serialVersionUID = 1L;

    private VocabListModel model;

    public VocabResultPanel(VocabListModel model)
    {
        super(model);
        this.model = model;
        resultsTable.getColumnModel().getColumn(0).setMaxWidth(32);
        resultsTable.getColumnModel().getColumn(1).setMaxWidth(72);
    }

    public void updateResults(List<VocabResult> results)
    {
        model.setResults(results);
        revalidateTable();
        model.fireTableDataChanged();
    }

    public List<VocabResult> getSelectedResults()
    {
        int[] selectedRows = resultsTable.getSelectedRows();
        List<VocabResult> selectedResults = new ArrayList<VocabResult>();
        for (int i : selectedRows)
        {
            selectedResults.add(model.getValueAt(resultsTable.convertRowIndexToModel(i)));
        }
        return selectedResults;
    }

}
