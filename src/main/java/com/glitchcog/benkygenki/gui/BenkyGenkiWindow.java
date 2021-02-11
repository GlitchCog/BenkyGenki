package com.glitchcog.benkygenki.gui;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

import com.glitchcog.benkygenki.gui.view.ViewPanel;
import com.glitchcog.benkygenki.model.KanjiVocab;
import com.glitchcog.benkygenki.model.Vocab;
import com.glitchcog.benkygenki.model.VocabResult;

/**
 * @author Matt Yanos
 */
public class BenkyGenkiWindow extends JFrame
{
    private static final long serialVersionUID = 1L;

    private final BenkyGenkiWindow me;

    private String helpMessage;

    private ViewPanel copySource;

    public BenkyGenkiWindow(String title)
    {
        super(title);
        this.me = this;
        constructAboutPopup();
        initMenus();
    }

    /**
     * Builds the menus
     */
    private void initMenus()
    {
        JMenuBar menuBar = new JMenuBar();

        final String[] mainMenuText = { "File", "Edit", "Help" };
        final int[] mainMnomonics = { KeyEvent.VK_F, KeyEvent.VK_E, KeyEvent.VK_H };

        JMenu[] menus = new JMenu[mainMenuText.length];

        for (int i = 0; i < mainMenuText.length; i++)
        {
            menus[i] = new JMenu(mainMenuText[i]);
            menus[i].setMnemonic(mainMnomonics[i]);
        }

        /* File Menu Item Text */
        final String strFileOpen = "Open Deck";
        final String strFileSave = "Save Deck";
        final String strFileExit = "Exit";
        // @formatter:off
        final MenuComponent[] fileComponents = new MenuComponent[] { 
            new MenuComponent(strFileOpen, KeyEvent.VK_O, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())), 
            new MenuComponent(strFileSave, KeyEvent.VK_S, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())), 
            new MenuComponent(strFileExit, KeyEvent.VK_X, KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()))
        };
        // @formatter:on

        /* Edit Menu Item Text */
        final String strCopy = "Copy Selected Vocab";
        final MenuComponent[] editComponents = new MenuComponent[] { new MenuComponent(strCopy, KeyEvent.VK_C, null) };

        /* Help Menu Item Text */
        final String strHelpHelp = "Help";
        final String strHelpAbout = "About";
        final MenuComponent[] helpComponents = new MenuComponent[] { new MenuComponent(strHelpHelp, KeyEvent.VK_H, null), null, new MenuComponent(strHelpAbout, KeyEvent.VK_A, null) };

        /* All menu components, with a placeholder for the Presets menu */
        final MenuComponent[][] allMenuComponents = new MenuComponent[][] { fileComponents, editComponents, helpComponents };

        ActionListener mal = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JMenuItem mi = (JMenuItem) e.getSource();
                if (strFileOpen.equals(mi.getText()))
                {
                    JOptionPane.showMessageDialog(me, "Feature not yet ready...");
                }
                else if (strFileSave.equals(mi.getText()))
                {
                    JOptionPane.showMessageDialog(me, "Feature not yet ready...");
                }
                else if (strFileExit.equals(mi.getText()))
                {
                    dispatchEvent(new WindowEvent(me, WindowEvent.WINDOW_CLOSING));
                }
                else if (strCopy.equals(mi.getText()))
                {
                    List<VocabResult> copyList = copySource.getResults();
                    if (copyList != null && !copyList.isEmpty())
                    {
                        final String copyText = buildCopyText(copyList);
                        StringSelection stringSelection = new StringSelection(copyText);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);
                    }
                }
                else if (strHelpHelp.equals(mi.getText()))
                {
                    if (helpMessage == null)
                    {
                        // @formatter:off
                        String[] helpMessageLines = new String[] {
                            "Use the text filter, lesson slider, and vocabulary types to find words.", 
                            "Click the desired words to add them to the bottom selected vocabulary list.", 
                            "Configure and run a flash card deck of the bottom word list with the bottom right options.", 
                            "The Clear button empties your selected vocabulary list.", 
                            "The Flash button runs a flash card deck popup."
                        };
                        // @formatter:on
                        helpMessage = "";
                        for (String line : helpMessageLines)
                        {
                            helpMessage += (helpMessage.isEmpty() ? "<html>" : "<br>") + line;
                        }
                        helpMessage += "</html>";
                    }
                    JOptionPane.showMessageDialog(me, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (strHelpAbout.equals(mi.getText()))
                {
                    showAboutPane();
                }
            }
        };

        /* Set all menu items but presets */
        JMenuItem item = null;
        for (int i = 0; i < allMenuComponents.length; i++)
        {
            for (int j = 0; j < allMenuComponents[i].length; j++)
            {
                MenuComponent mc = allMenuComponents[i][j];
                if (mc == null)
                {
                    menus[i].add(new JSeparator());
                }
                else
                {
                    item = mc.checkbox ? new JCheckBoxMenuItem(mc.label) : new JMenuItem(mc.label);
                    item.addActionListener(mal);
                    item.setMnemonic(mc.mnemonic);
                    if (mc.accelerator != null)
                    {
                        item.setAccelerator(mc.accelerator);
                    }
                    menus[i].add(item);
                }
            }
            menuBar.add(menus[i]);
        }

        for (int i = 0; i < menus.length; i++)
        {
            menuBar.add(menus[i]);
        }

        setJMenuBar(menuBar); // add the whole menu bar
    }

    private void showAboutPane()
    {
        JOptionPane.showMessageDialog(this, aboutPane, "About", JOptionPane.PLAIN_MESSAGE);
    }

    private JEditorPane aboutPane;

    // @formatter:off
    private static String ABOUT_CONTENTS = "<html><table bgcolor=#EEEEEE width=100% border=1><tr><td>" + 
        "<center><font face=\"Arial, Helvetica\"><b>Benky Genki</b> is a study aid<br />" + 
        "for the Genki Japanese series of textbooks.<br /><br />" + 
        "It is free, open source, and in the public domain to the furthest<br />" + 
        "extent I am permitted to forfeit my copyright over this software.<br /><br />" + 
        "Please enjoy!<br /><br />" + 
        "By Matt Yanos<br /><br />" + 
        "<a href=\"www.github.com/GlitchCog/BenkyGenki\">www.github.com/GlitchCog/BenkyGenki</a>" + 
        "</font></center>" + 
        "</td></tr></table></html>";
    // @formatter:on

    /**
     * Construct the popup dialog containing the About message
     */
    private void constructAboutPopup()
    {
        aboutPane = new JEditorPane("text/html", ABOUT_CONTENTS);
        aboutPane.addHyperlinkListener(new HyperlinkListener()
        {
            public void hyperlinkUpdate(HyperlinkEvent e)
            {
                if (EventType.ACTIVATED.equals(e.getEventType()))
                {
                    if (Desktop.isDesktopSupported())
                    {
                        try
                        {
                            Desktop.getDesktop().browse(URI.create("https://" + e.getDescription()));
                        }
                        catch (IOException e1)
                        {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        aboutPane.setEditable(false);
    }

    public void setCopySource(ViewPanel copySource)
    {
        this.copySource = copySource;
    }

    private String buildCopyText(List<VocabResult> copyList)
    {
        StringBuilder copyBuilder = new StringBuilder();
        int offset = 0;
        final String spacer = "\t";
        for (int i = 0; i < copyList.size(); i++)
        {
            copyBuilder.append((i - offset + 1) + spacer);
            VocabResult result = copyList.get(i);
            if (result instanceof Vocab)
            {
                Vocab v = ((Vocab) result);
                copyBuilder.append(v.getKana() + spacer + v.getKanji() + spacer + v.getEnglish() + spacer + v.getType() + (v.hasParticles() ? spacer + v.getParticlesText(false) : "") + "\n");
            }
            else if (result instanceof KanjiVocab)
            {
                KanjiVocab v = ((KanjiVocab) result);
                copyBuilder.append(v.getKanji() + spacer + v.getKana() + spacer + v.getEnglish() + "\n");
            }
            else
            {
                offset++;
            }
        }
        return copyBuilder.toString();
    }

}