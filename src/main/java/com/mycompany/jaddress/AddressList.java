/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaddress;

import com.google.common.base.Preconditions;
import com.mycompany.jaddress.comparatorImpl.SortFactory;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public class AddressList implements Comparable<AddressList> {
    private Path path;
    private volatile int hashcode;
    private final SortedSet<AddressEntry> list;
    private static final int LIMIT = 256;
    private final int id;
    
    public AddressList(int id) {
        this.id = id;
        this.list = new TreeSet<>();
    }
    
    public AddressList(int id, SortedSet<AddressEntry> list) {
        this.id = id;
        this.list = new TreeSet<>(list);
    }
    
    public SortedSet<AddressEntry> getList() {
        return Collections.unmodifiableSortedSet(this.list);
    }
    
    public int getNumEntries() {
        return this.list == null ? 0 : this.list.size();
    }
    
    public void addEntry(String[] csvRow) {
        this.addEntry(new AddressEntry(csvRow));
    }
    
    public void addEntry(String firstName, String surname, String address, String email, String phone) {
        this.addEntry(new AddressEntry(this.getFreeId(), firstName, surname, address, email, phone));
    }
    
    public void addEntry(AddressEntry ae) {
        Preconditions.checkArgument(this.getNumEntries() <= LIMIT, "maximum capacity reached! adding new elements not permittable");
        if(this.list.contains(ae)) {
            System.err.println("Entry with given key already contained in the list: " + ae.toString());
            return;
        }
        this.list.add(Preconditions.checkNotNull(ae, "address entry cannot be null!")); 
    }
    
    public AddressEntry getEntry(int id) {
        for(AddressEntry ae : this.list) {
            if(ae.getId() == id) {
                return ae;
            }
        }
        return null;
    }
    
    public boolean removeEntry(int id) {
        AddressEntry ae = this.getEntry(id);
        return this.list.remove(ae);
    }
    
    public Path getPath() {
        return this.path;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setPath(Path path) {
        this.path = path;
    }
    
    /**
     * Returns integer key which is not contained in the key set.
     * @return integer value 
     */
    private int getFreeId() {
        Random r = new Random();
        int guess = Math.abs(r.nextInt());
        while(true) {
            if(this.getEntry(guess) != null) { 
                guess = Math.abs(r.nextInt());
            } else {
                return guess;
            }
        }
    }
    
    public AddressList sort(String args) {
        SortedSet<AddressEntry> ret;
        if(args == null || "".equals(args)) { // no sort specified, therefore using the default one
            ret = new TreeSet<>(this.list);
        } else {
            ret = new TreeSet<>(SortFactory.create(args));
            ret.addAll(this.list);
        }
        return new AddressList(this.getId(), ret);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder = this.getPath() == null ? builder : builder.append("PATH: ").append(this.getPath().toString()).append("\nENTRIES:\n");
        for(AddressEntry ae : this.list) {
            builder = builder.append(ae.toString()).append('\n');
        }
        return builder.toString();
    }
    
    /**
     * Two address lists are equal iff they contain the same entries. More specifically,
     * this implementation does not take into consideration the path attribute, because
     * the only address list which can have the same path as another a.l. must be the address
     * list itself.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof AddressList)) {
            return false;
        }
        AddressList al = (AddressList) obj;
        return this.list.equals(al.list);
    } 

    @Override
    public int hashCode() {
        int hash = hashcode;
        if(this.list == null || this.list.isEmpty()) {
            return 17;
        }
        if(hashcode == 0) {
            hash = 17;
            hash = 31 * hash + this.list.hashCode();
            hashcode = hash;
        }
        return hash;
    }

    @Override
    public int compareTo(AddressList t) {
        if(this.getPath() == null) {
            return -1;
        }
        if(t == null || t.getPath() == null) {
            return 1;
        }
        return this.getPath().compareTo(this.path);
    }
}
