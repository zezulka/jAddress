package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public abstract class SkeletonAddressComparator implements AddressComparator {
    
    private final AddressComparator next;
    
    public SkeletonAddressComparator(AddressComparator next) {
        this.next = next;
    }
    
    @Override
    public AddressComparator getNext() {
        return this.next;
    }
}
