package step.learning.basics;

import java.io.File;
import java.io.FileNotFoundException;

public class FilesDemo2 {
    private void findFilePath(File inputFile, String findFile) throws FileNotFoundException{
        if(!inputFile.exists()){
            throw new FileNotFoundException(String.format("'%s' file is not exist",inputFile));
        }
        if(inputFile.isDirectory()){
            File[] filesList=inputFile.listFiles();
            if(filesList!=null){
                for(File file:filesList){
                    if(file.isDirectory()){
                        findFilePath(file,findFile);
                    }
                    else{
                        if(file.getName().contains(findFile)){
                            System.out.println(file.getAbsolutePath());
                            break;
                        }
                    }
                }
            }
        }
    }
    public void run(){
        System.out.println("fefef");
        try {
            findFilePath(new File("C:\\Users\\dsgnrr\\Desktop\\Новая папка (2)"),"git.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
