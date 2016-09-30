package test.junit.org.optimizationBenchmarking.documentation.examples;

import org.junit.experimental.categories.Category;

import shared.junit.CategorySlowTests;
import shared.junit.org.optimizationBenchmarking.evaluator.dataAndIO.ExperimentSetTest;

/**
 * The test of downloading and installing the BBOB example
 */
@Category(CategorySlowTests.class)
public final class TSPSuiteExampleFullTest extends ExperimentSetTest {

  /** create */
  public TSPSuiteExampleFullTest() {
    super(new TSPSuiteExampleTest());
  }
}
