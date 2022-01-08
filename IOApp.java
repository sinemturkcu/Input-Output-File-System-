

import java.io.*;
import java.util.*;

public class IOApp {
    static String filename = "transactions.txt";
    static int limitRecord = 100;
    static Record[] records;
    static int lastIndex;

    static class Record {
        String name;
        Integer price, number;
    }

    public static void main(String[] args) throws IOException {
        initialProcess(); // Don't change
        showMenu();

    }

    public static void showMenu() throws IOException {

        System.out.println("------MENU-----------------");
        System.out.println("------1-ADD RECORD---------");
        System.out.println("------2-REMOVE RECORD------");
        System.out.println("------3-SEARCH RECORD------");
        System.out.println("------4-LIST ALL-----------");
        System.out.println("------5-DELETE ALL---------");
        System.out.println("------6-CLOSE--------------");

        System.out.println("Please make a choice: ");

        Scanner input = new Scanner(System.in);
        int user_choice = input.nextInt();

        if (user_choice == 1) {
            System.out.println("Please enter Name: ");
            String name = input.next();
            System.out.println("Please enter Price: ");
            int price = input.nextInt();
            System.out.println("Please enter Number: ");
            int number = input.nextInt();

            addRecord(name, price, number);
            update();
            System.out.println("Record is added.");
            showMenu();
        } else if (user_choice == 2) {
            System.out.println("Please enter Name: ");
            String name = input.next();
            if (removeRecord(name)) {
                lastIndex--;
                update();
                System.out.println("Record is removed.");
                showMenu();
            } else {
                System.out.println("Record is not available.");
                showMenu();

            }
        } else if (user_choice == 3) {
            System.out.println("Please enter Name: ");
            String name = input.next();
            searchRecord(name);
            update();
            showMenu();
        } else if (user_choice == 4) {
            listRecord();
            showMenu();
        } else if (user_choice == 5) {
            System.out.println("Are you sure ? y/n");
            String ask = input.next().toLowerCase();
            if (ask.equals("y"))
                deleteall();
            showMenu();
        } else if (user_choice == 6) {
            System.exit(0);
        } else {
            System.out.println("Please enter valid number");
            showMenu();
        }


    }

    private static void listRecord() {

        if (lastIndex == 0) {
            System.out.println("There is no product in list");
        }
        for (int i = 0; i < lastIndex; i++) {
            System.out.println(records[i].name + " " + records[i].number + " " + records[i].price);
        }


    }

    private static void addRecord(String name, Integer price, Integer number) throws FileNotFoundException {
        Record newRecord = new Record();
        newRecord.name = name;
        newRecord.price = price;
        newRecord.number = number;

        records[lastIndex] = newRecord;
        lastIndex++;

    }

    private static void update() throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(filename)));


        for (int i = 0; i < lastIndex; i++) {
            dataOutputStream.writeBytes(records[i].name + "\t" + records[i].price + "\t" + records[i].number + "\n");
        }

        dataOutputStream.close();

    }

    private static boolean removeRecord(String name) {
        boolean process = false;
        int i;
        for (i = 0; i < lastIndex; i++) {
            if (records[i].name.equals(name)) {
                process = true;
                break;
            }
        }
        records[i] = new Record();
        int a;
        for (a = i; a < lastIndex - i; a++) {
            records[a] = records[a + 1];
        }
        records[a] = new Record();

        return process;
    }

    private static void searchRecord(String name) {
        boolean process = false;
        int i;
        for (i = 0; i < lastIndex; i++) {
            if (records[i].name.equals(name)) {
                process = true;
                break;
            }
        }

        if (process) {
            System.out.println(records[i].name + "\t" + records[i].price + "\t" + records[i].number);
        } else {
            System.out.println("Product is not available!");
        }
    }

    private static void deleteall() throws IOException {
        records = new Record[limitRecord];
        for (int i = 0; i < limitRecord; i++) {
            records[i] = new Record();
        }
        System.out.println("All records are deleted successfully!");
        lastIndex = 0;
        update();

    }

    // initialProcess() method must not be changed.

    private static void initialProcess() {
        records = new Record[limitRecord];
        for (int i = 0; i < limitRecord; i++) {
            records[i] = new Record();
        }

        try {
            Reader reader = new InputStreamReader(new FileInputStream(filename), "Windows-1254");
            BufferedReader br = new BufferedReader(reader);
            String strLine;
            int i = 0;

            while ((strLine = br.readLine()) != null) {
                StringTokenizer tokens = new StringTokenizer(strLine, "\t");
                String[] t = new String[3];
                int j = 0;
                while (tokens.hasMoreTokens()) {
                    t[j] = tokens.nextToken();
                    j++;
                }
                records[i].name = t[0];
                records[i].price = Integer.valueOf(t[1]);
                records[i].number = Integer.valueOf(t[2]);
                i++;
            }

            lastIndex = i;
            reader.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
