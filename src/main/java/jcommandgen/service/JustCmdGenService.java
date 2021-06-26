package jcommandgen.service;

import jcommandgen.api.RamCounter;
import jcommandgen.api.ReturnCode;
import jcommandgen.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static jcommandgen.api.Constants.*;
import static jcommandgen.api.ReturnCode.*;

public class JustCmdGenService implements IJustCmdGen {

    private int defaultMinRamValue;
    private int defaultMaxRamValue;
    private final RamCounter minRam;
    private final RamCounter maxRam;
    private final FileUtil engineFile = new FileUtil();
    private final FileUtil eulaFile = new FileUtil();
    private final FileUtil cmdFile = new FileUtil();

    public JustCmdGenService() {
        this(0, 0);
    }

    public JustCmdGenService(int defaultMinRamValue, int defaultMaxRamValue) {
        this.defaultMinRamValue = defaultMinRamValue;
        this.minRam = new RamCounter(defaultMinRamValue);
        this.defaultMaxRamValue = defaultMaxRamValue;
        this.maxRam = new RamCounter(defaultMaxRamValue);

        this.setObservableMinRam(defaultMinRamValue);
        this.setObservableMaxRam(defaultMinRamValue);
    }

    @Override
    public void setDefaultMinRamValue(int defaultMinRamValue) {
        this.defaultMinRamValue = defaultMinRamValue;
    }

    @Override
    public void setDefaultMaxRamValue(int defaultMaxRamValue) {
        this.defaultMaxRamValue = defaultMaxRamValue;
    }

    @Override
    public Integer getObservableMinRam() {
        return minRam.getObservable();
    }

    @Override
    public void setObservableMinRam(int minRam) {
        this.minRam.setObservable(minRam);
    }

    @Override
    public Integer getObservableMaxRam() {
        return maxRam.getObservable();
    }

    @Override
    public void setObservableMaxRam(int maxRam) {
        this.maxRam.setObservable(maxRam);
    }

    @Override
    public File getEngineFile() {
        return engineFile.getFile();
    }

    @Override
    public void setEngineFile(File file) {
        this.engineFile.setFile(file);
    }

    @Override
    public String getEngineFileDirectory() {
        return engineFile.getDirectory();
    }

    @Override
    public String getEngineFullFileName() {
        return engineFile.getFullFileName();
    }

    @Override
    public String getEngineFileNameWithoutExpansion() {
        return engineFile.getFileNameWithoutExpansion();
    }

    @Override
    public void setEulaFile(File eulaFile) {
        this.eulaFile.setFile(eulaFile);
    }

    @Override
    public File getEulaFile() {
        return eulaFile.getFile();
    }

    @Override
    public ReturnCode minRamDecrement() {
        if (getObservableMinRam() <= 0) {
            return INCORRECT_INPUT_NUMBER;
        }
        if (getObservableMinRam() <= defaultMinRamValue) {
            return MIN_LESS_THAN_DEFAULT_MIN;
        }
        if (getObservableMinRam() > defaultMaxRamValue) {
            return MIN_MORE_THAN_DEFAULT_MAX;
        }
        minRam.decrement(2);
        return OK;
    }

    @Override
    public ReturnCode minRamIncrement() {
        if (getObservableMinRam() <= 0) {
            return INCORRECT_INPUT_NUMBER;
        }
        if (getObservableMinRam() >= defaultMaxRamValue) {
            return MIN_MORE_THAN_DEFAULT_MAX;
        }
        if (getObservableMinRam() < defaultMinRamValue) {
            return MIN_LESS_THAN_DEFAULT_MIN;
        }
        if (getObservableMinRam() >= getObservableMaxRam()) {
            maxRamIncrement();
        }
        minRam.increment(2);
        return OK;
    }

    @Override
    public ReturnCode maxRamDecrement() {
        if (getObservableMaxRam() <= 0) {
            return INCORRECT_INPUT_NUMBER;
        }
        if (getObservableMaxRam() <= defaultMinRamValue) {
            return MAX_LESS_THAN_DEFAULT_MIN;
        }
        if (getObservableMaxRam() > defaultMaxRamValue) {
            return MAX_MORE_THAN_DEFAULT_MAX;
        }
        if (getObservableMaxRam() <= getObservableMinRam()) {
            minRamDecrement();
        }
        maxRam.decrement(2);
        return OK;
    }

    @Override
    public ReturnCode maxRamIncrement() {
        if (getObservableMaxRam() <= 0) {
            return INCORRECT_INPUT_NUMBER;
        }
        if (getObservableMaxRam() >= defaultMaxRamValue) {
            return MAX_MORE_THAN_DEFAULT_MAX;
        }
        if (getObservableMaxRam() < defaultMinRamValue) {
            return MAX_LESS_THAN_DEFAULT_MIN;
        }
        maxRam.increment(2);
        return OK;
    }

