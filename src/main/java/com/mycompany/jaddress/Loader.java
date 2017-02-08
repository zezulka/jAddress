
package com.mycompany.jaddress;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
            + "8) Quit\n"
            + "\n"
            + "Please choose what you'd like to do with the database:\n";
    private static final String FILENAME_PROMPT = "Please, enter path to the address book:\n";
    private static final String ADDRESS_ID_PROMPT = "Please, enter id of the address entry you wish to select:\n";
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
                System.err.println("\nAn errror has occured while parsing the input: \n" + nfe + "\n\n");
            }

        }
        System.exit(0);
    }

    public static int parse(int i) {
        Scanner sc = new Scanner(System.in);
        switch (i) {
            case 1: {
                try {
                    AB.loadAddressList(Loader.getFilename().toString());
                    System.out.println(AB.toString());
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.err);
                }

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
                String in;
                try {
                    in = Loader.getFilename().toString();
                    AB.loadAddressList(in);
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.err);
                    break;
                }
                System.out.println("Please, enter values in this order:"
                        + "\n first name"
                        + "\n surname"
                        + "\n address"
                        + "\n email"
                        + "\n phone"
                        + "\n"
                );
                AB.addAddressEntry(in,
                        sc.nextLine(), sc.nextLine(),
                        sc.nextLine(), sc.nextLine(),
                        sc.nextLine());
                break;
            }
            case 4: {
                try {
                    String in = Loader.getFilename().toString();
                    AB.loadAddressList(in);
                    System.out.println(AB.toString());
                    System.out.println("Which entry do you wish to remove?" + Loader.ADDRESS_ID_PROMPT);
                    if (AB.removeAddressEntry(in, Integer.parseInt(sc.nextLine()))) {
                        System.out.println(AB.toString());
                    } else {
                        System.err.println("Index given has not been found in '" + in + "'.");
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.err);
                }
                break;
            }
            case 5: {
                try {
                    String in = Loader.getFilename().toString();
                    AB.loadAddressList(in);
                    System.out.println(AB.toString());
                    System.out.println("Which entry do you wish to edit?" + Loader.ADDRESS_ID_PROMPT);
                    AddressEntry ae = AB.getAddressEntry(in, Integer.parseInt(sc.nextLine()));
                    if (ae == null) {
                        System.err.println("Index given has not been found in '" + in + "'.");
                        break;
                    }
                    System.out.println("When you have finished editing entry, simply hit enter, editation will stop.");
                    String attr = "-";
                    while (!attr.equals("")) {
                        System.out.println("Enter attribute name ('first name'/'surname'/'address'/'email'/'phone') and then its value:");
                        attr = sc.nextLine();
                        switch (attr) {
                            case "":
                                break;
                            case "first name":
                                ae.setFirstName(sc.nextLine());
                                break;
                            case "surname":
                                ae.setSurname(sc.nextLine());
                                break;
                            case "address":
                                ae.setAddress(sc.nextLine());
                                break;
                            case "email":
                                ae.setEmail(sc.nextLine());
                                break;
                            case "phone":
                                ae.setPhone(sc.nextLine());
                                break;
                            default:
                                System.err.println(attr + " not found.");
                                break;
                        }
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.err);
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
                try {
                    String in = Loader.getFilename().toString();
                    AB.loadAddressList(in);
                    System.out.println("Which entry do you wish to find? Please, type index of the entry:\n");
                    System.out.println(AB.getAddressEntry(in, Integer.parseInt(sc.nextLine())));
                } catch (IOException ioe) {
                    ioe.printStackTrace(System.err);
                }
                break;
            }
            case 8:
                return -1; //exit
        }
        return 0;
    }

    public static File getFilename() {
        System.out.println(FILENAME_PROMPT);
        return Loader.CF.getFile();
    }
    
}
