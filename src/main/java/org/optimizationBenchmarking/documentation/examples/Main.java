package org.optimizationBenchmarking.documentation.examples;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;

/** The main class of the documentation downloader */
public final class Main {

  /**
   * The main method
   *
   * @param args
   *          the arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    Logger logger;
    Handler[] handlers;

    Configuration.setup(args);

    logger = Configuration.getGlobalLogger();
    while (logger != null) {
      logger.setFilter(null);
      logger.setLevel(Level.ALL);
      handlers = logger.getHandlers();
      if (handlers != null) {
        for (final Handler handler : handlers) {
          handler.setFilter(null);
          handler.setLevel(Level.ALL);
        }
      }
      logger = logger.getParent();
    }

    ExampleDownloadTool.getInstance().use()
        .configure(Configuration.getRoot()).create().call();
  }
}
