package com.glitchcog.benkygenki.gui.resultlist;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.glitchcog.benkygenki.model.VocabResult;

/**
 * @author Matt Yanos
 */
public class VocabListModel extends DefaultTableModel
{
    private static final long serialVersionUID = 1L;

    protected static final String[] HEADER_VOCAB = new String[] { "L", "Type", "Japanese", "English" };

    private List<VocabResult> results;

    public List<VocabResult> getResults()
    {
        return results;
    }

    public VocabListModel()
    {
    }

    public void setResults(List<VocabResult> results)
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

    public VocabResult getValueAt(int rowIndex)
    {
        return results.get(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (results != null)
        {
            if (rowIndex >= results.size())
            {
                System.err.println("Table error, looking for row " + rowIndex + " among only " + results.size() + " row" + (results.size() == 1 ? "" : "s"));
            }
            else
            {
                VocabResult r = results.get(rowIndex);
                if (columnIndex == 0)
                {
                    return r.getLesson();
                }
                else if (columnIndex == 1)
                {
                    return r.getType().getLabel();
                }
                else if (columnIndex == 2)
                {
                    return r.getJapanese();
                }
                else if (columnIndex == 3)
                {
                    return r.getEnglish();
                }
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return HEADER_VOCAB[columnIndex];
    }

}
