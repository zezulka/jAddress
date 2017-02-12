package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;
import com.mycompany.jaddress.Entries;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public final class SurnameAddressComparator extends SkeletonAddressComparator implements AddressComparator  {

    public SurnameAddressComparator(AddressComparator next) {
        super(next, Entries.SURNAME);
    }
}
