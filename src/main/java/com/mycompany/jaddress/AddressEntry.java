package com.mycompany.jaddress;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public final class AddressEntry implements Comparable<AddressEntry> {
    private static final String NOT_NULL = " cannot be null";
    public static final int NUM_REQ_ARGS = 6;
    private volatile int hashcode;
    private final int id;
    private final Map<Entries, String> vals = new HashMap<>();
    
    /**
     * Basic constructor, it does not set optional parameters (use setters instead)
     * @param firstName
     * @param surname
     * @param address
     * @param email
     * @param phone 
     * @param id
     * @throws NullPointerException if any of the parameters is null
     */
    public AddressEntry(int id, String firstName, String surname, String address, String email, String phone) {
        this.id = id;
        this.vals.put(Entries.FIRST_NAME, Preconditions.checkNotNull(firstName.trim(), "first name"+NOT_NULL));
        this.vals.put(Entries.SURNAME, Preconditions.checkNotNull(surname.trim(), "surname"+NOT_NULL));
        this.vals.put(Entries.ADDRESS, Preconditions.checkNotNull(address.trim(), "address"+NOT_NULL));
        Preconditions.checkArgument(checkEmailValid(email.trim()));
        Preconditions.checkArgument(checkPhoneValid(phone.trim()));
        this.vals.put(Entries.EMAIL, email);
        this.vals.put(Entries.PHONE, phone);
    }
    
    /**
     * Constructor, which has got address values as an array. This format is generated by
     * the opencsv library. Functionality same as the wordy constructor (provided the order in which
     * Strings are stored in the array is the same as in the wordy constructor!)
     * @param csvRow array of strings : {firstName, surname, address, email, phone}
     */
    public AddressEntry(String[] csvRow) {
        this(Integer.parseInt(csvRow[0]), csvRow[1], csvRow[2], csvRow[3], csvRow[4], csvRow[5]);
    }
    
    public Set<String> getAllAttrs() {
        return Stream.of(Entries.values()).map(e -> e.getName()).collect(Collectors.toSet());
    }
    
    /**
     * Sets value to the given key. In case key does
     * not exist, operation is not performed and false is returned.
     * @param key K
     * @param value V
     * @return 
     */
    public boolean setVal(Entries key, String value) {
        boolean exists = this.vals.containsKey(key);
        if(exists) {
            this.vals.put(key, value);
        }
        return exists;
    }
    
    public String getVal(Entries key) {
        return this.vals.get(key);
    }
    
    public int getId() {
        return this.id;
    }
    
    private static boolean checkEmailValid(String email) {
        return email == null? false : email.matches("^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" 
                             + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))?$");
    }
    
    private static boolean checkPhoneValid(String phone) {
        return phone == null ? false : phone.matches("^((\\+[0-9]{3}\\s)?[0-9]{3}(\\s[0-9]{3}){2})?$");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entries entry : this.vals.keySet()) {
            sb = sb.append(String.format("%-25s", this.vals.get(entry)));
        }
        return sb.toString();
    }
    
    /**
     * This implementation does not take id attribute into consideration, because
     * the only object having the same id must be the object itself. Furthermore,
     * skipping id attribute prevents same address entries from being added, even though
     * they have different id (=different lines in csv files with same values)
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) { //ae.id == this.id!
            return true;
        }
        if(!(obj instanceof AddressEntry)) {
            return false;
        }
        
        AddressEntry ae = (AddressEntry) obj;
        return this.vals.equals(ae.vals);
    }

    @Override
    public int hashCode() {
        int hash = hashcode;
        if(hash == 0) {
            hash = 17;
            for(String value : vals.values()) {
                hash = 31 * hash + value.hashCode();
            }
            hashcode = hash;
        }
        return hash;
    } 

    @Override
    public int compareTo(AddressEntry t) {
        return this.id - t.id;
    }
}
