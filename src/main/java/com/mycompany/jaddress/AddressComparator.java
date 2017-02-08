package com.mycompany.jaddress;

import java.util.Comparator;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public interface AddressComparator extends Comparator<AddressEntry> {
    AddressComparator getNext();
}
