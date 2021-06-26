package jcommandgen.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private File file;

    public FileUtil() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPathWithoutExpansion() {
        return withoutExpansion(file.getPath());
    }

    public String getFileNameWithoutExpansion() {
        return withoutExpansion(file.getName());
    }

    public String getDirectory() {
        return file.getParent();
    }

    public String getFullFileName() {
        return file.getName();
    }

    public boolean replaceOldLineToNewLine(String oldLine, String newLine) {
        List<String> newLines = new ArrayList<>();
        try {
            List<String> oldLines = getFileLines();
            for (String line : oldLines) {
                if (line.contains(oldLine)) {
                    newLines.add(line.replace(oldLine, newLine));
                } else {
                    newLines.add(line);
                    if (newLine.equals(line)) {
                        return false;
                    }
                }
            }
            Files.write(file.toPath(), newLines);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean writeLines(List<String> lines) {
        try {
            if (lines != null && !lines.isEmpty() && !isEqualValues(lines)) {
                Files.write(file.toPath(), lines);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String withoutExpansion(String s) {
        int pos = s.lastIndexOf(".");
        return s.substring(0, pos);
    }

    private List<String> getFileLines() throws IOException {
        return Files.readAllLines(file.toPath());
    }

    private boolean isEqualValues(List<String> lines) {
        try {
            return getFileLines().equals(lines);
        } catch (IOException e) {
            return false;
        }
    }
}
