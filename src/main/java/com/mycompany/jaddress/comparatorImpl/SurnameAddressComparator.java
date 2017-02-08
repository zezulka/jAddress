package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;
import com.mycompany.jaddress.AddressEntry;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public final class SurnameAddressComparator extends SkeletonAddressComparator implements AddressComparator  {

    public SurnameAddressComparator(AddressComparator next) {
        super(next);
    }
    
    @Override
    public int compare(AddressEntry t, AddressEntry t1) {
        String s1 = t.getSurname();
        String s2 = t1.getSurname();
        if(s1 == null || s2 == null) {
            return this.getNext().compare(t, t1);
        }
        return s1.compareTo(s2);
    }  
}
