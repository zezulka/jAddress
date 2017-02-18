package com.mycompany.jaddress;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
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
    private static final Set<AddressList> ADDRESSES = new HashSet<>();
    
    private AddressBook() {}
    
    public static AddressBook getInstance() {
        return AddressBook.ADDRBOOK;
    }
    
    public SortedSet<AddressList> sort(String args) {
        SortedSet<AddressList> ret = new TreeSet<>();
        ADDRESSES.forEach(list -> ret.add(list.sort(args)));
        return ret;
    }
    
    /**
     * Loads address list stored in the file pointed by {@code filename}.
     * @param filename
     * @return false iff address list is only contained in this instance, true otherwise.
     * @throws IOException in case I/O error occurs
     */
    public boolean loadAddressList(String filename) throws IOException {
        if(filename == null || this.contains(filename) != null) {
            return true;
        }
        AddressList al = FileHandler.load(filename);
        return al.getNumEntries() == 0 ? true : ADDRESSES.add(al);
    }
    
    private AddressList contains(String path) {
        Path p = Paths.get(path);
        for(AddressList a : ADDRESSES) {
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
        return ADDRESSES.remove(ret);
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
        for(AddressList a : ADDRESSES) {
            FileHandler.save(a);
        }
    }
    
    public Set<AddressList> getAll() {
        return Collections.unmodifiableSet(ADDRESSES);
    }
    
    public String getAllListNames() {
        StringBuilder sb = new StringBuilder();
        for(AddressList a : ADDRESSES) {
            sb = sb.append(String.format("%-11s", Integer.toString(a.getId()))).append(a.getPath().toFile().getName()).append('\n');
        }
        return sb.toString();
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
        return AddressBook.toString(ADDRESSES);
    }
}   
