import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileReader {

    /**
     * Reads victims from a file and forms groups of victims.
     * @param filename
     * @param numberOfGroups
     * @return
     */
    public List<List<String>> readVictimsFile(String filename, int numberOfGroups) {
        List<String> victims = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                victims.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return formRandomGroups(victims, numberOfGroups);
    }

    private List<List<String>> formRandomGroups(List<String> victims, int numberOfGroups) {
        List<List<String>> groups = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfGroups; i++) {
            int groupSize = random.nextInt(4) + 2;
            List<String> group = new ArrayList<>();

            for (int j = 0; j < groupSize; j++) {
                int index = random.nextInt(victims.size());
                group.add(victims.remove(index));
            }

            groups.add(group);

        }

        return groups;
    }

}