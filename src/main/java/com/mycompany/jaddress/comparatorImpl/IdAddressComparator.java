/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;
import com.mycompany.jaddress.AddressEntry;

/**
 *
 * @author miloslav
 */
public class IdAddressComparator extends SkeletonAddressComparator implements AddressComparator {

    public IdAddressComparator() {
        super(null);
    }
    
    public IdAddressComparator(AddressComparator next) {
        super(next);
    }
    
    @Override
    public int compare(AddressEntry t, AddressEntry t1) {
        int result =  t.getId() - t1.getId();
        if(result != 0) {
            return result;
        }
        throw new IllegalStateException("This should not happen!");
        //return this.getNext().compare(t, t1);
    }
    
}
