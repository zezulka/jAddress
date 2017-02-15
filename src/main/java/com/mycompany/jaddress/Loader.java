package com.mycompany.jaddress;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public class Loader {

    private static final String MENU
            = "1) Load from file\n"
            + "2) Save to file\n"
            + "3) Add an entry\n"
            + "4) Remove an entry\n"
            + "5) Edit an existing entry\n"
            + "6) Sort the address book\n"
            + "7) Search for a specific entry\n"
            + "8) SHOW ALL SELECTED\n"
            + "9) Quit\n"
            + "\n"
            + "Please choose what you'd like to do with the database:\n";
    public static final String FILENAME_PROMPT = "Please, enter path to the address book:\n";
    public static final String ADDRESS_ID_PROMPT = "Please, enter id of the address entry you wish to select:\n";
    private static final ChooseFile CF = new ChooseFile();

    private static final AddressBook AB = AddressBook.getInstance();

    public static void main(String[] args) {

        while (true) {
            System.out.println(MENU);
            Scanner sc = new Scanner(System.in);
            try {
                if (Loader.parse(sc.nextInt()) == -1) {
                    break;
                }
            } catch (NumberFormatException nfe) {
                System.err.println("\nAn error has occured while parsing the input: \n" + nfe + "\n\n");
            } catch (IOException ex) {
                System.err.println("I/O error: " + ex);
            }

        }
        System.exit(0);
    }

    public static int parse(int i) throws IOException {
        Scanner sc = new Scanner(System.in);
        String filename;
        switch (i) {
            case 1: {
                Loader.getFilename();
                Loader.showAll();
                break;
            }
            case 2: {
                try {
                    AB.save();
                    System.out.println("SAVE OK\n\n");
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.err);
                }
                break;
            }
            case 3: {
                System.out.println("NEW ENTRY CREATION: \n");
                filename = Loader.getFilename();
                System.out.println("Please, enter values in this order:"
                );
                Stream.of(Entries.values()).forEach(e -> System.out.println(e.getName()));
                AB.addAddressEntry(filename,
                        sc.nextLine(), sc.nextLine(),
                        sc.nextLine(), sc.nextLine(),
                        sc.nextLine());
                Loader.showAll();
                break;
            }
            case 4: {
                filename = Loader.getFilename();
                Loader.showAll();
                System.out.println("Which entry do you wish to remove? " + Loader.ADDRESS_ID_PROMPT);
                if (AB.removeAddressEntry(filename, Integer.parseInt(sc.nextLine()))) {
                    System.out.println(AB.toString());
                } else {
                    System.err.println("Index given has not been found in '" + filename + "'.");
                }
                break;
            }
            case 5: {
                filename = Loader.getFilename();
                Loader.showAll();
                System.out.println("Which entry do you wish to edit?" + Loader.ADDRESS_ID_PROMPT);
                AddressEntry ae = AB.getAddressEntry(filename, Integer.parseInt(sc.nextLine()));
                if (ae == null) {
                    System.err.println("Index given has not been found in '" + filename + "'.");
                    break;
                }
                System.out.println("When you have finished editing entry, simply hit enter, editation will stop.");
                int attr = 0;
                while (attr >= 0) {
                    System.out.println("Enter attribute index and then its value:\n");
                    for(Entries e : Entries.values()) {
                        System.out.println(e.getName() + '(' + e.getId() + ")\n");
                    }
                    try {
                        attr = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException nfe) {
                        attr = -1;
                    }
                    ae.setVal(Entries.getEntry(attr), sc.nextLine()); 
                }
                break;
            }
            case 6: {
                System.out.println("Which attribute should this address book be sorted by? \n"
                        + "fsaep -> ('first name'/'surname'/'address'/'email'/'phone' \n In case you want to specify more attributes write the "
                        + "ones with highest priority first \n(e.g. 'fs' -> primary sort by first name, secondary by surname. \nNo matter which combination you choose,"
                        + "'id' is going to be used as the last attribute.");
                System.out.println(AddressBook.toString(AB.sort(sc.nextLine())));
                break;
            }
            case 7: {
                filename = Loader.getFilename();
                Loader.showAll();
                System.out.println("Which entry do you wish to find? Please, type index of the entry:\n");
                System.out.println(AB.getAddressEntry(filename, Integer.parseInt(sc.nextLine())));
                break;
            }
            case 8: {
                System.out.println(AB.toString());
            }
            case 9:
                return -1; //exit
        }
        return 0;
    }

    public static String getFilename() throws IOException {
        File chosenFile = Loader.CF.getFile();
        String in = chosenFile == null ? null : chosenFile.toString();
        AB.loadAddressList(in);
        return in;
    }
    
    private static void showAll() {
        System.out.println(AB.toString());
    }

}
