package test.junit.org.optimizationBenchmarking.documentation.examples;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.optimizationBenchmarking.documentation.examples.ExampleDownloadJobBuilder;
import org.optimizationBenchmarking.documentation.examples.ExampleDownloadTool;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.versioning.Version;

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
   * test the example download for version 0.8.5
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  public void testExampleDownload_0_8_5() throws Exception {
    ExampleDownloadJobBuilder builder;

    builder = ExampleDownloadTool.getInstance().use();
    this.setup(builder);
    builder.setLogger(TestBase.getNullLogger());
    builder.setVersion(new Version(0, 8, 5, null, null));

    try (final TempDir temp = new TempDir()) {
      builder.setDestinationPath(temp.getPath());
      Assert.assertNotNull(builder.create().call());
    }
  }

  /**
   * test the example download for version 0.8.6
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  public void testExampleDownload_0_8_6() throws Exception {
    ExampleDownloadJobBuilder builder;

    builder = ExampleDownloadTool.getInstance().use();
    this.setup(builder);
    builder.setLogger(TestBase.getNullLogger());
    builder.setVersion(new Version(0, 8, 6, null, null));

    try (final TempDir temp = new TempDir()) {
      builder.setDestinationPath(temp.getPath());
      Assert.assertNotNull(builder.create().call());
    }
  }
}
