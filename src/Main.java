import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    protected static StringBuilder log = new StringBuilder();
    public static void main(String[] args) {

        List<String> directories = new ArrayList<>();
        directories.addAll(Arrays.asList("C:\\Games\\src","C:\\Games\\res","C:\\Games\\temp","C:\\Games\\saveGames","C:\\Games\\src\\main","C:\\Games\\src\\test",
                "C:\\Games\\res\\drawables","C:\\Games\\res\\vectors",
                "C:\\Games\\res\\icons"));
        for(String element: directories){
            dirCreate(element);
        }
        createNLogFile("C:\\Games\\src\\main", "Main.java");
        createNLogFile("C:\\Games\\src\\main", "Utils.java");
        createNLogFile("C:\\Games\\temp", "temp.txt");
        try (FileWriter fileWriter = new FileWriter("C:\\Games\\temp\\temp.txt", false)) {
            fileWriter.write(String.valueOf(log));
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        List<String> gameFiles = new ArrayList<>();
        gameFiles.addAll(Arrays.asList("C:\\Games\\saveGames\\Save1.dat", "C:\\Games\\saveGames\\Save2.dat", "C:\\Games\\saveGames\\Save3.dat"));
        fileCreate("C:\\Games\\saveGames", "Save1.dat");
        fileCreate("C:\\Games\\saveGames", "Save2.dat");
        fileCreate("C:\\Games\\saveGames", "Save3.dat");

        GameProgress gameSave1 = new GameProgress(100, 2, 1, 100);
        GameProgress gameSave2 = new GameProgress(90, 3, 5, 500);
        GameProgress gameSave3 = new GameProgress(50, 3, 10, 1000);
        saveGame("C:\\Games\\saveGames\\Save1.dat", gameSave1);
        saveGame("C:\\Games\\saveGames\\Save2.dat", gameSave2);
        saveGame("C:\\Games\\saveGames\\Save3.dat", gameSave3);
        fileCreate("C:\\Games\\saveGames", "SaveZip.zip");
        zipGame("C:\\Games\\saveGames\\SaveZip.zip", gameFiles);

        try {
            Files.delete(Path.of("C:\\Games\\saveGames\\Save1.dat"));
            Files.delete(Path.of("C:\\Games\\saveGames\\Save2.dat"));
            Files.delete(Path.of("C:\\Games\\saveGames\\Save3.dat"));
        } catch (IOException x) {
            // File permission problems are caught here.
            System.out.println(x.getMessage());
        }
    }
     public static void dirCreate(String path) {
        File file = new File(path);
        if (file.mkdir()) {
            log.append("Папка " + file.getName() + " успешно создана");
            log.append('\n');
        } else {
            System.out.println("Что-то пошло не так");
        }
    }

    public static void createNLogFile(String path, String name) {
        File file = new File(path,name);
        try {
            if (file.createNewFile()) {
                log.append("Файл " + file.getName() + " успешно создан");
                log.append('\n');
            } else {
                System.out.println("Что-то пошло не так");
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public static void saveGame(String path, GameProgress gameProgress){
        try(FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(gameProgress);
            oos.flush();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void zipGame(String zipPath, List<String> gameFiles){
       try (ZipOutputStream zop = new ZipOutputStream(new FileOutputStream(zipPath))){
            for(String arr : gameFiles){
            try(FileInputStream fis = new FileInputStream(arr)){

               ZipEntry entry = new ZipEntry(arr);
               zop.putNextEntry(entry);
               byte[] buffer = new byte[fis.available()];
               fis.read(buffer);
               zop.write(buffer);
               zop.closeEntry();
           }
       }} catch (Exception e){
           System.out.println(e.getMessage());
       }
    }
    public static void fileCreate(String path, String name) {
        File file = new File(path,name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
