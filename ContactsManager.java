public class ContactsManager {

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       ContactList contactList = new ContactList();
boolean exit = false;
while(!exit) {
    System.out.println("\n--- Contacts Manager ---");
    System.out.println("1. View All Contacts");
    System.out.println("2. Add New Contact");
    System.out.println("3. Search Contact by Name");
    System.out.println("4. Delete and existing contact");
    System.out.println("0. Exit");

    System.out.println("Enter your choice: ");


    System.out.println("Name: " + contact.getName());
    System.out.println("Phone:" + contact.getPhoneNumber());
}else{
           System.out.println("Contact Not Found..");
       }


    }
    private static void deleteContact(Scanner scanner, ContactList contactList){
        System.out.println("\n---Delete Contact?---");
        System.out.println("Enter name to delete: ");
        System.out.println("Are you sure you want to delete?" + contact.getName());
        String name = scanner.nexLine();
        removed = contactList.deleteContactByName(contact.getName()); // check on this, it may be an error..**
    } // if and else here to check the name is deleted
if (removed)

    {
        System.out.println("Success");
    }else{
        System.out.println("Contact not found");
    }
}
