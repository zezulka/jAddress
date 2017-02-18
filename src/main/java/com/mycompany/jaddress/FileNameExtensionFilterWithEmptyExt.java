package com.mycompany.jaddress;

import java.io.File;
import java.util.Arrays;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Same functionality as in FileNameExtensionFilter, except files with no extensions are accepted as well. Decorator pattern used.
 * @author Miloslav Zezulka, 2017
 */
public class FileNameExtensionFilterWithEmptyExt extends FileFilter {

    private static final String[] DEFAULT_EXTENSIONS = new String[]{"txt"};
    private final FileNameExtensionFilter FILTER;
    
    private FileNameExtensionFilterWithEmptyExt(String... varargs) {
        String desc = "Files with no extension or: ";
        if(varargs.length == 0) {
            FILTER = new FileNameExtensionFilter(desc + Arrays.toString(DEFAULT_EXTENSIONS), DEFAULT_EXTENSIONS);
        } else {
            FILTER = new FileNameExtensionFilter(desc + Arrays.toString(varargs), varargs);
        }
    } 
    
    public static FileFilter getInstance(String... varargs) {
        return new FileNameExtensionFilterWithEmptyExt(varargs);
    }
    
    @Override
    public boolean accept(File file) {
        return FILTER.accept(file) || !file.getName().contains(".");
    }

    @Override
    public String getDescription() {
        return FILTER.getDescription();
    }
    
    
    
}
