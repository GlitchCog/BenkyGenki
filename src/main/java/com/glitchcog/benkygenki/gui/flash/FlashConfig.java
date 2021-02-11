package com.glitchcog.benkygenki.gui.flash;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import com.glitchcog.benkygenki.BenkyGenkiData;
import com.glitchcog.benkygenki.model.KanjiVocab;
import com.glitchcog.benkygenki.model.Vocab;
import com.glitchcog.benkygenki.model.VocabResult;

public class FlashConfig
{
    private Color color;

    private final boolean kana;
    private final boolean kanji;
    private final boolean english;
    private final boolean romanji;
    private final boolean particle;
    private final boolean type;

    /**
     * Flag order: kana, kanji, english, romanji, particle, type
     * 
     * @param flags
     */
    public FlashConfig(Color color, JCheckBox[] flags)
    {
        this(color, flags[0].isSelected(), flags[1].isSelected(), flags[2].isSelected(), flags[3].isSelected(), flags[4].isSelected(), flags[5].isSelected());
    }

    public FlashConfig(final Color color, final boolean kana, final boolean kanji, final boolean english, final boolean romanji, final boolean particle, final boolean type)
    {
        this.color = color;
        this.kana = kana;
        this.kanji = kanji;
        this.english = english;
        this.romanji = romanji;
        this.particle = particle;
        this.type = type;
    }

    public String[] getSide(VocabResult vr)
    {
        List<String> sideLines = null;
        if (vr instanceof Vocab)
        {
            sideLines = new ArrayList<String>();
            Vocab v = (Vocab) vr;
            if (kana)
            {
                sideLines.add(v.getKana());
            }
            if (kanji && (!kana || !v.getKana().equals(v.getKanji())))
            {
                sideLines.add(v.getKanji());
            }
            if (english)
            {
                for (String line : v.getEnglish().split(";"))
                {
                    sideLines.add(line.trim());
                }
            }
            if (romanji)
            {
                sideLines.add(v.getRomanji());
            }
            if (particle && v.hasParticles())
            {
                for (String pt : v.getParticlesTextLines())
                {
                    sideLines.add(pt);
                }
            }
            if (type)
            {
                sideLines.add("" + vr.getType());
            }
        }
        else if (vr instanceof KanjiVocab)
        {
            sideLines = new ArrayList<String>();
            KanjiVocab kv = (KanjiVocab) vr;
            if (kana)
            {
                sideLines.add(kv.getKana());
            }
            if (kanji && (!kana || !kv.getKana().equals(kv.getKanji())))
            {
                sideLines.add(kv.getKanji());
            }
            if (english)
            {
                for (String line : kv.getEnglish().split(";"))
                {
                    sideLines.add(line.trim());
                }
            }
            if (romanji)
            {
                sideLines.add(kv.getRomanji());
            }
            if (particle)
            {
                String japaneseWord = kv.getKana();
                List<Vocab> allVocab = BenkyGenkiData.getVocab();
                for (Vocab v : allVocab)
                {
                    if (japaneseWord.equals(v.getKana()))
                    {
                        for (String pt : v.getParticlesTextLines())
                        {
                            sideLines.add(pt);
                        }
                        break;
                    }
                }
            }
            if (type)
            {
                sideLines.add("" + vr.getType());
            }
        }
        return sideLines == null ? new String[] {} : sideLines.toArray(new String[sideLines.size()]);
    }

    public Color getColor()
    {
        return color;
    }

    @Override
    public String toString()
    {
        return "kana=" + kana + ",kanji=" + kanji + ",english=" + english + ",romanji=" + romanji + ",particle=" + particle;
    }

}
