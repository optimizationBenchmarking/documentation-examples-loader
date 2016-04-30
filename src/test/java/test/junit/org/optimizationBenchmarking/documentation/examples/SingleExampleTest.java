package test.junit.org.optimizationBenchmarking.documentation.examples;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.documentation.examples.ExampleDownloadJobBuilder;
import org.optimizationBenchmarking.documentation.examples.ExampleDownloadTool;
import org.optimizationBenchmarking.utils.io.paths.TempDir;

import shared.junit.TestBase;

/** A base class for testing example installations. */
@Ignore
public abstract class SingleExampleTest {

  /** create the installer test */
  protected SingleExampleTest() {
    super();
  }

  /**
   * setup the job builder
   *
   * @param builder
   *          the job builder
   */
  protected abstract void setup(final ExampleDownloadJobBuilder builder);

  /**
   * test the example download
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  public void testExampleDownload() throws Exception {
    ExampleDownloadJobBuilder builder;

    builder = ExampleDownloadTool.getInstance().use();
    this.setup(builder);
    builder.setLogger(TestBase.getNullLogger());

    try (final TempDir temp = new TempDir()) {
      builder.setDestinationPath(temp.getPath());
      Assert.assertNotNull(builder.create().call());
    }
  }
}
