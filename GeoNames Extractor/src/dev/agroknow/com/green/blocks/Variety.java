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

/**
 *
 * @author Agroknow
 */
public class Variety {
    
    private String name;
    private int oiv_id;

    public Variety(String name, int oiv_id) {
        this.name = name;
        this.oiv_id = oiv_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOiv_id() {
        return oiv_id;
    }

    public void setOiv_id(int oiv_id) {
        this.oiv_id = oiv_id;
    }
    
    
    
}
