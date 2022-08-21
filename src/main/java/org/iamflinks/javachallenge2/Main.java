package org.iamflinks.javachallenge2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ArrayList<Contact> contacts;
    private static Scanner scanner;
    private static int id =0;

    public static void main(String[] args) {

        /**
         * Simulate your phone's contacts and messages applications
         *
         * Greet the user
         * Show these 3 options: 1. Manage contacts  2. Messages   3. Quit
         * In case of selecting 1 --> show these options:
         *      1. Show all contacts
         *      2. Add new contact
         *      3. Search for a contact
         *      4. Delete a contact
         *      5. Go back to the previous menu
         * In case the user select 2 --> show this menu:
         *      1. See the list of all messages
         *      2. Send a new message
         *      3. Go back to the previous menu
         * In case of 3 --> Quit the application
         */

        contacts = new ArrayList<>();
        System.out.println("Welcome to my PhoneApp");
        showInitialInfo();

    }

    private static void showInitialInfo() {
        System.out.println("Please select an option: " +
                "\n\t1. Manage Contacts" + "\t2. Messaging" + "\t3. Quit the Application");
        scanner = new Scanner(System.in);

        int option = 0;
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Please select a valid choice.");
            showInitialInfo();
        }

        switch (option) {
            case 1 -> manageContacts();
            case 2 -> manageMessages();
            default -> {
            }
        }
    }

    private static void manageMessages() {
        System.out.println("PLease select one of the option below: " +
                "\n\t1. Show all messages" +
                "\n\t2. Send a new message" +
                "\n\t3. Previous menu");
        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Please select a valid choice.\n");
        }
        switch (choice) {
            case 1 -> showAllMessage();
            case 2 -> sendNewMessage();
            default -> showInitialInfo();
        }

    }

    private static void sendNewMessage() {
        System.out.print("Please enter the name of the recipient: ");
        String name = scanner.next();
        if (name.equals("")) {
            System.out.println("Please enter a valid name: ");
            sendNewMessage();
        } else {
            boolean isFound = false;
            for (Contact c: contacts) {
                if(c.getName().equals(name)) {
                    isFound = true;
                }
            }
            if (isFound) {
                System.out.print("Please enter the content of the message: ");
                String text = scanner.next();
                if (text.equals("")) {
                    System.out.println("Please enter a message: ");
                    sendNewMessage();
                } else  {
                    id++;
                    Message message = new Message(name, text, id);
                    for (Contact c: contacts) {
                        if (c.getName().equals(name)){
                            ArrayList<Message> newMessages = c.getMessages();
                            newMessages.add(message);
                            Contact currentContact = c;
                            currentContact.setMessages(newMessages);
                            contacts.remove(c);
                            contacts.add(currentContact);
                            System.out.println("Message sent successfully");
                        }
                    }
                }

            } else {
                System.out.println(name + " is not in your contact list");
            }
        }
        showInitialInfo();
    }

    private static void showAllMessage() {
        ArrayList<Message> allMessages = new ArrayList<>();
        for (Contact c: contacts) {
            allMessages.addAll(c.getMessages());
        }
        if (allMessages.size()>0) {
            for (Message m: allMessages) {
                m.getDetails();
                System.out.println("*****************************");
            }
        } else {
            System.out.println("The message list is empty.");
        }
        showInitialInfo();
    }

    private static void manageContacts() {
        System.out.println("Please select an option:" +
                "\n\t1. Show all contacts" +
                "\n\t2. Add new contact" +
                "\n\t3. Search for a contact" +
                "\n\t4. Delete a contact" +
                "\n\t5. Previous menu");

        int choice = 0;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Please select a valid choice.\n");
        }

        switch (choice) {
            case 1:
                showAllContact();
                break;

            case 2:
                addNewContact();
                break;
            case 3:
                searchContact();
            case 4:
                deleteContact();
            default:
                showInitialInfo();
                break;

        }
    }

    private static void deleteContact() {
        System.out.print("Please enter the contact name: ");
        String name = scanner.next();
        if (name.equals("")) {
            System.out.println("Please enter the contact name: ");
            deleteContact();
        } else {
            boolean isFound = false;
            Contact contact2Delete = null;
            for (Contact c: contacts) {
                if (c.getName().equals(name)) {
                    isFound = true;
                    contact2Delete = c;
                }
            }
            if (isFound) {
                contacts.remove(contact2Delete);
                System.out.println(name + " removed successfully");
            } else {
                System.out.println(name + " is not in the contact list.");
            }
        }
        showInitialInfo();
    }

    private static void searchContact() {
        System.out.print("Please enter the contact's name: ");
        String name = scanner.next();
        if (name.equals("")) {
            System.out.println("Please enter the name: ");
            searchContact();
        } else {
            boolean isFound = false;
            for (Contact c: contacts){
                if (c.getName().equals(name)) {
                    c.getDetails();
                    isFound = true;
                }
            }
            if (!isFound) {
                System.out.println(name + " is not in the contact list.");
            }
        }
        showInitialInfo();
    }

    private static void addNewContact() {
        System.out.println("Adding a new contact...");
        System.out.print("Please enter the contact's name: ");
        String name = scanner.next();
        System.out.print("Please enter the phone number: ");
        String number = scanner.next();
        System.out.print("Please enter the email: ");
        String email = scanner.next();

        if (name.equals("") || number.equals("") || email.equals("")) {
            System.out.println("One of the input is empty:");
            System.out.println("Please enter all of the information");
            addNewContact();
        } else {
            boolean isFound = false;
            for (Contact c: contacts) {
                if (c.getName().equals(name)) {
                    isFound = true;
                }
            }
            if (isFound) {
                System.out.println("A contact with Name: " + name + " is already in the contact list");
                addNewContact();
            } else {
                Contact contact = new Contact(name, number, email);
                contacts.add(contact);
                System.out.println(name + " added successfully");
            }
        }showInitialInfo();
    }

    private static void showAllContact() {
        if (contacts.isEmpty()) {
            System.out.println("The contact list is empty");
        } else {
            for (Contact c: contacts) {
                c.getDetails();
                System.out.println("*****************************");
            }
        }
        showInitialInfo();
    }
}
