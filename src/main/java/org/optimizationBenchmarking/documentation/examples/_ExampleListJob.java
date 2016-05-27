package org.optimizationBenchmarking.documentation.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * This job downloads a list of jobs from a repository.
 */
final class _ExampleListJob
    extends _ExampleJobBase<ArrayListView<Example>> {

  /** the name of the file list */
  private static final String FILE_LIST_NAME = "exampleList.txt"; //$NON-NLS-1$

  /**
   * create the job
   *
   * @param builder
   *          the builder
   */
  _ExampleListJob(final ExampleListJobBuilder builder) {
    super(builder);
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayListView<Example> call() throws IOException {
    final Logger logger;
    final ArrayListView<Example> result;
    String name;
    URL fileListUrl;
    ArrayList<Example> list;
    String line;
    final int size;

    logger = this.getLogger();

    name = ((("the list of examples from repository '") + //$NON-NLS-1$
        this.m_baseUri) + '\'');
    if (this.m_version != null) {
      name += " for version '" + this.m_version + '\'';//$NON-NLS-1$
    }

    if ((logger != null) && (logger.isLoggable(Level.INFO))) {
      logger.info("Now beginning to download the " + //$NON-NLS-1$
          name + '.');
    }

    fileListUrl = this.m_baseUri.resolve(_ExampleListJob.FILE_LIST_NAME)
        .toURL();
    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer((("The URL of the example list is '" + //$NON-NLS-1$
          fileListUrl) + '\'') + '.');
    }

    list = new ArrayList<>();
    try (final InputStream listStream = fileListUrl.openStream()) {
      try (final InputStreamReader reader = new InputStreamReader(
          listStream)) {
        try (final BufferedReader buffered = new BufferedReader(reader)) {
          while ((line = buffered.readLine()) != null) {
            line = TextUtils.prepare(line);
            if (line != null) {
              line = this._getVersionResource(line);
              if (line != null) {
                list.add(new Example(this.m_baseUri, line,
                    TextUtils.prepare(buffered.readLine()),
                    TextUtils.prepare(buffered.readLine())));
              }
            }
          }
        }
      }
    }

    result = ArrayListView.collectionToView(list);
    list = null;

    size = result.size();
    if (size <= 0) {
      if ((logger != null) && (logger.isLoggable(this.m_failureLevel))) {
        logger.log(this.m_failureLevel, "Finished downloading " + name + //$NON-NLS-1$
            ", but discovered no examples (for the specified software version)."); //$NON-NLS-1$
      }
    } else {
      if ((logger != null) && (logger.isLoggable(this.m_successLevel))) {
        logger.log(this.m_successLevel,
            "Finished downloading " + name + //$NON-NLS-1$
                " and discovered " + size + //$NON-NLS-1$
                " examples for the specified software version."); //$NON-NLS-1$
      }
    }
    return result;
  }
}
