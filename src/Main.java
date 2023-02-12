import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        File src = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games", "src");
        File res = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games", "res");
        File saveGames = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games", "saveGames");
        File temp = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games", "temp");
        StringBuilder stringBuilder = new StringBuilder();
        dirCreate(stringBuilder, src);
        dirCreate(stringBuilder, res);
        dirCreate(stringBuilder, saveGames);
        dirCreate(stringBuilder, temp);
        File main = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\src", "main");
        File test = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\src", "test");
        dirCreate(stringBuilder, main);
        dirCreate(stringBuilder, test);
        File Main = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\src\\main", "Main.java");
        File Utils = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\src\\main", "Utils.java");
        fileCreate(stringBuilder, Main);
        fileCreate(stringBuilder, Utils);
        File drawables = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\res", "drawables");
        File vectors = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\res", "vectors");
        File icons = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\res", "icons");
        dirCreate(stringBuilder, drawables);
        dirCreate(stringBuilder, vectors);
        dirCreate(stringBuilder, icons);
        File tempTxt = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\temp", "temp.txt");
        fileCreate(stringBuilder, tempTxt);
        try (FileWriter fileWriter = new FileWriter(tempTxt)) {
            fileWriter.write(String.valueOf(stringBuilder));
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        File save1 = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames", "Save1.dat");
        File save2 = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames", "Save2.dat");
        File save3 = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames", "Save3.dat");
        fileCreate(save1);
        fileCreate(save2);
        fileCreate(save3);
        GameProgress gameSave1 = new GameProgress(100, 2, 1, 100);
        GameProgress gameSave2 = new GameProgress(90, 3, 5, 500);
        GameProgress gameSave3 = new GameProgress(50, 3, 10, 1000);
        saveGame("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\Save1.dat", gameSave1);
        saveGame("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\Save2.dat", gameSave2);
        saveGame("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\Save3.dat", gameSave3);
        File saveZip1 = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames", "SaveZip1.zip");
        File saveZip2 = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames", "SaveZip2.zip");
        File saveZip3 = new File("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames", "SaveZip3.zip");
        fileCreate(saveZip1);
        fileCreate(saveZip2);
        fileCreate(saveZip3);
        zipGame("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\Save1.dat", "C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\SaveZip1.zip", gameSave1);
        zipGame("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\Save2.dat", "C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\SaveZip2.zip", gameSave2);
        zipGame("C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\Save3.dat", "C:\\Users\\milla\\OneDrive\\Рабочий стол\\Games\\saveGames\\SaveZip3.zip", gameSave3);
        save1.delete();
        save2.delete();
        save3.delete();
    }
    public static void dirCreate(StringBuilder stringBuilder, File file) {
        if (file.mkdir()) {
            stringBuilder.append("Папка " + file.getName() + " успешно создана");
            stringBuilder.append('\n');
        } else {
            System.out.println("Что-то пошло не так");
        }
    }

    public static void fileCreate(StringBuilder stringBuilder, File file) {
        try {
            if (file.createNewFile()) {
                stringBuilder.append("Файл " + file.getName() + " успешно создан");
                stringBuilder.append('\n');
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
    public static void zipGame(String path,String zipPath, GameProgress gameProgress){
       try (ZipOutputStream zop = new ZipOutputStream(new FileOutputStream(zipPath));
            FileInputStream fis = new FileInputStream(path)){

           ZipEntry entry = new ZipEntry(path);
           zop.putNextEntry(entry);
           byte[] buffer = new byte[fis.available()];
           fis.read(buffer);
           zop.write(buffer);
           zop.closeEntry();
       } catch (Exception e){
           System.out.println(e.getMessage());
       }
    }
    public static void fileCreate(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
