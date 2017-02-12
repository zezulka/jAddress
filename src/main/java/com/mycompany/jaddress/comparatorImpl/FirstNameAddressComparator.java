package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;
import com.mycompany.jaddress.Entries;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public class FirstNameAddressComparator extends SkeletonAddressComparator implements AddressComparator {

    public FirstNameAddressComparator(AddressComparator next) {
        super(next, Entries.FIRST_NAME);
    }
}
