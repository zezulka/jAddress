package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;
import com.mycompany.jaddress.AddressEntry;
import com.mycompany.jaddress.Entries;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public abstract class SkeletonAddressComparator implements AddressComparator {
    
    private final AddressComparator next;
    private final Entries ent;
    
    public SkeletonAddressComparator(AddressComparator next, Entries ent) {
        this.next = next;
        this.ent = ent;
    }
    
    @Override
    public AddressComparator getNext() {
        return this.next;
    }
    
    @Override
    public int compare(AddressEntry t, AddressEntry t1) {
        return this.compareWithEnt(t, t1, this.ent);
    }
    
    protected int compareWithEnt(AddressEntry t, AddressEntry t1, Entries ent) {
        String s1 = t.getVal(ent);
        String s2 = t1.getVal(ent);
        if(s1 == null || s2 == null) {
            return this.getNext().compare(t, t1);
        }
        return s1.compareTo(s2);
    }
}
