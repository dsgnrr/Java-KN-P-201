package step.learning.basics;

import jdk.nashorn.internal.parser.DateParser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FilesDemo {
    public void run() {
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
        System.out.println(String.format("%-6s %-17s %-8s %s","----","-------------","------","----"));
        for (File file : dir.listFiles()) {
            PrintFileInfo(file);
        }

    }
    private void PrintFileInfo(File file){
        System.out.println(
                String.format("%-6s %-17s %-8s %s",
                        getMode(file),
                        getDate(file.lastModified()),
                        getLength(file),
                        file.getName()));
    }
    private String getMode(File file){
        return file.isDirectory()?"d-----":"-a----";
    }
    private String getLength(File file){
        return file.isFile()?String.format("%s",file.length()):"";
    }
    private String getDate(long milliseconds) {
        Date date = new Date(milliseconds);
        Locale.setDefault(Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(date);
    }
}

/* Робота з файлами розглядається у двох аспектах:
    - робота з файловую системою: створення файлів, пошук, переміщення, видалення, тощо
    - використання файлів для збереження/віднолвення даних
*/
