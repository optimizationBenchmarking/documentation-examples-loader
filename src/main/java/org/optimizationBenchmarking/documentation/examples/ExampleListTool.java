package org.optimizationBenchmarking.documentation.examples;

import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** The example lister tool. */
public final class ExampleListTool extends Tool {

  /** create */
  ExampleListTool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final ExampleListJobBuilder use() {
    return new ExampleListJobBuilder();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Example Lister Tool"; //$NON-NLS-1$
  }

  /**
   * Get the instance of the example lister tool
   *
   * @return the instance of the example lister tool
   */
  public static final ExampleListTool getInstance() {
    return __ToolHolder.INSTANCE;
  }

  /** the tool instance holder */
  private static final class __ToolHolder {
    /** the shared instance */
    static final ExampleListTool INSTANCE = new ExampleListTool();
  }
}
