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
 * @author Miloslav Zezulka, 2017
 */
public class AddressAddressComparator extends SkeletonAddressComparator implements AddressComparator {

    public AddressAddressComparator(AddressComparator next) {
        super(next);
    }

    @Override
    public int compare(AddressEntry t, AddressEntry t1) {
        String s1 = t.getAddress();
        String s2 = t1.getAddress();
        if(s1 == null || s2 == null) {
            return this.getNext().compare(t, t1);
        }
        return s1.compareTo(s2);
    }
    
}
