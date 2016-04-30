package test.junit.org.optimizationBenchmarking.documentation.examples;

import org.optimizationBenchmarking.documentation.examples.ExampleDownloadJobBuilder;

/**
 * The test of downloading and installing the maximum satisfiability
 * example
 */
public final class MaxSatExampleTest extends SingleExampleTest {

  /** create */
  public MaxSatExampleTest() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void setup(final ExampleDownloadJobBuilder builder) {
    builder.setExampleId("maxSat"); //$NON-NLS-1$
  }
}
