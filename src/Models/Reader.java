package Models;

import java.io.*;

public class Reader {
    private final File configuration;
    private BufferedReader reader;

    public Reader(String fileName) {
        configuration = new File(fileName);
        try {
            reader = new BufferedReader(new FileReader(configuration));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public String getLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean fileIsReady() {
        try {
            if (configuration == null || reader == null) {
                return false;
            } else return reader.ready();
        } catch (IOException e) {
            return false;
        }
    }
}

