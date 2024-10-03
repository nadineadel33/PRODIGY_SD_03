import java.io.*;
import java.util.*;

class Contact {
    String name;
    String phoneNumber;
    String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
    }
}

public class ContactManager {
    private static final String FILE_NAME = "contacts.txt";
    private List<Contact> contacts;

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContactsFromFile();
    }

    // Load contacts from the file
    private void loadContactsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load contacts: " + e.getMessage());
        }
    }

    // Save contacts to the file
    private void saveContactsToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts) {
                bw.write(contact.name + "," + contact.phoneNumber + "," + contact.email);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save contacts: " + e.getMessage());
        }
    }

    // Add a new contact
    public void addContact(String name, String phoneNumber, String email) {
        contacts.add(new Contact(name, phoneNumber, email));
        saveContactsToFile();
        System.out.println("Contact added: " + name);
    }

    // View all contacts
    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    // Edit an existing contact
    public void editContact(String oldName, String newName, String newPhoneNumber, String newEmail) {
        for (Contact contact : contacts) {
            if (contact.name.equalsIgnoreCase(oldName)) {
                contact.name = newName;
                contact.phoneNumber = newPhoneNumber;
                contact.email = newEmail;
                saveContactsToFile();
                System.out.println("Contact updated: " + newName);
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    // Delete a contact
    public void deleteContact(String name) {
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.name.equalsIgnoreCase(name)) {
                iterator.remove();
                saveContactsToFile();
                System.out.println("Contact deleted: " + name);
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactManager contactManager = new ContactManager();

        while (true) {
            System.out.println("\nContact Management System");
            System.out.println("1. Add New Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String email = scanner.nextLine();
                    contactManager.addContact(name, phoneNumber, email);
                    break;
                case 2:
                    contactManager.viewContacts();
                    break;
                case 3:
                    System.out.print("Enter the name of the contact to edit: ");
                    String oldName = scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email address: ");
                    String newEmail = scanner.nextLine();
                    contactManager.editContact(oldName, newName, newPhoneNumber, newEmail);
                    break;
                case 4:
                    System.out.print("Enter the name of the contact to delete: ");
                    String contactName = scanner.nextLine();
                    contactManager.deleteContact(contactName);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
