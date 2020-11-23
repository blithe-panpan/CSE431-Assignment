package minimum_edit_distance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MED {

    public static int min = 0;

    public static void main(String[] args) {
        try {
            String[] words = null;
            String lines = "";
            Scanner readfile = new Scanner(new File("C:\\Users\\Asus\\Documents\\NetBeansProjects\\Minimum_Edit_Distance\\src\\minimum_edit_distance\\input.txt"));
            while (readfile.hasNextLine()) {
                lines = lines + readfile.nextLine();
            }
            words = lines.split(" ");
            String word01 = words[0];
            String word02 = words[1];
            char[] ch01 = word01.toCharArray();
            char[] ch02 = word02.toCharArray();
            int coloumnSize = word02.length() + 2;
            int rowSize = word01.length() + 2;
            String array[][] = new String[rowSize][coloumnSize];
            InitializeArray(array, ch01, ch02);
            PrintStream myConsole = new PrintStream(new File("C:\\Users\\Asus\\Documents\\NetBeansProjects\\Minimum_Edit_Distance\\src\\minimum_edit_distance\\output.txt"));
            System.setOut(myConsole);
            printArray(array, rowSize, coloumnSize);
            calculateMinimumEditDistance(array, ch01, ch02);
            printArray(array, rowSize, coloumnSize);
            System.out.println("Therefore, the minimum edit distance between " + word01 + " and " + word02 + " is " + min);
        } catch (FileNotFoundException fx) {
            System.out.println(fx);
        }
    }

    public static void printArray(String[][] array, int size01, int size02) {
        for (int i = 0; i < size01; i++) {
            for (int j = 0; j < size02; j++) {
                System.out.print(array[i][j] + " ");
                System.out.print("\t");
            }

            System.out.println();
        }
        System.out.println();
    }

    public static void printSteps(int a, int b, int c, int add, int min, String x, String y) {
        System.out.println("Steps :");
        System.out.println("D[i-1][j]=" + a + " + 1 = " + (a + 1));
        System.out.println("D[i][j-1]=" + b + " + 1 = " + (b + 1));
        System.out.println("D[i][j-1]=" + c + " + " + add + " = " + (c + add));
        System.out.println("Here, the minimum edit distance between " + x + " and " + y + " is " + min);
        System.out.println();
    }

    public static void InitializeArray(String[][] array, char ch01[], char ch02[]) {
        array[ch01.length + 1][0] = " ";
        array[ch01.length][0] = "#";
        array[ch01.length + 1][1] = "#";
        for (int i = 0, j = ch01.length - 1; i < ch01.length; i++, j--) {
            array[i][0] = String.valueOf(ch01[j]);
        }
        for (int i = 0, j = 2; i < ch02.length; i++, j++) {
            array[array.length - 1][j] = "" + String.valueOf(ch02[i]);
        }
    }

    public static int findNumberFromString(String str) {
        String v = "";
        int value = 0;
        for (int i = 0; i < str.length(); i++) {
            Boolean flag = Character.isDigit(str.charAt(i));
            if (flag) {
                v = v + str.charAt(i);

            }
        }
        value = Integer.parseInt(v);
        return value;
    }

    public static void calculateMinimumEditDistance(String[][] a, char ch01[], char ch02[]) {
        int count = 0;
        for (int i = ch01.length, j = 1; j <= ch02.length + 1; j++) {
            if ("#".equals(a[i][j - 1])) {
                a[i][j] = "0";
            } else {
                count = count + 1;
                a[i][j] = "" + count;
            }
        }
        printArray(a, ch01.length + 2, ch02.length + 2);
        count = 0;
        for (int i = ch01.length - 1, j = 1; i >= 0; i--) {
            count = count + 1;
            a[i][j] = "" + count;
        }
        printArray(a, ch01.length + 2, ch02.length + 2);
        int x = 0, y = 0, z = 0, add = 0;
        String v = "";
        for (int i = ch01.length - 1; i >= 0; i--) {
            for (int j = 2; j <= ch02.length + 1; j++) {
                v = "";
                x = findNumberFromString(a[i][j - 1]) + 1;
                y = findNumberFromString(a[i + 1][j]) + 1;
                if (a[i][0] == null ? a[ch01.length + 1][j] == null : a[i][0].equals(a[ch01.length + 1][j])) {
                    add = 0;
                    z = findNumberFromString(a[i + 1][j - 1]) + add;

                } else if (a[i][0] == null ? a[ch01.length + 1][j] != null : !a[i][0].equals(a[ch01.length + 1][j])) {
                    add = 2;
                    z = findNumberFromString(a[i + 1][j - 1]) + add;
                }
                min = Math.min(Math.min(x, y), z);
                if (min == x) {
                    v = v + "←";
                }
                if (min == y) {
                    v = v + "↓";
                }
                if (min == z) {
                    v = v + "↙";
                }
                a[i][j] = v + "" + min;
                printArray(a, ch01.length + 2, ch02.length + 2);
                printSteps(x - 1, y - 1, z - add, add, min, a[i][0], a[ch01.length + 1][j]);
            }
        }
    }
}
