package com.glitchcog.benkygenki.gui.flash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.glitchcog.benkygenki.gui.BGUtils;

public class FlashDialog extends JDialog
{
    private static final long serialVersionUID = 1L;

    private FlashStack stack;
    private FlashCard card;

    private JButton nextButton;
    private JButton prevButton;

    private JSlider weightSlider;

    private static final Color BORDER_COLOR = Color.GRAY;

    public FlashDialog(Frame owner)
    {
        super(owner, true);
        getContentPane().setBackground(BORDER_COLOR);
        card = new FlashCard();
        card.setPreferredSize(new Dimension(1000000, 480));
        build(owner);
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setupHideOnEscape(this);
    }

    public void setStack(FlashStack stack)
    {
        this.stack = stack;
        loadCard(true);
        setTitle(this.stack.size() + " Flash Card" + (this.stack.size() == 1 ? "" : "s"));
    }

    private void build(Frame owner)
    {
        prevButton = new JButton(BGUtils.getTextPrev());
        prevButton.setPreferredSize(new Dimension(500, 140));
        prevButton.setOpaque(true);
        prevButton.setBackground(BORDER_COLOR);
        prevButton.setFont(BGUtils.getJapaneseFont(64.0f));

        nextButton = new JButton(BGUtils.getTextNext());
        nextButton.setPreferredSize(new Dimension(500, 140));
        nextButton.setOpaque(true);
        nextButton.setBackground(BORDER_COLOR);
        nextButton.setFont(BGUtils.getJapaneseFont(64.0f));

        ActionListener progressAl = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loadCard(e.getSource() == nextButton);
            }
        };

        prevButton.addActionListener(progressAl);
        nextButton.addActionListener(progressAl);

        weightSlider = new JSlider(0, 200, 100);
        weightSlider.setBackground(BORDER_COLOR);
        weightSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                double likelihood = getSliderToWeight(weightSlider.getValue());
                stack.setWeight(likelihood);
                int textWeight = (int) (likelihood * 100.0);
                card.setWeightText("LIKELIHOOD OF CARD REOCCURANCE: " + (textWeight == 0 ? "" + likelihood : textWeight) + "%");
                card.repaint();
            }
        });
        weightSlider.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                card.setWeightText(null);
            }
        });

        JLabel incorrectLabel = new JLabel(BGUtils.getTextIncorrect());
        incorrectLabel.setFont(BGUtils.getJapaneseFont(24));
        JLabel correctLabel = new JLabel(BGUtils.getTextCorrect());
        correctLabel.setFont(BGUtils.getJapaneseFont(24));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = BGUtils.getGbc();

        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1.0;
        gbc.weightx = 0.0;

        add(prevButton, gbc);
        gbc.gridx++;

        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridwidth = 1;
        add(incorrectLabel, gbc);
        gbc.gridx++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        add(weightSlider, gbc);
        gbc.gridx++;

        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        add(correctLabel, gbc);
        gbc.gridy++;
        gbc.gridx++;
        int holdGridX = gbc.gridx;

        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(card, gbc);

        gbc.gridx = holdGridX;
        gbc.gridy = 0;

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(nextButton, gbc);
    }

    private void loadCard(boolean dir)
    {
        if (card != null && stack.size() > 0)
        {
            stack.next(dir);
            stack.setContent(card);
            double rawWeight = stack.getWeight();
            int weight = getWeightToSlider(rawWeight);
            weightSlider.setValue(weight);
            card.setWeightText(null);
            card.repaint();
        }
    }

    private double getSliderToWeight(int value)
    {
        return Math.max(0.001, (weightSlider.getMaximum() - value + weightSlider.getMinimum()) / 100.0);
    }

    private int getWeightToSlider(double weight)
    {
        return Math.min(weightSlider.getMaximum(), Math.max(weightSlider.getMinimum(), (int) ((2.0 - weight + 0.001) * 100.0)));
    }

    /**
     * Escape stroke to close popups
     */
    private static final KeyStroke escapeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

    /**
     * Does the work required to make the parameter JDialog be hidden when pressing escape
     * 
     * @param popup
     */
    public static void setupHideOnEscape(final JDialog popup)
    {
        Action aa = new AbstractAction()
        {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent event)
            {
                popup.setVisible(false);
            }
        };
        final String mapKey = "escapePressed";
        JRootPane root = popup.getRootPane();
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeStroke, mapKey);
        root.getActionMap().put(mapKey, aa);
    }
}
