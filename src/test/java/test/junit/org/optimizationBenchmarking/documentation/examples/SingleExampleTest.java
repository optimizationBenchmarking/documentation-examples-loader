package test.junit.org.optimizationBenchmarking.documentation.examples;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.optimizationBenchmarking.documentation.examples.ExampleDownloadJobBuilder;
import org.optimizationBenchmarking.documentation.examples.ExampleDownloadTool;
import org.optimizationBenchmarking.evaluator.data.impl.ref.ExperimentSetContext;
import org.optimizationBenchmarking.evaluator.data.spec.IDimension;
import org.optimizationBenchmarking.evaluator.data.spec.IExperiment;
import org.optimizationBenchmarking.evaluator.data.spec.IExperimentSet;
import org.optimizationBenchmarking.evaluator.data.spec.IFeature;
import org.optimizationBenchmarking.evaluator.data.spec.IFeatureValue;
import org.optimizationBenchmarking.evaluator.data.spec.IInstance;
import org.optimizationBenchmarking.evaluator.data.spec.IInstanceRuns;
import org.optimizationBenchmarking.evaluator.data.spec.IParameter;
import org.optimizationBenchmarking.evaluator.data.spec.IParameterValue;
import org.optimizationBenchmarking.evaluator.data.spec.IRun;
import org.optimizationBenchmarking.evaluator.io.impl.ExperimentSetInputParser;
import org.optimizationBenchmarking.evaluator.io.spec.IExperimentSetInput;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.config.ConfigurationBuilder;
import org.optimizationBenchmarking.utils.config.ConfigurationXMLInput;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOTool;
import org.optimizationBenchmarking.utils.versioning.Version;

import shared.junit.CategorySlowTests;
import shared.junit.TestBase;

