package com.glitchcog.benkygenki.gui.flash;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.glitchcog.benkygenki.model.VocabResult;

public class FlashStack
{
    private List<VocabResult> data;

    /**
     * The current card index to be displayed
     */
    private int index;

    /**
     * The index on the spentCard list
     */
    private int spentIndex;

    /**
     * The list of cards viewed
     */
    private Deque<Integer> spentCards;

    /**
     * How many calls to prev have expanded the left end of the spent cards list into the negative
     */
    private int spentPrevOffset;

    /**
     * User input card weights
     */
    private Map<Integer, Double> weights;

    private FlashConfig configSideA;
    private FlashConfig configSideB;

    public FlashStack(List<VocabResult> data, FlashConfig configSideA, FlashConfig configSideB)
    {
        this.data = data;
        this.configSideA = configSideA;
        this.configSideB = configSideB;
        this.spentIndex = 0;
        this.spentPrevOffset = spentIndex;
        weights = new HashMap<Integer, Double>(data.size());
        for (int i = 0; i < data.size(); i++)
        {
            weights.put(i, 1.0);
        }
        spentCards = new LinkedList<Integer>();
    }

    public int size()
    {
        return data == null ? 0 : data.size();
    }

    public void setContent(FlashCard card)
    {
        card.setSideA(getSideA());
        card.setSideB(getSideB());
        card.setColorA(configSideA.getColor());
        card.setColorB(configSideB.getColor());
    }

    private String[] getSideA()
    {
        return getContent(configSideA);
    }

    private String[] getSideB()
    {
        return getContent(configSideB);
    }

    public void next()
    {
        next(true);
    }

    public void setWeight(double value)
    {
        weights.put(index, value);
    }

    public void next(boolean dir)
    {
        if (dir) // next
        {
            if (spentIndex + spentPrevOffset >= spentCards.size())
            {
                index = getWeightedRandomValue(weights);
                spentCards.addLast(index);
                spentIndex++;
            }
            else
            {
                spentIndex++;
                index = getSpentCard(spentIndex + spentPrevOffset);
            }
        }
        else // prev
        {
            if (spentIndex + spentPrevOffset <= 1)
            {
                spentPrevOffset++;
                spentIndex--;
                index = getWeightedRandomValue(weights);
                spentCards.addFirst(index);
            }
            else
            {
                spentIndex--;
                index = getSpentCard(spentIndex + spentPrevOffset);
            }
        }
    }

    private int getSpentCard(int i)
    {
        for (int cid : spentCards)
        {
            if (--i <= 0)
            {
                return cid;
            }
        }
        return -1;
    }

    protected void printSpentCards()
    {
        for (int cid : spentCards)
        {
            String cids = "" + cid;
            while (cids.length() < 2)
            {
                cids = " " + cids;
            }
            System.out.print(cids + " ");
        }
        System.out.println();
        for (int i = 0; i < spentIndex + spentPrevOffset - 1; i++)
        {
            System.out.print("   ");
        }
        System.out.println(" ^");
    }

    private String[] getContent(FlashConfig config)
    {
        String[] side = config.getSide(data.get(index));
        return side;
    }

    public static Integer getWeightedRandomValue(Map<Integer, Double> weightedIndicies)
    {
        double totalWeight = 0.0;
        for (Double weight : weightedIndicies.values())
        {
            totalWeight += weight;
        }
        double randomValue = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;
        for (Entry<Integer, Double> e : weightedIndicies.entrySet())
        {
            cumulativeWeight += e.getValue();
            if (cumulativeWeight >= randomValue)
            {
                return e.getKey();
            }
        }
        return null;
    }

    public double getWeight()
    {
        return weights.get(index);
    }

}
