package ggcfg;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * The class enclosing all config manager functions.
 */
public class CfgManager {

    /**
     * Reads ("deserializes") a config from a String.
     * @param lines The text of a configuration.
     * @param obj The object to fill.
     * @param <T>The type the config was based on.
     * @return obj
     */

    public static  <T> T readConfigStr(String lines, T obj) throws IllegalAccessException {
        return readConfig(Arrays.asList(lines.split(System.lineSeparator())), obj);
    }

    /**
     * Reads ("deserializes") a config from a list of cfg lines.s
     * @param lines The lines of a configuration.
     * @param obj The object to fill.
     * @param <T>The type the config was based on.
     * @return obj
     */
    public static <T> T readConfig(List<String> lines, T obj) throws IllegalAccessException
    {
        Map<String, Field> cfgPropMap = new HashMap<>();

        var cls = obj.getClass();
        var fields = cls.getDeclaredFields();

        for (var it: fields) {
            if(it.isAnnotationPresent(CfgProperty.class)){
                cfgPropMap.put(it.getName(), it);
            }
        }

        var pairs = getPairs(lines);

        for (var it: pairs) {
            var field = cfgPropMap.get(it[0]);
            if(field == null)
                throw new NoSuchFieldError("Entry specified in cfg file not found. (" + it[0] +")");

            field.setAccessible(true);
            var type = field.getType();

            if(type == float.class || type == double.class){
                field.set(obj, Double.parseDouble(it[1]));
            }
            else if(type == String.class){
                field.set(obj, it[1]);
            }
            else if(type == int.class){
                field.set(obj, Integer.parseInt(it[1]));
            }
        }
        return obj;
    }

    /**
     * Reads a config from the given path to an object of type T
     * @param path The filepath to load the .cfg file from (does not actually need to be of .cfg)
     * @param obj The object to load the config into.
     * @param <T> The type which the config was based on.
     * @return The requested config
     */
    public static <T> T readConfig(String path, T obj) throws IOException, IllegalAccessException {

        var lines = readFile(path);
        return (T) readConfig(lines, obj);
    }

    private static List<String> readFile(String path) throws IOException {
        return Files.readAllLines(Path.of(path));
    }

    /**
     * Reads or if the read fails creates a config to a given file.
     * @param path The path of the cfg file.
     * @param cfgObj The object to read data into.
     * @param <T> The type which the config was based on.
     * @return The requested config
     */
    public static <T> T readOrCreateDefault(String path, T cfgObj) {
        if(Files.exists(Path.of(path))) {
            try {
                return readConfig(path, cfgObj);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            Save(path, cfgObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfgObj;
    }

    /**
     * Writes a config of type T to a given filepath. (Using CfgProperty annotations.)
     * @param path The destination path.
     * @param obj The object to base the written config on.
     * @param <T> The type to base the config on.
     */
    public static <T> void Save(String path, T obj) throws IllegalAccessException, IOException {
        var lines = generateLines(obj);
        FileWriter writer = new FileWriter(path);
        for(var it : lines){
            writer.write(it + System.lineSeparator());
        }
        writer.close();
    }


    /**
     * Writes a config of type T to a String. (Using CfgProperty annotations.)
     * @param objIn The object to base the written config on.
     * @param <T> The type to base the config on.
     * @return The string form of a config.
     * @throws IllegalAccessException
     */
    public static <T> String generateStr(T objIn) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        for(var it: generateLines(objIn))
            sb.append(it + System.lineSeparator());
        return sb.toString();
    }


    private static <T> List<String> generateLines(T objIn) throws IllegalAccessException {
        ArrayList<String> res = new ArrayList<>();
        var cls = objIn.getClass();
        var fields = cls.getDeclaredFields();
        for (var it: fields) {
            if(it.isAnnotationPresent(CfgProperty.class)){
                it.setAccessible(true);
                res.add(it.getName() + " = " + it.get(objIn).toString());
            }
        }
        return res;
    }

    private static ArrayList<String[]> getPairs(List<String> lines) throws IndexOutOfBoundsException{
        ArrayList<String[]> res = new ArrayList<>();
        for (var it: lines) {
            if(it.isBlank() || it.isEmpty())
                continue;
            var pair = it.split("=");
            if(pair.length != 2)
                throw  new IndexOutOfBoundsException("All configuration properties are expected as \"key = value\" pairs");
            pair[0] = pair[0].trim();
            pair[1] = pair[1].trim();
            res.add(pair);
        }
        return res;
    }
}
