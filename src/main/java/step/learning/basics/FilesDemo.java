package step.learning.basics;

import jdk.nashorn.internal.parser.DateParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class FilesDemo {
    // region зберігання даних у файлах
    public void run() {
        String filename = "test.txt";
        // Всі види роботи з даними у файлі - через Stream, особливість усіх stream -
        // Це некеровані ресурси, їх треба закривати окремими командами або вживати
        // блоки з автоматичним вивільненням ресурсів (using - C#, try() - Java)
        try (OutputStream writer = new FileOutputStream(filename);) {
            writer.write("Hello, world".getBytes());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("\nNew Line");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        try (InputStream reader = new FileInputStream(filename)) {
            int c;// symbol to read
            while ((c = reader.read()) != -1) { // -1 — EOF
                sb.append((char) c);
            }
            System.out.println(sb.toString());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println("---------------------------------");
        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream(4096);            //
        byte[] buf = new byte[512];                                                          //
        try (InputStream reader = new BufferedInputStream(new FileInputStream(filename))) {  //
            int cnt;                                                                         //
            while ((cnt = reader.read(buf)) > 0) {                                                 //
                byteBuilder.write(buf, 0, cnt);                                            //
            }                                                                                //
            String content = new String(                                                     //
                    byteBuilder.toByteArray(),                                               //
                    StandardCharsets.UTF_8                                                   //
            );                                                                               //
            System.out.println(content);                                                     //
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println("---------------------------------");
        try (InputStream reader = new FileInputStream(filename);
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) { // next - "слово" - між пробілами/кінцем рядку
                System.out.println(scanner.next());
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
//        Scanner kbScanner = new Scanner(System.in);
//        System.out.print("Your name: ");
//        String name=kbScanner.next();
//        System.out.printf("Hello, %s!%n",name);
        Random random=new Random();
        byte[] arr=new byte[random.nextInt(50)];
        random.nextBytes(arr);
        for (byte x: arr) {
            System.out.print((char)x);
        }

    }
    // endregion

    // region створення файлів та папок
    public void run2() {

        File dir = new File("./uploads");
        // задача: перевірити чи є в проєкті папка, якщо немає - створити
        if (dir.exists()) {
            if (dir.isDirectory()) {
                System.out.printf("Directory '%s' already exists%n", dir.getName());
            } else {
                System.out.printf("'%s' already exists BUT IS NOT DIRECTORY%n", dir.getName());
            }
        } else {
            if (dir.mkdir()) {
                System.out.printf("Directory '%s' created", dir.getName());
            } else {
                System.out.printf("Directory '%s' creation error%n", dir.getName());
            }
        }
        // створити у директорії файл
        File file = new File("./uploads/whitelist.txt");
        if (file.exists()) {
            if (file.isFile()) {
                System.out.printf("File '%s' already exists%n", file.getName());
            } else {
                System.out.printf("'%s' already exists BUT IS NOT FILE%n", file.getName());
            }
        } else {
            try {
                if (file.createNewFile()) {
                    System.out.printf("File '%s' created", file.getName());
                } else {
                    System.out.printf("File '%s' creation error%n", file.getName());
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    // endregion

    // region Робота з файлами частина 1
    public void run1() {
        // Основа роботи з файлами - java.io.File
        File dir = new File("./");
        // стврення new File НЕ впливає на файлову систему, це лише
        // програмний об'єкт, який відповідає за зазначений шлях
        if (dir.exists()) {
            System.out.println("Path exists");
        } else {
            System.out.println("Path does not exist");
        }

        System.out.printf("Path is %s %n",
                dir.isDirectory() ? "directory" : "file");

        System.out.println(dir.getAbsolutePath());
//        for (String filename : dir.list()) {    // dir.list() - лише імена (String)
//            System.out.println(filename);       // dir.listFiles() - об'єкти (Files)
//        }
        System.out.println(String.format("%-6s %-17s %-8s %s", "Mode", "LastWriteTime", "Length", "Name"));
        System.out.println(String.format("%-6s %-17s %-8s %s", "----", "-------------", "------", "----"));
        for (File file : dir.listFiles()) {
            PrintFileInfo(file);
        }

    }

    private void PrintFileInfo(File file) {
        System.out.println(
                String.format("%-6s %-17s %-8s %s",
                        getMode(file),
                        getDate(file.lastModified()),
                        getLength(file),
                        file.getName()));
    }

    private String getMode(File file) {
        return file.isDirectory() ? "d-----" : "-a----";
    }

    private String getLength(File file) {
        return file.isFile() ? String.format("%s", file.length()) : "";
    }

    private String getDate(long milliseconds) {
        Date date = new Date(milliseconds);
        Locale.setDefault(Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(date);
    }
    // endregion
}

/* Робота з файлами розглядається у двох аспектах:
    - робота з файловую системою: створення файлів, пошук, переміщення, видалення, тощо
    - використання файлів для збереження/віднолвення даних
   Зберігання/читання даних з файлів відбувається через Stream
    - це некеровані ресурси, потрібне закриття
    - базові засоби Stream значно обмежені роботою з одним байтом чи їх масивом
    - часто вживаються "обгортки", які спрощують рботу з даними, розбиваючи
      потоки байт на типи Java
        = FileWriter/FileReader - додає роботу з символами та строками
*/
