package com.glitchcog.benkygenki.gui.resultlist;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.glitchcog.benkygenki.gui.BGUtils;

/**
 * @author Matt Yanos
 */
public abstract class ResultPanelBase extends JPanel
{
    private static final long serialVersionUID = 1L;

    protected JTable resultsTable;

    private DefaultTableModel model;

    public ResultPanelBase(DefaultTableModel model)
    {
        this.model = model;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = BGUtils.getGbc();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        resultsTable = new JTable(this.model);
        resultsTable.setAutoCreateRowSorter(true);
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        add(new JScrollPane(resultsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), gbc);
        gbc.gridy++;
    }

    public void revalidateTable()
    {
        resultsTable.revalidate();
        resultsTable.repaint();
    }

    public void addListSelectionListener(ListSelectionListener lsl)
    {
        resultsTable.getSelectionModel().addListSelectionListener(lsl);
    }

}
