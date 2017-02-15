package com.mycompany.jaddress;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public final class AddressBook {
    private static final AddressBook ADDRBOOK = new AddressBook(); //Singleton
    private final Set<AddressList> addresses = new HashSet<>();
    
    private AddressBook() {}
    
    public static AddressBook getInstance() {
        return AddressBook.ADDRBOOK;
    }
    
    public SortedSet<AddressList> sort(String args) {
        SortedSet<AddressList> ret = new TreeSet<>();
        this.addresses.forEach(list -> ret.add(list.sort(args)));
        return ret;
    }
    
    public boolean loadAddressList(String filename) throws IOException {
        if(filename == null || this.contains(filename) != null) {
            return true;
        }
        return this.addresses.add(FileHandler.load(filename));
    }
    
    private AddressList contains(String path) {
        Path p = Paths.get(path);
        for(AddressList a : addresses) {
            if(a != null && a.getPath().equals(p)) {
                return a;
            }
        }
        return null;
    }
    
    public boolean removeAddressList(String path) {
        AddressList ret = this.contains(path);
        if(ret == null) {
            return false;
        }
        return this.addresses.remove(ret);
    }
    
    /**
     * Removes address entry (with the given id) from given address list (stored in path)
     * @param path
     * @param id
     * @return true if index was contained in the given file, false otherwise
     */
    public boolean removeAddressEntry(String path, int id) {
        AddressList ret = this.contains(path);
        return (ret != null && ret.removeEntry(id));
    }
    
    public void addAddressEntry(String path, String firstName, String surname, String address, String email, String phone) {
        AddressList ret = this.contains(path);
        if(ret != null) {
            ret.addEntry(firstName, surname, address, email, phone);
        }
    }
    
    public AddressEntry getAddressEntry(String path, int id) {
        AddressList ret = this.contains(path);
        return ret == null ? null : ret.getEntry(id);
    }
    
    public void save() throws IOException {
        for(AddressList a : addresses) {
            FileHandler.save(a);
        }
    }
    
    public static String toString(Set<AddressList> set) {
        StringBuilder builder = new StringBuilder();
        for(AddressList a : set) {
            builder = builder.append(a.toString()).append('\n');
        }
        return builder.toString();
    }
    
    @Override
    public String toString() {
        return AddressBook.toString(this.addresses);
    }
}   