    @Override
    public ReturnCode eulaAgreement(String oldLine, String newLine) {
        if (getEngineFile() != null) {
            return setEula(oldLine, newLine);
        }
        return ENGINE_FILE_NOT_SELECTED;
    }

    @Override
    public ReturnCode generateCmd(String inputMinNumber, String inputMaxNumber, boolean isIgnore20Sec, boolean isGui) {
        if (getEngineFile() != null) {
            int enteredMinRam;
            int enteredMaxRam;
            try {
                enteredMinRam = Integer.parseInt(inputMinNumber);
                enteredMaxRam = Integer.parseInt(inputMaxNumber);
            } catch (NumberFormatException e) {
                return INCORRECT_INPUT_NUMBER;
            }
            if (enteredMinRam <= 0 || enteredMaxRam <= 0) {
                return INCORRECT_INPUT_NUMBER;
            }
            if (enteredMinRam < defaultMinRamValue) {
                return ENTERED_MIN_LESS_THAN_DEFAULT_MIN;
            }
            if (enteredMinRam > defaultMaxRamValue) {
                return ENTERED_MIN_MORE_THAN_DEFAULT_MAX;
            }
            if (enteredMaxRam < defaultMinRamValue) {
                return ENTERED_MAX_LESS_THAN_DEFAULT_MIN;
            }
            if (enteredMaxRam > defaultMaxRamValue) {
                return ENTERED_MAX_MORE_THAN_DEFAULT_MAX;
            }
            return generateCmdFile(enteredMinRam, enteredMaxRam, isIgnore20Sec, isGui);
        }
        return ENGINE_FILE_NOT_SELECTED;
    }

    @Override
    public void clear() {
        setEulaFile(null);
        setCmdFile(null);
        setEngineFile(null);
        minRam.setObservable(defaultMinRamValue);
        maxRam.setObservable(defaultMinRamValue);
    }

    @Override
    public Integer getDefaultMinRamValue() {
        return this.defaultMinRamValue;
    }

    @Override
    public Integer getDefaultMaxRamValue() {
        return this.defaultMaxRamValue;
    }

    private File getCmdFile() {
        return this.cmdFile.getFile();
    }

    private void setCmdFile(File cmdFile) {
        this.cmdFile.setFile(cmdFile);
    }

    private ReturnCode generateCmdFile(int enteredMinRam, int enteredMaxRam, boolean isIgnore20Sec, boolean isGui) {
        setCmdFile(new File(String.format("%s\\%s-%s", getEngineFileDirectory(), getEngineFileNameWithoutExpansion(), ENGINE_FILE_NAME)));
        String command = getCommand(enteredMinRam, enteredMaxRam, isIgnore20Sec, isGui);
        try {
            if (getCmdFile().createNewFile()) {
                return cmdFile.writeLines(Collections.singletonList(command)) ? CMD_FILE_GENERATED : CMD_FILE_NOT_GENERATED;
            } else {
                return cmdFile.writeLines(Collections.singletonList(command)) ? CMD_FILE_MODIFIED : CMD_FILE_NOT_MODIFIED;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return CMD_FILE_NOT_GENERATED;
        }
    }

    private ReturnCode setEula(String oldLine, String newLine) {
        try {
            if (getEulaFile().createNewFile()) {
                List<String> list = new ArrayList<>();
                Collections.addAll(list,"#By changing the setting below to TRUE you are indicating your " +
                                "agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).",
                        "#" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        newLine);
                return eulaFile.writeLines(list) ? ACCEPTED : NOT_ACCEPTED;
            } else {
                return eulaFile.replaceOldLineToNewLine(oldLine, newLine) ? EULA_FILE_MODIFIED : EULA_FILE_NOT_MODIFIED;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return EULA_FILE_NOT_CREATED;
        }
    }

    private String getCommand(int minRam, int maxRam, boolean isIgnore20Sec, boolean isGui) {
        StringBuilder sb = new StringBuilder("java ");
        sb.append("-Xms").append(minRam).append("M ")
                .append("-Xmx").append(maxRam).append("M ")
                .append("-jar -Dfile.encoding=UTF-8 ");
        if (isIgnore20Sec) {
            sb.append("-DIReallyKnowWhatIAmDoingISwear ");
        }
        sb.append(getEngineFullFileName());
        if (!isGui) {
            sb.append(" nogui");
        }
        return sb.toString();
    }

}
