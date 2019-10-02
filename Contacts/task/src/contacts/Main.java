package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    private static final String ADD = "add";

    private static final String LIST = "list";

    private static final String SEARCH = "search";

    private static final String DELETE = "delete";

    private static final String EDIT = "edit";

    private static final String COUNT = "count";

    private static final String EXIT = "exit";

    private static final String PERSON = "person";

    private static final String ORGANIZATION = "organization";

    private static final String NAME = "name";

    private static final String SURNAME = "surname";

    private static final String NUMBER = "number";

    private static final String ADDRESS = "address";

    private static final String BIRTH = "birth";

    private static final String GENDER = "gender";

    private static final String WRONG_FORMATER = "Wrong number format!";

    private static final String DEFAULT_NUMBER = "[no number]";

    private static final String BAD_DATE = "Bad birth date!";

    private static final String NO_DATA = "[no data]";

    private static final String BAD_GENDER = "Bad gender!";

    private static final String BACK = "back";

    private static final String AGAIN = "again";

    private static final String MENU = "menu";

    private static List<Base> phoneBook;

    public static void main(String[] args) {
        phoneBook = new ArrayList<>();

        action();
    }

    private static void action() {
        System.out.println("[menu] Enter action (add, list, search, count, exit):");
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();

        switch (value) {
            case ADD: {
                add();
                System.out.println("\n");
                action();
                break;
            }

            case LIST: {
                list();
                System.out.println("\n");
                nextStepSearch(phoneBook);
                break;
            }

            case SEARCH: {
                startSearch();
                System.out.println("\n");
                break;
            }

            case COUNT: {
                count();
                System.out.println("\n");
                action();
                break;
            }

            case EXIT: {
                System.out.println("\n");
                break;
            }

            default: {
                System.out.println("Erro Filho");
                System.out.println("\n");
                action();
                break;
            }
        }
    }

    private static void startSearch() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter search query:");
        String text = scanner.nextLine();

        List<Base> queryBase = serarchByName(text);
        printList(queryBase);
        System.out.println("\n");
        nextStepSearch(queryBase);
    }

    private static List<Base> serarchByName(String query) {
        List<Base> newList = new ArrayList<>();
        for (Base item : phoneBook) {
            if (item.getName().toLowerCase().contains(query.toLowerCase()) || item.getPhone().toLowerCase().contains(query.toLowerCase())) {
                newList.add(item);
            }
        }

        for (Base item : phoneBook){
            if(item.getPerson()){
                User user = (User) item;
                if(user.getSurname().toLowerCase().contains(query.toLowerCase())){
                    newList.add(item);
                }
            }
        }
        return newList;
    }

    private static void printList(List<Base> list) {
        System.out.println("Found " + list.size() + " results:");
        int x = 1;
        for (Base base : list) {
            base.printBase(x);
            x++;
        }
    }

    private static void nextStepSearch(List<Base> list) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("[search] Enter Action: ([number], back, again):");
        String text = scanner.nextLine();

        switch (text) {
            case BACK: {
                action();
                break;
            }

            case AGAIN: {
                startSearch();
                break;
            }

            default: {
                if (verifyNumber(text)) {
                    int number = Integer.parseInt(text);
                    list.get(number - 1).info();
                    System.out.println("\n");
                    record(list.get(number - 1));
                } else {
                    System.out.println("error try again");
                    System.out.println("\n");
                    nextStepSearch(list);
                }
                break;
            }
        }
    }

    private static void record(Base entity) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("[record] Enter action (edit, delete, menu):");
        String action = scanner.nextLine();

        switch (action) {
            case DELETE: {
                remove(entity);
                break;
            }

            case EDIT: {
                if (entity instanceof User) {
                    editPerson(entity);
                } else {
                    editOrg(entity);
                }
                break;
            }

            case MENU: {
                action();
                break;
            }
        }
    }

    private static boolean verifyNumber(String text) {
        try {
            Integer integer = Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void count() {
        System.out.println("The Phone Book has " + phoneBook.size() + " records.");
    }

    private static void add() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enther the type (person, organization):");
        String option = scanner.nextLine();

        switch (option) {
            case PERSON: {
                addUser();
                break;
            }
            case ORGANIZATION: {
                addOrganization();
                break;
            }
            default: {
                break;
            }
        }
    }

    private static void addUser() {
        Scanner scanner = new Scanner(System.in);

        User newUser = new User();

        System.out.println("Enter the name");
        String name = scanner.nextLine();
        newUser.setName(name);

        System.out.println("Enter the surename");
        String surname = scanner.nextLine();
        newUser.setSurname(surname);

        System.out.println("Enter the birth date");
        String localDate = scanner.nextLine();

        try {
            LocalDate localDate1 = LocalDate.parse(localDate);
            newUser.setBirthday(localDate1);
        } catch (Exception e) {
            System.out.println(BAD_DATE);
            newUser.setBirthday(null);
        }

        System.out.println("Enter the gender (M, F):");
        String gender = scanner.nextLine();

        try {
            newUser.setGender(getGender(Gender.valueOf(gender)));
        } catch (Exception e) {
            newUser.setGender(null);
            System.out.println(BAD_GENDER);
        }


        System.out.println("Enter the number:");
        String phone = scanner.nextLine();

        if (isValid(phone)) {
            newUser.setPhone(phone);
        } else {
            newUser.setPhone(DEFAULT_NUMBER);
        }

        newUser.setPerson(true);
        newUser.setCreate(LocalDateTime.now());
        newUser.setEdit(LocalDateTime.now());
        phoneBook.add(newUser);

        System.out.println("The record added.");
    }

    private static void addOrganization() {

        Scanner scanner = new Scanner(System.in);

        Organization org = new Organization();

        System.out.println("Enter the organization name:");
        String name = scanner.nextLine();
        org.setName(name);

        System.out.println("Enter the address:");
        String address = scanner.nextLine();
        org.setAddress(address);

        System.out.println("Enter the number:");
        String phone = scanner.nextLine();

        if (isValid(phone)) {
            org.setPhone(phone);
        } else {
            org.setPhone(DEFAULT_NUMBER);
        }

        org.setPerson(false);
        org.setCreate(LocalDateTime.now());
        org.setEdit(LocalDateTime.now());
        phoneBook.add(org);
        System.out.println("The record added.");
    }

    private static void remove(Base entity){
        phoneBook.remove(entity);
        System.out.println("\n");
        action();
    }

    private static void list() {
        int x = 1;
        for (Base item : phoneBook) {
            System.out.println(x + ". " + item.getName());
            x++;
        }
    }

    private static void editPerson(Base user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a field (name, surname, birth, gender, number");
        String option = scanner.nextLine();

        switch (option) {
            case NAME: {
                editName(user);
                break;
            }

            case SURNAME: {
                editPersonSurname(user);
                break;
            }

            case BIRTH: {
                editPersonBirth(user);
                break;
            }

            case GENDER: {
                editPersonGender(user);
                break;
            }

            case NUMBER: {
                editPhone(user);
                break;
            }

            default: {
                break;
            }
        }
    }

    private static void editName(Base entity) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name:");
        String name = scanner.nextLine();

        int index = phoneBook.indexOf(entity);
        phoneBook.get(index).setName(name);
        phoneBook.get(index).setEdit(LocalDateTime.now());
        System.out.println("Saved");
        phoneBook.get(index).info();
        System.out.println("\n");
        record(phoneBook.get(index));
    }

    private static void editPersonSurname(Base entity) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Surname:");
        String surname = scanner.nextLine();

        int index = phoneBook.indexOf(entity);

        User user = (User) phoneBook.get(index);

        user.setSurname(surname);
        user.setEdit(LocalDateTime.now());

        phoneBook.remove(index);
        phoneBook.add(index, user);

        System.out.println("Saved");
        phoneBook.get(index).info();
        System.out.println("\n");
        record(phoneBook.get(index));
    }

    private static void editPersonBirth(Base entity) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the birth date");
        String localDate = scanner.nextLine();

        int index = phoneBook.indexOf(entity);
        User user = (User) phoneBook.get(index);

        try {
            LocalDate localDate1 = LocalDate.parse(localDate);
            user.setBirthday(localDate1);
        } catch (Exception e) {
            System.out.println(BAD_DATE);
            user.setBirthday(null);
        }


        user.setEdit(LocalDateTime.now());

        phoneBook.remove(index);
        phoneBook.add(index, user);

        System.out.println("Saved");
        phoneBook.get(index).info();
        System.out.println("\n");
        record(phoneBook.get(index));
    }

    private static void editPersonGender(Base entity) {
        Scanner scanner = new Scanner(System.in);
        int index = phoneBook.indexOf(entity);

        User user = (User) phoneBook.get(index);

        System.out.println("Enter the gender (M, F):");
        String gender = scanner.nextLine();

        try {
            user.setGender(getGender(Gender.valueOf(gender)));
        } catch (Exception e) {
            user.setGender(null);
            System.out.println(BAD_GENDER);
        }


        user.setEdit(LocalDateTime.now());

        phoneBook.remove(index);
        phoneBook.add(index, user);

        System.out.println("Saved");
        phoneBook.get(index).info();
        System.out.println("\n");
        record(phoneBook.get(index));
    }

    private static void editPhone(Base entity) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number:");
        String phone = scanner.nextLine();

        int index = phoneBook.indexOf(entity);

        if (isValid(phone)) {
            phoneBook.get(index).setPhone(phone);
        } else {
            phoneBook.get(index).setPhone(DEFAULT_NUMBER);
        }
        phoneBook.get(index).setEdit(LocalDateTime.now());
        System.out.println("Saved");
        phoneBook.get(index).info();
        System.out.println("\n");
        record(phoneBook.get(index));
    }

    private static void editOrgAddress(Base entity) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the address:");
        String address = scanner.nextLine();

        int index = phoneBook.indexOf(entity);

        Organization org = (Organization) phoneBook.get(index);

        org.setAddress(address);
        org.setEdit(LocalDateTime.now());

        phoneBook.remove(index);
        phoneBook.add(index, org);

        System.out.println("Saved");
        phoneBook.get(index).info();
        System.out.println("\n");
        record(phoneBook.get(index));
    }

    private static void editOrg(Base org) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a field (name, address, number):");
        String option = scanner.nextLine();
        switch (option) {
            case NAME: {
                editName(org);
                break;
            }

            case ADDRESS: {
                editOrgAddress(org);
                break;
            }

            case NUMBER: {
                editPhone(org);
                break;
            }

            default: {
                break;
            }
        }
    }

    private static Gender getGender(Gender gender) {


        switch (gender) {
            case M: {
                return Gender.M;
            }

            case F: {
                return Gender.F;
            }

            default: {
                System.out.println(BAD_GENDER);
                return null;
            }
        }
    }

    private static boolean isValid(String phone) {
        String[] phoneList = phone.split("[\\s\\-]");

        boolean result1 = true;
        boolean result2 = true;
        boolean result3 = true;
        boolean result4 = true;
        int qntd = 0;
        if (phoneList.length >= 1) {

            if (phoneList.length == 1) {
                Pattern pattern = Pattern.compile("^\\+?\\w*");
                Matcher matcher1 = pattern.matcher(phoneList[0]);
                result1 = matcher1.find();
            } else {
                Pattern pattern = Pattern.compile("^\\+?(?<block1>\\w{1,4}|\\(\\w{1,4}\\))$");
                Matcher matcher1 = pattern.matcher(phoneList[0]);
                result1 = matcher1.find();
            }

            if (phoneList[0].contains("(")) {
                qntd++;
            }
        }

        if (phoneList.length >= 2) {
            Pattern pattern = Pattern.compile("^(?<block1>\\w{2,4}|\\(\\w{2,4}\\))$");
            Matcher matcher2 = pattern.matcher(phoneList[1]);

            result2 = matcher2.find();
            if (phoneList[1].contains("(")) {
                qntd++;
            }
        }

        if (phoneList.length >= 3) {
            Pattern pattern = Pattern.compile("^(?<block1>\\w{2,4})$");
            Matcher matcher3 = pattern.matcher(phoneList[2]);

            result3 = matcher3.find();
            if (phoneList[2].contains("(")) {
                qntd++;
            }
        }

        if (phoneList.length >= 4) {
            Pattern pattern = Pattern.compile("(?<block1>\\w{1,4})$");
            Matcher matcher4 = pattern.matcher(phoneList[3]);

            result4 = matcher4.find();
            if (phoneList[3].contains("(")) {
                qntd++;
            }
        }

        if (!(result1 && result2 && result3 && result4 && (qntd <= 1))) {
            System.out.println(WRONG_FORMATER);
        }

        return result1 && result2 && result3 && result4 && (qntd <= 1);
    }
}
