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
public class PhoneAddressComparator extends SkeletonAddressComparator implements AddressComparator {

    public PhoneAddressComparator(AddressComparator next) {
        super(next, Entries.PHONE);
    }
}
