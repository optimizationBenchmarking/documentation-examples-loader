package org.optimizationBenchmarking.documentation.examples;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;

/** The main class of the documentation downloader */
public final class Main {

  /** print the help screen */
  private static final String PARAM_HELP = "help";//$NON-NLS-1$

  /** list the examples */
  private static final String PARAM_LIST = "list";//$NON-NLS-1$

  /**
   * The main method
   *
   * @param args
   *          the arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    final Configuration config;
    final ArrayListView<Example> list;
    final AbstractTextOutput out;
    Logger logger;
    Handler[] handlers;

    Configuration.setup(args);

    config = Configuration.getRoot();
    if (config.getBoolean(Main.PARAM_HELP, false)) {
      System.out.println("Example Downloader/Lister"); //$NON-NLS-1$
      System.out.println(
          "java -jar documentation-examples-loader-full.jar args");//$NON-NLS-1$

      System.out.print('-');
      System.out.print(Main.PARAM_HELP);
      System.out.println(": print this screen");//$NON-NLS-1$

      System.out.print('-');
      System.out.print(ExampleJobBuilderBase.PARAM_BASE_URI);
      System.out.print(
          "=<uri>: base URI of repositors (if omitted defaults to ");//$NON-NLS-1$
      System.out.print(ExampleJobBuilderBase.DEFAULT_BASE_URI);
      System.out.println(')');

      System.out.print('-');
      System.out.print(ExampleJobBuilderBase.PARAM_VERSION);
      System.out.print(
          "=<?.?.?>: version of software (if omitted, all versions are allowed)");//$NON-NLS-1$

      System.out.print('-');
      System.out.print(Main.PARAM_LIST);
      System.out
          .println(": list available examples (for specified version)");//$NON-NLS-1$

      System.out.print('-');
      System.out.print(ExampleDownloadJobBuilder.PARAM_EXAMPLE);
      System.out.println("=<id>: id of example to download");//$NON-NLS-1$

      System.out.print('-');
      System.out.print(ExampleDownloadJobBuilder.PARAM_DEST);
      System.out.println("=<path>: destination path to download to");//$NON-NLS-1$
      return;
    }

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

    if (config.getBoolean(Main.PARAM_LIST, false)) {
      list = ExampleListTool.getInstance().use()
          .configure(Configuration.getRoot()).create().call();

      if (!(list.isEmpty())) {
        System.out.flush();
        System.err.flush();
        out = AbstractTextOutput.wrap(System.out);
        for (final Example example : list) {
          out.appendLineBreak();
          out.appendLineBreak();
          example.toText(out);
        }
      }

      return;
    }

    ExampleDownloadTool.getInstance().use()
        .configure(Configuration.getRoot()).create().call();
  }
}
