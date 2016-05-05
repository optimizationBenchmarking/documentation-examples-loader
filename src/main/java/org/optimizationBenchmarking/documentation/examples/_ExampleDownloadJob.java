package org.optimizationBenchmarking.documentation.examples;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * The example download job will return a path where it installed the
 * example, or {@code null} if nothing was installed.
 */
final class _ExampleDownloadJob extends _ExampleJobBase<Path> {

  /** the name of the file list */
  private static final String FILE_LIST_NAME = "fileList.txt"; //$NON-NLS-1$

  /** the data folder which needs to be appended to the base url */
  private static final String DATA_FOLDER = "data/";//$NON-NLS-1$

  /** the example's id */
  private final String m_id;
  /** the destination directory */
  private final Path m_dest;

  /**
   * create the job
   *
   * @param builder
   *          the builder
   */
  _ExampleDownloadJob(final ExampleDownloadJobBuilder builder) {
    super(builder);

    this.m_id = ExampleDownloadJobBuilder._checkExampleId(builder.m_id);
    this.m_dest = ExampleDownloadJobBuilder
        ._checkDestinationPath(builder.m_dest);
  }

  /** {@inheritDoc} */
  @Override
  public final Path call() throws Exception {
    final URI exampleUri;
    final Path destDirectory;
    final Logger logger;
    String name;
    URL fileListUrl;
    ArrayList<String> list;
    String line;
    int counter;

    logger = this.getLogger();

    name = (((((("example '" + //$NON-NLS-1$
        this.m_id) + "' from repository '") + //$NON-NLS-1$
        this.m_baseUri) + "' to destination folder '") + //$NON-NLS-1$
        this.m_dest) + '\'');
    if (this.m_version != null) {
      name += " for version '" + this.m_version + '\'';//$NON-NLS-1$
    }
    name += '.';

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Now beginning to download and install " + //$NON-NLS-1$
          name);
    }

    destDirectory = PathUtils.createPathInside(this.m_dest, this.m_id);
    exampleUri = this.m_baseUri
        .resolve(_ExampleDownloadJob.DATA_FOLDER + this.m_id + '/');
    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer((("The URI of the example is '" + //$NON-NLS-1$
          exampleUri) + '\'') + '.');
    }

    fileListUrl = exampleUri.resolve(_ExampleDownloadJob.FILE_LIST_NAME)
        .toURL();
    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Now downloading the list of files to install from '" + //$NON-NLS-1$
          fileListUrl) + '\'') + '.');
    }

    list = new ArrayList<>();
    try (final InputStream listStream = fileListUrl.openStream()) {
      try (final InputStreamReader reader = new InputStreamReader(
          listStream)) {
        try (final BufferedReader buffered = new BufferedReader(reader)) {
          while ((line = buffered.readLine()) != null) {
            line = TextUtils.prepare(line);
            if ((line != null) && (line.charAt(0) == '[')) {
              list.add(line);
            }
          }
        }
      }
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine(
          "Finished downloading and processing the list of files to install from '" //$NON-NLS-1$
              + fileListUrl + "'; found " + //$NON-NLS-1$
              list.size()
              + " possible entries, which will now be processed one-by-one.");//$NON-NLS-1$
    }

    counter = 0;
    for (final String string : list) {
      if (this.__process(string, exampleUri, destDirectory,
          (counter <= 0))) {
        ++counter;
      }
    }

    if (logger != null) {
      if (counter > 0) {
        if (logger.isLoggable(this.m_successLevel)) {
          logger.log(this.m_successLevel, //
              "Successfully finished downloading " + //$NON-NLS-1$
                  counter + " resources and installing " + //$NON-NLS-1$
                  name);
        }
      } else {
        if (logger.isLoggable(this.m_failureLevel)) {
          logger.log(this.m_failureLevel, //
              "No resources for the required version '" + //$NON-NLS-1$
                  this.m_version + //
                  "' found, nothing was installed for example " + //$NON-NLS-1$
                  name);
        }
      }
    }

    return ((counter > 0) ? destDirectory : null);
  }

  /**
   * process a given line
   *
   * @param line
   *          the line
   * @param baseUri
   *          the base uri
   * @param destFolder
   *          the destination folder
   * @param found
   *          did we already create something?
   * @return {@code true} if a file was unpacked, {@code false} otherwise
   * @throws Exception
   *           if something goes wrong
   */
  private final boolean __process(final String line, final URI baseUri,
      final Path destFolder, final boolean found) throws Exception {
    final Logger logger;
    final URL url;
    final Path destPath;
    final boolean zip;
    String path;
    int index;
    char ch;

    path = this._getVersionResource(line);
    if (path == null) {
      return false;
    }

    logger = this.getLogger();
    url = baseUri.resolve(path).toURL();
    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Now downloading resource '" + url) + //$NON-NLS-1$
          +'\'') + '.');
    }

    if (!(found)) {
      Files.createDirectories(destFolder);
    }

    index = path.length();
    zip = ((index >= 4) && //
        (((ch = path.charAt(--index)) == 'p') || (ch == 'P')) && //
        (((ch = path.charAt(--index)) == 'i') || (ch == 'I')) && //
        (((ch = path.charAt(--index)) == 'z') || (ch == 'Z')) && //
        ((ch = path.charAt(--index)) == '.'));

    index = path.lastIndexOf('/');
    if (index > 0) {
      path = path.substring(index + 1);
    }
    if (zip) {
      destPath = destFolder;
    } else {
      destPath = PathUtils.createPathInside(destFolder, path);
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Finished downloading resource '" + url + //$NON-NLS-1$
          (zip ? "' as zip archive into folder '"//$NON-NLS-1$
              : "' as file into file '")//$NON-NLS-1$
          + destPath) + '\'') + '.');
    }

    try (final InputStream input = url.openStream()) {
      if (zip) {
        EArchiveType.ZIP.decompressStreamToFolder(input, destPath, path);
      } else {
        Files.copy(input, destPath);
      }
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Finished downloading resource '" + url) //$NON-NLS-1$
          + '\'') + '.');
    }

    return true;
  }
}
