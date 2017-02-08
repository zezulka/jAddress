/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaddress;

import com.google.common.base.Preconditions;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class.
 * @author Miloslav Zezulka, 2017
 */
public class FileHandler {
    private static final char SEPARATOR = ';';
    
    private FileHandler() {}
    
    /**
     * Loads address list from the given filename and adds it to the collection
     * stored in AddressBook.
     * @param filename file to be written to
     * @throws IOException I/O error
     * @return read address list (unique entries only!)
     */
    public static AddressList load(String filename) throws IOException {
        AddressList result = new AddressList();
        try (FileReader fr = new FileReader(filename)) {
            result.setPath(Paths.get(filename));
            CSVReader reader = new CSVReader(fr, SEPARATOR);
            List<String[]> file = reader.readAll();
            for(Iterator<String[]> i = file.iterator(); i.hasNext();) {
                String[] row = i.next();
                if(row.length < AddressEntry.NUM_REQ_ARGS) {
                    throw new IllegalArgumentException(Arrays.toString(row) + " <- not enough values, therefore not valid entry");
                }
                result.addEntry(row);
            }
        }
        return result;
    }
    
    public static void save(String path, AddressList al) throws IOException {
        al.setPath(Paths.get(Preconditions.checkNotNull(path, "path cannot be null!")));
        FileHandler.save(al);
    }
    
    public static void save(AddressList al) throws IOException {
        if(al == null || al.getPath() == null) {
            throw new IllegalArgumentException("path to which the adrress list were to be saved is not available!");
        }
        
        try (FileWriter fw = new FileWriter(al.getPath().toFile())) {
            CSVWriter writer = new CSVWriter(fw, SEPARATOR);
            al.getList().forEach(item -> {
                writer.writeNext(item.getAllVals());
            });
        }
    }
}
