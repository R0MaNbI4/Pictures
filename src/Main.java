import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        Picture picture = new Picture();
        String fileName = "Loveis"; // Теперь можно не указывать расширение изображение, если оно .png или .jpg
        picture.imageToFile(fileName); // Считывание изображение и запись в файл в одном методе
        picture.loadPictureFromFile(fileName);
        //picture.savePictureIndexToFile(fileName); // Не оптимизировано

        Window window = new Window();
        window.drawPicture(picture);
    }
}
