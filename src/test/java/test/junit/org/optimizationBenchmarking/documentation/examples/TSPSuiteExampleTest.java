package test.junit.org.optimizationBenchmarking.documentation.examples;

import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.documentation.examples.ExampleDownloadJobBuilder;

import shared.junit.CategorySlowTests;

/**
 * The test of downloading and installing the BBOB example
 */
@Category(CategorySlowTests.class)
public final class TSPSuiteExampleTest extends SingleExampleTest {

  /** create */
  public TSPSuiteExampleTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void setup(final ExampleDownloadJobBuilder builder) {
    builder.setExampleId("tspSuite"); //$NON-NLS-1$
  }

  /**
   * the main method
   *
   * @param args
   *          the arguments
   * @throws Throwable
   *           if something goes wrong
   */
  public static void main(final String[] args) throws Throwable {
    new TSPSuiteExampleTest().printInfos();
  }
}
