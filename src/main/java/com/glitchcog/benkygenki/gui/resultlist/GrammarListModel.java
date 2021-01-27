package com.glitchcog.benkygenki.gui.resultlist;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.glitchcog.benkygenki.model.GrammarResult;

/**
 * @author Matt Yanos
 */
public class GrammarListModel extends DefaultTableModel
{
    private static final long serialVersionUID = 1L;

    protected static final String[] HEADER_VOCAB = new String[] { "L", "Japanese", "English" };

    private List<GrammarResult> results;

    public GrammarListModel()
    {
    }

    public void setResults(List<GrammarResult> results)
    {
        this.results = results;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if (columnIndex < 1)
        {
            return Integer.class;
        }
        else
        {
            return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex == 0;
    }

    @Override
    public int getRowCount()
    {
        return results == null ? 0 : results.size();
    }

    @Override
    public int getColumnCount()
    {
        return HEADER_VOCAB.length;
    }

    public GrammarResult getValueAt(int rowIndex)
    {
        return results.get(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (results != null)
        {
            GrammarResult r = results.get(rowIndex);
            if (columnIndex == 0)
            {
                return false;
            }
            else if (columnIndex == 1)
            {
                return r.getLesson();
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return HEADER_VOCAB[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
    }

}
