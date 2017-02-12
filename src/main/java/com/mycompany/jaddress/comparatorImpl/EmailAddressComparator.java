package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;
import com.mycompany.jaddress.Entries;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public class EmailAddressComparator extends SkeletonAddressComparator implements AddressComparator {

    public EmailAddressComparator(AddressComparator next) {
        super(next, Entries.EMAIL);
    }
}
