package utils;

import model.IModel;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static <T> void writeToFile(String path, List<T> datas){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (T item: datas){
                bw.write(item.toString() + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static <T> List<T> readFromFile(String path, Class<T> tClass){
        List<T> datas = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line = br.readLine()) != null) {
                Object obj = tClass.getDeclaredConstructor().newInstance();
                IModel<T> iModel = (IModel<T>) obj;
                iModel.parseData(line);

                datas.add((T) obj);
            }
            br.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return datas;
    }
}
