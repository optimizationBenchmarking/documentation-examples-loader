package test.junit.org.optimizationBenchmarking.documentation.examples;

import org.junit.Assert;
import org.junit.Test;
import org.optimizationBenchmarking.documentation.examples.ExampleListTool;

import shared.junit.org.optimizationBenchmarking.utils.tools.ToolTest;

/** the test of the example list tool */
public class ExampleListToolTest extends ToolTest<ExampleListTool> {

  /** create the test */
  public ExampleListToolTest() {
    super(ExampleListTool.getInstance());
  }

  /**
   * test whether the tool produces a non-empty example list if no version
   * limitation is provided
   *
   * @throws Exception
   *           if something goes wrong
   */
  @Test(timeout = 3600000)
  public void testNonEmptyListWithoutVersion() throws Exception {
    Assert.assertFalse(this.getInstance().use().create().call().isEmpty());
  }
}