/** A base class for testing example installations. */
@Ignore
public abstract class SingleExampleTest
    implements Callable<IExperimentSet> {

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
   * load the example for the given version
   *
   * @param major
   *          the major version
   * @param minor
   *          the minor version
   * @param build
   *          the build number
   * @param path
   *          the path
   * @return the path used
   * @throws Exception
   *           if something fails
   */
  private final Path __load(final int major, final int minor,
      final int build, final Path path) throws Exception {
    ExampleDownloadJobBuilder builder;
    Path ret;

    builder = ExampleDownloadTool.getInstance().use();
    this.setup(builder);
    ret = builder.setLogger(TestBase.getNullLogger())//
        .setVersion(new Version(major, minor, build, null, null))//
        .setDestinationPath(path)//
        .create().call();
    Assert.assertNotNull(ret);
    return ret;
  }

  /**
   * test the example download for version 0.8.5
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  public void testExampleDownload_0_8_5() throws Exception {
    try (final TempDir temp = new TempDir()) {
      this.__load(0, 8, 5, temp.getPath());
    }
  }

  /**
   * test the example download for version 0.8.6
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testExampleDownload_0_8_6() throws Exception {
    try (final TempDir temp = new TempDir()) {
      this.__load(0, 8, 6, temp.getPath());
    }
  }

  /**
   * test the example download for version 0.8.7
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testExampleDownload_0_8_7() throws Exception {
    try (final TempDir temp = new TempDir()) {
      this.__load(0, 8, 7, temp.getPath());
    }
  }

  /**
   * test the example download for version 0.8.8
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  public void testExampleDownload_0_8_8() throws Exception {
    try (final TempDir temp = new TempDir()) {
      this.__load(0, 8, 8, temp.getPath());
    }
  }

  /**
   * test the example download for version 0.8.5
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testExampleDownloadAndLoad_0_8_5() throws Exception {
    this.__load(0, 8, 5);
  }

  /**
   * test the example download for version 0.8.6
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testExampleDownloadAndLoad_0_8_6() throws Exception {
    this.__load(0, 8, 6);
  }

  /**
   * test the example download for version 0.8.7
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testExampleDownloadAndLoad_0_8_7() throws Exception {
    this.__load(0, 8, 7);
  }

  /**
   * test the example download for version 0.8.8
   *
   * @throws Exception
   *           if I/O fails
   */
  @Test(timeout = 3600000)
  @Category(CategorySlowTests.class)
  public void testExampleDownloadAndLoad_0_8_8() throws Exception {
    this.__load(0, 8, 8);
  }

  /** {@inheritDoc} */
  @Override
  public final IExperimentSet call() throws Exception {
    return this.__load(0, 8, 8);
  }

  /**
   * load an experiment set
   *
   * @param major
   *          the major version
   * @param minor
   *          the minor version
   * @param build
   *          the build number
   * @return the experiment set
   * @throws Exception
   *           if something goes wrong
   */
  private final IExperimentSet __load(final int major, final int minor,
      final int build) throws Exception {
    final Path dir;
    final __Visitor visitor;
    final IExperimentSetInput input;
    final Configuration config;
    final IExperimentSet data;

    try (final TempDir temp = new TempDir()) {
      dir = this.__load(0, 8, 8, temp.getPath());

      visitor = new __Visitor();
      Files.walkFileTree(dir, visitor);
      Assert.assertNotNull(visitor.m_config);

      try (ConfigurationBuilder cfb = new ConfigurationBuilder()) {
        ConfigurationXMLInput.getInstance().use().setBasePath(dir)//
            .setLogger(TestBase.getNullLogger())//
            .setDestination(cfb).addPath(visitor.m_config)//
            .create().call();
        config = cfb.getResult();
      }
      Assert.assertNotNull(config);

      input = config.get((IOTool.INPUT_PARAM_PREFIX + "Driver"), //$NON-NLS-1$
          ExperimentSetInputParser.getInstance(), null);
      Assert.assertNotNull(input);

      try (
          final ExperimentSetContext context = new ExperimentSetContext()) {
        input.use().configure(config).setBasePath(dir)//
            .setLogger(TestBase.getNullLogger())//
            .setDestination(context).create().call();
        data = context.create();
      }

      Assert.assertNotNull(data);

      return data;
    }
  }

  /**
   * Print the infos
   *
   * @throws Exception
   *           if something goes wrong
   */
  public final void printInfos() throws Exception {
    final IExperimentSet data;
    char ch;
    data = this.call();

    System.out.print("Experiments: "); //$NON-NLS-1$
    System.out.println(data.getData().size());
    System.out.print("Experiments-list"); //$NON-NLS-1$
    ch = ':';
    for (final IExperiment item : data.getData()) {
      System.out.print(ch);
      ch = ',';
      System.out.print(' ');
      System.out.print(item.getName());
    }
    System.out.println();
    System.out.println();
    System.out.println();

    System.out.print("Instances: "); //$NON-NLS-1$
    System.out.println(data.getInstances().getData().size());
    System.out.print("Instances-list"); //$NON-NLS-1$
    ch = ':';
    for (final IInstance item : data.getInstances().getData()) {
      System.out.print(ch);
      ch = ',';
      System.out.print(' ');
      System.out.print(item.getName());
    }
    System.out.println();
    System.out.println();
    System.out.println();

    System.out.print("Parameters: "); //$NON-NLS-1$
    System.out.println(data.getParameters().getData().size());
    System.out.print("Parameters-list"); //$NON-NLS-1$
    ch = ':';
    for (final IParameter item : data.getParameters().getData()) {
      System.out.print(ch);
      ch = ',';
      System.out.print(' ');
      System.out.print(item.getName());
    }
    System.out.println();
    System.out.println();
    System.out.println();

    System.out.print("Features: "); //$NON-NLS-1$
    System.out.println(data.getFeatures().getData().size());
    System.out.print("Features-list"); //$NON-NLS-1$
    ch = ':';
    for (final IFeature item : data.getFeatures().getData()) {
      System.out.print(ch);
      ch = ',';
      System.out.print(' ');
      System.out.print(item.getName());
    }
    System.out.println();
    System.out.println();
    System.out.println();

    System.out.print("Dimensions: "); //$NON-NLS-1$
    System.out.println(data.getDimensions().getData().size());
    System.out.print("Dimensions-list"); //$NON-NLS-1$
    ch = ':';
    for (final IDimension item : data.getDimensions().getData()) {
      System.out.print(ch);
      ch = ',';
      System.out.print(' ');
      System.out.print(item.getName());
    }
    System.out.println();
    System.out.println();
    System.out.println();

    System.out.println("--- Parameter-values ---"); //$NON-NLS-1$
    for (final IParameter item : data.getParameters().getData()) {
      System.out.print(item.getName());
      System.out.print(' ');
      System.out.print('(');
      System.out.print(item.getData().size());
      System.out.print(')');

      ch = ':';
      for (final IParameterValue value : item.getData()) {
        System.out.print(ch);
        ch = ',';
        System.out.print(' ');
        System.out.print(value.getName());
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
    System.out.println();

    System.out.println("--- Feature-values ---"); //$NON-NLS-1$
    for (final IFeature item : data.getFeatures().getData()) {
      System.out.print(item.getName());
      System.out.print(' ');
      System.out.print('(');
      System.out.print(item.getData().size());
      System.out.print(')');

      ch = ':';
      for (final IFeatureValue value : item.getData()) {
        System.out.print(ch);
        ch = ',';
        System.out.print(' ');
        System.out.print(value.getName());
      }
      System.out.println();
    }

    System.out.println();
    System.out.println();
    System.out.println();

    System.out.println("--- runs ---"); //$NON-NLS-1$
    long totalRuns, totalDataPoints, totalInstanceRuns, instanceRuns,
        dataPoints, runs;

    totalRuns = totalDataPoints = totalInstanceRuns = instanceRuns = dataPoints = runs = 0L;
    for (final IExperiment item : data.getData()) {
      System.out.print(item.getName());
      System.out.print(':');
      System.out.print(' ');
      instanceRuns = item.getData().size();
      System.out.print(instanceRuns);
      System.out.print(" instances, "); //$NON-NLS-1$
      dataPoints = runs = 0L;
      for (final IInstanceRuns xruns : item.getData()) {
        runs += xruns.getData().size();
        for (final IRun run : xruns.getData()) {
          dataPoints += run.m();
        }
      }
      System.out.print(runs);
      System.out.print(" runs, "); //$NON-NLS-1$
      System.out.print(dataPoints);
      System.out.println(" points"); //$NON-NLS-1$
      totalRuns += runs;
      totalInstanceRuns += instanceRuns;
      totalDataPoints += dataPoints;
    }
    System.out.print("total: "); //$NON-NLS-1$
    System.out.print(totalInstanceRuns);
    System.out.print(" instances, "); //$NON-NLS-1$
    System.out.print(totalRuns);
    System.out.print(" runs, "); //$NON-NLS-1$
    System.out.print(totalDataPoints);
    System.out.print(" points"); //$NON-NLS-1$
  }

  /** the visitor */
  private class __Visitor extends SimpleFileVisitor<Path> {

    /** the configuration */
    Path m_config;

    /** create */
    __Visitor() {
      super();
    }

    /** {@inheritDoc} */
    @Override
    public FileVisitResult visitFile(final Path file,
        final BasicFileAttributes attrs) throws IOException {
      FileVisitResult superRes;
      String name;

      superRes = super.visitFile(file, attrs);
      if (superRes == FileVisitResult.CONTINUE) {
        if (attrs.isRegularFile()) {
          if ((attrs.size() > 100L) && (attrs.size() < 10_000_000L)) {
            name = PathUtils.getName(file).toLowerCase();
            if (name != null) {
              if (name.startsWith("config") && //$NON-NLS-1$
                  name.endsWith(".xml")) {//$NON-NLS-1$
                this.m_config = file;
                return FileVisitResult.TERMINATE;
              }
            }
          }
        }
      }
      return superRes;
    }
  }
}
