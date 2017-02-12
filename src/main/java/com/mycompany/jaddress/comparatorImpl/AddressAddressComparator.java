/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;
import com.mycompany.jaddress.Entries;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public class AddressAddressComparator extends SkeletonAddressComparator implements AddressComparator {

    public AddressAddressComparator(AddressComparator next) {
        super(next, Entries.ADDRESS);
    }
}
