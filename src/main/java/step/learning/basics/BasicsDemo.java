package step.learning.basics;

import java.util.*;

public class BasicsDemo {
    public void run() {
        // region Types and Variables
        System.out.println("BasicsDemo");  // snippet sout
        System.out.printf("Interpolated '%s' value%n", "hello");
        // Змінні та типи даних
        // primitives          У Java всі цілі типи даних - знакові
        byte b = 10;        // 8 біт (-128..+127) - 256 комбінацій
        short s = 100;      // 16 64к
        int i = 10000;      // 32 4G
        long l = 1000000000;// 64
        // Цим типам наявні reference-аналоги
        Byte rb = 10;            // boxing-обгортки для primitive-типів
        Short rs = 1000;
        Integer ri = 10000;
        Long rl = 1000000000L;

        float f = 1e-3f; // 32 MeE M - мантиса: число від 1.0 до 9.(9)
        double d = 2e-7; // 64     Е - експонента: ціле число - показник 10
        // 1е-3 = 1 х 10^(-3) = 0.001
        // 2е-7 = 1 х 10^(-7) = 0.0000002
        // 3.3е3 = 3.3 х 10^3 = 3300

        boolean bool = true;
        char c = 'A';

        String str1 = "Hello";    // String pooling - збирання однакових значень
        String str2 = "Hello";    // та використання іх для різних рядків
        String str3 = new String("Hello");
        if (str1 == str2) {    // у Java == - об'єктна рівність (рівність посилань)
            System.out.println("str1 == str2"); // рівні оскільки pooling
        } else {
            System.out.println("str1 != str2");
        }

        if (str1 == str3) {
            System.out.println("str1 == str2");
        } else {
            System.out.println("str1 != str2");
        }

        // для порівняння вмісту використовується .equals()
        if (str1.equals(str3)) {
            System.out.println("str1 equals str2");
        } else {
            System.out.println("str1 not equals str2");
        }
        // endregion

        arrDemo();
    }

    private void arrDemo() {
        // масиви, колекції, цикли
        int[] arr1 = {5, 4, 3, 2, 1};
        int[] arr2 = new int[]{5, 4, 3, 2, 1};
        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + " ");
        }
        System.out.println();

        for (int x : arr2) { // foreach (for-of)
            System.out.print(x + " ");
        }
        System.out.println();

        int[][] arr2d = {
                {1, 2, 3},
                {4, 5, 6, 4},
                {7, 8, 9, 1, 2},
        };
        for (int[] x : arr2d) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }

        // Collections
        // Linear: List - interface, ArrayList - implementation
        List<Integer> list1 = new LinkedList<>(); // new ArrayList<>();
        list1.add(10);
        list1.add(20);
        list1.add(30);
        list1.add(40);
        list1.add(50);
        for (Integer x : list1) {
            System.out.print(x + " ");
        }
        System.out.println();

        // Assoc:
        Map<String,String> headers = new LinkedHashMap<>();
        headers.put("Host","localhost");
        headers.put("Connection","close");
        headers.put("Content-Type","text/html");
        for (String key : headers.keySet()) {
            System.out.println(
                    String.format(
                            "%s: %s",
                            key, headers.get(key)
                    )
            );
        }
    }
}
