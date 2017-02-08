package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;
import com.mycompany.jaddress.AddressEntry;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public class FirstNameAddressComparator extends SkeletonAddressComparator implements AddressComparator {

    public FirstNameAddressComparator(AddressComparator next) {
        super(next);
    }

    @Override
    public int compare(AddressEntry t, AddressEntry t1) {
        String s1 = t.getFirstName();
        String s2 = t1.getFirstName();
        if(s1 == null || s2 == null) {
            return this.getNext().compare(t, t1);
        }
        return s1.compareTo(s2);
    }
    
}
