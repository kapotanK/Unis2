package com.kapot.unis2.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UnisProps {

    private Properties props;

    public final String VERSION;
    public final boolean FILE_MODE_RETURNS_FILES;

    public UnisProps(InputStream from) throws IOException {
        this.props = new Properties();
        props.load(from);

        this.VERSION = props.getProperty("appVersion");
        this.FILE_MODE_RETURNS_FILES = Boolean.parseBoolean(props.getProperty("fileModePrintsOutToFiles"));
    }

}
