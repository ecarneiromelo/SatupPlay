package common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import common.exceptions.SystemException;

/**
 * @author jgomes
 */
public class ProcessUtil {

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private ProcessUtil() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Public static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static synchronized String runCommand(final String... commandAndArgs) throws SystemException {
        final List<String> lstCommandAndArgs = new ArrayList<String>(commandAndArgs.length);
        for (final String arg : commandAndArgs) {
            lstCommandAndArgs.add(arg);
        }
        Process process = null;
        try {
            final ProcessBuilder processBuilder = new ProcessBuilder(lstCommandAndArgs);
            process = processBuilder.start();
            return getProcessResult(process);
        } catch (final IOException e) {
            throw new SystemException(e);
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private static methods.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static String getProcessResult(final Process process) throws SystemException, IOException {
        try {
            if (process.waitFor() == 0) {
                return getSuccessInputStream(process);
            } else {
                return getErrorInputStream(process);
            }
        } catch (final InterruptedException e) {
            throw new SystemException(e);
        }
    }
    private static String getErrorInputStream(final Process process) throws SystemException, IOException {
        InputStream errorStream = null;
        try {
            errorStream = process.getErrorStream();
            return readInputStream(errorStream);
        } catch (final IOException e) {
            throw new SystemException(e);
        } finally {
            if (errorStream != null) {
                errorStream.close();
            }
        }
    }
    private static String getSuccessInputStream(final Process process) throws SystemException, IOException {
        InputStream inputStream = null;
        try {
            inputStream = process.getInputStream();
            return readInputStream(inputStream);
        } catch (final IOException e) {
            throw new SystemException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
    private static String readInputStream(final InputStream inputStream) throws IOException {
        final StringBuilder builder = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
            return builder.toString();
        } finally {
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }
}
