package test.junit.org.optimizationBenchmarking.documentation.examples;

import org.optimizationBenchmarking.documentation.examples.ExampleDownloadTool;

import shared.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/** the test of the example download tool */
public class ExampleDownloadToolTest
    extends ToolTest<ExampleDownloadTool> {

  /** create the test */
  public ExampleDownloadToolTest() {
    super(ExampleDownloadTool.getInstance());
  }
}
