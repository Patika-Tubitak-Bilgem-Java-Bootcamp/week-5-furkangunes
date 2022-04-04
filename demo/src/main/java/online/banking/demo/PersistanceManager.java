package online.banking.demo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ListIterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import online.banking.demo.model.Persistable;
import online.banking.demo.util.DoesNotExistException;

public abstract class PersistanceManager {
    protected static final Path BASE_FILE_PATH = Paths.get(
        "src", "main", "resources", "data"
    );
    private static final JSONParser jsonParser = new JSONParser();

    protected static JSONArray readArray(final Path path) throws IOException, ParseException {
        return (JSONArray) jsonParser.parse(new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
    }

    protected static void writeArray(final JSONArray array, final Path path) {
        try (Writer writer = new FileWriter(path.toFile())) {
            array.writeJSONString(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static JSONObject findObject(final Object object, final String fieldName, final JSONArray array) throws IOException, ParseException, DoesNotExistException {
        for (Object obj : array) {
            JSONObject jsonObject = (JSONObject) obj;
            if (object.equals(jsonObject.get(fieldName))) {
                return (JSONObject) obj;
            }
        }

        throw new DoesNotExistException();
    }

    protected static JSONObject findObject(final Object object, final String fieldName, final Path path) throws IOException, ParseException, DoesNotExistException {
        return findObject(object, fieldName, readArray(path));
    }

    // protected static void persist(final Persistable persistable, final Path path) {
    //     try {
    //         writeArray(updateArray(readArray(path), persistable), path);
    //     } catch (IOException | ParseException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    protected static void persist(final Path path, Persistable... persistables) {
        try {
            JSONArray array = readArray(path);
            for (Persistable persistable : persistables) {
                updateArray(array, persistable);
            }
            writeArray(array, path);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONArray updateArray(final JSONArray array, final Persistable persistable) {
        boolean exists = false;
        for (ListIterator it = array.listIterator(); it.hasNext(); ) {
            JSONObject jsonObject = (JSONObject) it.next();
            if (persistable.getId().equals(jsonObject.get(persistable.getIdKeyword()))) {
                it.remove();
                it.add(persistable.dump());
                exists = true;
                break;
            }
        }
        if (!exists) {
            array.add(persistable.dump());
        }
        return array;
    }
}
