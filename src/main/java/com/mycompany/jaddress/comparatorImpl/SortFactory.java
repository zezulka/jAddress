package com.mycompany.jaddress.comparatorImpl;

import com.mycompany.jaddress.AddressComparator;

/**
 * Factory class that creates instances of comparators
 * Only public method is create, creates instance of comparators
 *
 * @author Miloslav Zezulka, 2017
 */
public class SortFactory {

    private static final SkeletonAddressComparator DEFAULT_COMPARATOR = new IdAddressComparator();

    /**
     * Static method that returns a decorated address comparator
     * Comparators are in specific order (param)
     *
     *
     * @param order string representing the order of comparators
     * @return comparator chain for given string
     */
    public static SkeletonAddressComparator create(String order) {
        SkeletonAddressComparator result = DEFAULT_COMPARATOR;

        for (int i = order.length() - 1; i >= 0; i--) {
            char c = order.charAt(i);
            result = yield(c, result);
        }

        return result;
    }

    private static SkeletonAddressComparator yield(char ch, AddressComparator nextComparator) {
        switch (ch) {
            case 'f':
                return new FirstNameAddressComparator(nextComparator);
            case 's':
                return new SurnameAddressComparator(nextComparator);
            case 'a':
                return new AddressAddressComparator(nextComparator);
            case 'e':
                return new EmailAddressComparator(nextComparator);
            case 'p':
                return new PhoneAddressComparator(nextComparator);
            default:
                return DEFAULT_COMPARATOR;
        }
    }
}
