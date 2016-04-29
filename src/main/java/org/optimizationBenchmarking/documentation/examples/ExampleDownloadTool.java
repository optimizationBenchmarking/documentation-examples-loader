package org.optimizationBenchmarking.documentation.examples;

import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** The example doanlod tool. */
public final class ExampleDownloadTool extends Tool {

  /** create */
  ExampleDownloadTool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final ExampleDownloadJobBuilder use() {
    return new ExampleDownloadJobBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Example Download Tool"; //$NON-NLS-1$
  }

  /**
   * Get the instance of the example downloader tool
   *
   * @return the instance of the example downloader tool
   */
  public static final ExampleDownloadTool getInstance() {
    return __ExampleDownloadToolHolder.INSTANCE;
  }

  /** the tool instance holder */
  private static final class __ExampleDownloadToolHolder {
    /** the shared instance */
    static final ExampleDownloadTool INSTANCE = new ExampleDownloadTool();
  }
}
