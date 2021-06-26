package jcommandgen.service;

import jcommandgen.api.ReturnCode;

import java.io.File;

public interface IJustCmdGen {

    ReturnCode minRamDecrement();
    ReturnCode minRamIncrement();
    ReturnCode maxRamIncrement();
    ReturnCode maxRamDecrement();
    ReturnCode eulaAgreement(String oldLine, String newLine);
    ReturnCode generateCmd(String enteredMinNumber, String enteredMaxNumber, boolean isIgnore20Sec, boolean isGu);
    File getEngineFile();
    void setEngineFile(File file);
    File getEulaFile();
    void setEulaFile(File file);
    String getEngineFileDirectory();
    String getEngineFullFileName();
    String getEngineFileNameWithoutExpansion();
    Integer getObservableMaxRam();
    void setObservableMaxRam(int maxRam);
    Integer getObservableMinRam();
    void setObservableMinRam(int minRam);
    void setDefaultMinRamValue(int defaultMinRamValue);
    void setDefaultMaxRamValue(int defaultMaxRamValue);
    void clear();
    Integer getDefaultMinRamValue();
    Integer getDefaultMaxRamValue();
}
