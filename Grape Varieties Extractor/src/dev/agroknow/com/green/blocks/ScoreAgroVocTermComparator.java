/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of version 3 of the GNU General Public License as published by
 * the Free Software Foundation, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 */
package dev.agroknow.com.green.blocks;

import java.util.Comparator;

/**
 *
 * @author Agroknow
 */
public class ScoreAgroVocTermComparator implements Comparator<RecordAgroVocTerm> {
    @Override
    public int compare(RecordAgroVocTerm o1, RecordAgroVocTerm o2) {
        if (o1.getScore() > o2.getScore()) return -1;
        if (o1.getScore() < o2.getScore()) return 1;
        return 0;
    }   
}
