import java.util.*;
import java.nio.file.*;
import java.io.*;

public class ContactsManager {
    private static final String FILE_NAME = "contacts.txt";
    private static final Path PATH = Paths.get(FILE_NAME);
    private List<Contact> contacts;

    public ContactsManager() {
        this.contacts = loadContacts();
    }

    private List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(PATH);
            for (String line : lines) {
                String[] parts = line.split(" \\| ");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String phoneNumber = formatPhoneNumber(parts[1].trim());
                    if (name.length() > 0 && phoneNumber != null) {
                        contacts.add(new Contact(name, phoneNumber));
                    } else {
                        System.out.println("Skipping invalid contact: " + line);
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read contacts. Starting with an empty list.");
        }
        return contacts;
    }

    public void start() {
        int option;
        do {
            option = showMenuOption();
            switch (option) {
                case 1:
                    showContacts();
                    break;
                case 2:
                    addContact();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    deleteContact();
                    break;
            }
        } while (option != 5);
        saveContacts();
    }

    private void addContact() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter name:");
            String name = scanner.nextLine();
            System.out.println("Enter phone number:");
            String phoneNumber = formatPhoneNumber(scanner.nextLine());
            if (phoneNumber == null) {
                System.out.println("Invalid phone number. Please enter a 7 or 10 digit number.");
                continue;
            }
            for (Contact contact : contacts) {
                if (contact.getName().equalsIgnoreCase(name)) {
                    System.out.println("Negative Ghost Rider, the contact is full. Do you want to overwrite it? (Yes/No)");
                    String answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("Yes")) {
                        contact.setPhoneNumber(phoneNumber);
                        System.out.println("Contact updated.");
                        saveContacts();
                        return;
                    } else {
                        System.out.println("Contact not updated.");
                    }
                    return;
                }
            }
            contacts.add(new Contact(name, phoneNumber));
            System.out.println("Contact added.");
            saveContacts();
            return;
        }
    }

    private void searchContact() {
        System.out.println("Enter the name of the contact to search:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                System.out.printf("%-20s | %s\n", contact.getName(), contact.getPhoneNumber());
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    private String formatPhoneNumber(String number) {
        // Remove all non-digits
        number = number.replaceAll("\\D", "");
        switch (number.length()) {
            case 7:
                return number.replaceAll("(\\d{3})(\\d{4})", "$1-$2");
            case 10:
                return number.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
            default:
                return null;
        }
    }

    private void saveContacts() {
        List<String> lines = new ArrayList<>();
        for (Contact contact : contacts) {
            lines.add(contact.toString());
        }
        try {
            Files.write(PATH, lines);
        } catch (IOException e) {
            System.out.println("Could not save contacts.");
        }
    }

        private void showContacts(){
                    System.out.printf("%-20s | %s\n", "Name", "Phone number");
                    System.out.println("---------------------|------");
                    for (Contact contact : contacts) {
                        System.out.printf("%-20s | %s\n", contact.getName(), contact.getPhoneNumber());
                    }
                }
        private int showMenuOption() {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println("\n--- Contacts Manager ---");
                System.out.println("1. View All Contacts");
                System.out.println("2. Add New Contact");
                System.out.println("3. Search Contact by Name");
                System.out.println("4. Delete an existing contact");
                System.out.println("5. Exit");
                System.out.println("Enter an option (1, 2, 3, 4 or 5):");
                try {
                    return scanner.nextInt();
                } catch (InputMismatchException e) {
                    scanner.next(); // discard the invalid input
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                }
            }
            return -1;
        }
        private void deleteContact() {
            System.out.println("\n---Delete Contact?---");
            System.out.println("Enter name to delete: ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            System.out.println("Are you sure you want to delete" + name + "? (Yes/No)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Yes")) {
                Iterator<Contact> iterator = contacts.iterator();
                while (iterator.hasNext()) {
                    Contact contact = iterator.next();
                    if (contact.getName().equalsIgnoreCase(name)) {
                        iterator.remove();
                        System.out.println("Bye Felicia.");
                        return;
                    }
                }
                System.out.println("Hmmm, not the droid you're looking for...");
            } else {
                System.out.println("Deletion cancelled.");
            }
        }
}
