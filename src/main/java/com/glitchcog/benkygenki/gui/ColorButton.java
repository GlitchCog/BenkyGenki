package com.glitchcog.benkygenki.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

import com.glitchcog.benkygenki.gui.view.ViewPanel;

/**
 * Color Button is a GUI component containing a button that lets a user select a color from a JColorChooser pop-up, and
 * then changes the button color to display the selection
 * 
 * @author Matt Yanos
 */
public class ColorButton extends JPanel
{
    private static final long serialVersionUID = 1L;

    private ColorButton otherButton;

    /**
     * The button that displays the color
     */
    private JButton button;

    private JColorChooser chooser;

    public ColorButton(final Component parent, final String label, Color value, final String explanation, final ViewPanel viewPanel)
    {
        super();
        this.button = new JButton(label);
        this.button.setToolTipText("");
        setColor(value == null ? Color.BLACK : value);
        button.setBackground(value);

        // So the button color shows up on Mac OS
        button.setOpaque(true);
        button.setBorderPainted(false);

        add(this.button);

        this.button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Color color = JColorChooser.showDialog(parent, "Select Color" + (label == null ? "" : " for " + label), getColor());
                if (color != null)
                {
                    setColor(color);
                    if (otherButton != null)
                    {
                        otherButton.setOtherColor(color);
                    }
                    viewPanel.updateFlashConfig();
                }
            }
        });
    }

    /**
     * Get the selected color
     * 
     * @return color
     */
    public Color getColor()
    {
        return button.getBackground();
    }

    /**
     * Set the selected color
     * 
     * @param value
     */
    public void setColor(Color value)
    {
        button.setBackground(value);
    }

    public void setOtherColor(Color fgColor)
    {
        button.setForeground(fgColor);
    }

    public void addListener(PropertyChangeListener pcl)
    {
        chooser.addPropertyChangeListener(pcl);
    }

    public void setOtherButton(ColorButton otherButton)
    {
        this.otherButton = otherButton;
        setOtherColor(otherButton.getColor());
    }
}
