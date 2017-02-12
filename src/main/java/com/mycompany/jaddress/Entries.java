/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaddress;

/**
 * The given ids represent ordering in the formatted output
 * @author Miloslav Zezulka, 2017
 */
public enum Entries {
    FIRST_NAME("first name",1),
    SURNAME("surname",2),
    EMAIL("email",3),
    ADDRESS("address",4),
    PHONE("phone",5);
    
    private final String name;
    private final int id;
    
    Entries(String name, int id) {
        this.name = name;
        this.id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getId() {
        return this.id;
    }
    
    /**
     * 
     * @param id
     * @return If id is not mapped to any value, null is returned.
     */
    public static Entries getEntry(int id) {
        switch(id) {
            case 1: return FIRST_NAME;
            case 2: return SURNAME;
            case 3: return EMAIL;
            case 4: return ADDRESS;
            case 5: return PHONE;
        }
        return null;
    }
    
}
