package bootstrap;

import static java.awt.Font.BOLD;
import static java.awt.Font.DIALOG_INPUT;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import common.exceptions.SystemException;
import common.utils.DateUtil;
import common.utils.PlayUtil;
import common.utils.StringUtil;
import play.Logger;
import play.Play;
import play.db.DBPlugin;
import play.i18n.Lang;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * @author jgomes | Aug 11, 2015 - 8:02:45 PM
 */
@OnApplicationStart
public class Bootstrap extends Job {

    private static final String BREAK_LINE = "\n";
    private static final String SIGNAL_SHARP = "#";
    private static final String WHITESPACE = " ";
    private static final String LINE_SEPARATOR = "======================================================";
    private static final int COLORS_CODE = -16777216;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see play.jobs.Job#doJob()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void doJob() throws Exception {
        try {
            this.printImageAscii("Satup");
            this.printSeparatorInfo("BOOTSTRAP START");
            this.setLogLevel();
            this.setDefaultLang();
            this.checkJdbcConnection();
        } catch (final SystemException e) {
            this.printSeparatorError("BOOTSTRAP FAIL", e);
        }
        this.printSeparatorInfo("BOOTSTRAP FINISHED");
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private Methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private void printSeparatorInfo(final String value) {
        Logger.info(Bootstrap.LINE_SEPARATOR);
        Logger.info(value);
        Logger.info(Bootstrap.LINE_SEPARATOR);
    }
    private void printSeparatorError(final String value, final Exception e) {
        Logger.info(Bootstrap.LINE_SEPARATOR);
        Logger.info(value);
        Logger.error(e, e.getLocalizedMessage());
        Logger.info(Bootstrap.LINE_SEPARATOR);
    }
    private void checkJdbcConnection() throws SystemException {
        final String dbStatus = new DBPlugin().getStatus();
        try {
            String line = null;
            final BufferedReader reader = new BufferedReader(new StringReader(dbStatus));
            while ((line = reader.readLine()) != null) {
                if (!line.contains("password") && StringUtil.isNotEmpty(line)) {
                    Logger.info(line);
                }
            }
        } catch (final IOException e) {
            throw new SystemException("Error when trying to Recover data base status");
        }
        final Date currentDate = DateUtil.currentDate();
        Logger.info("JDBC Driver works fine. Database current timestamp: %s", currentDate);
    }
    private void setLogLevel() {
        if (Play.mode.isDev()) {
            Logger.log4j = org.apache.log4j.Logger.getLogger("stdout");
        }
        Logger.info("Setting log level to: %s", Logger.log4j.getLevel());
    }
    private void setDefaultLang() {
        final String defaultLang = PlayUtil.getDefaultLang();
        Lang.change(defaultLang);
        Logger.info("Default language code is: %s", defaultLang);
    }
    private void printImageAscii(final String text) {
        if (StringUtil.isEmpty(text)) {
            return;
        }
        final int width = 130;
        final int height = 30;
        final BufferedImage image = new BufferedImage(width, height, TYPE_INT_RGB);
        final Graphics g = image.getGraphics();
        g.setFont(new Font(DIALOG_INPUT, BOLD, 16));
        final Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString(text, 6, 24);
        final StringBuilder builder = new StringBuilder();
        for (int y = 0; y < height; y++) {
            final StringBuilder innerBuilder = new StringBuilder(BREAK_LINE);
            for (int x = 0; x < width; x++) {
                innerBuilder.append(image.getRGB(x, y) == COLORS_CODE ? WHITESPACE : SIGNAL_SHARP);
            }
            if (StringUtil.isEmpty(innerBuilder.toString())) {
                continue;
            }
            builder.append(innerBuilder);
        }
        Logger.info("\n\n" + builder.toString() + "\n");
    }
}