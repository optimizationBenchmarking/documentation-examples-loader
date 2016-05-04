package org.optimizationBenchmarking.documentation.examples;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.spec.ICallableToolJob;

/** The builder for example download jobs. */
public final class ExampleDownloadJobBuilder extends
    ExampleJobBuilderBase<Path, _ExampleDownloadJob, ExampleDownloadJobBuilder> {
  /** the parameter for the example id */
  public static final String PARAM_EXAMPLE = "example"; //$NON-NLS-1$
  /** the parameter for the destination dir */
  public static final String PARAM_DEST = "dest"; //$NON-NLS-1$

  /** the example's id */
  String m_id;
  /** the destination directory */
  Path m_dest;

  /** create the job builder */
  ExampleDownloadJobBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public ExampleDownloadJobBuilder configure(final Configuration config) {
    final ExampleDownloadJobBuilder res;
    String string;
    Path path;

    res = super.configure(config);

    string = config.getString(ExampleDownloadJobBuilder.PARAM_EXAMPLE,
        this.m_id);
    if (string != null) {
      this.setExampleId(string);
    }

    path = config.getPath(ExampleDownloadJobBuilder.PARAM_DEST,
        this.m_dest);
    if (path != null) {
      this.setDestinationPath(path);
    }

    return res;
  }

  /**
   * Check an example id
   *
   * @param id
   *          the example id
   * @return the processed example id
   */
  static final String _checkExampleId(final String id) {
    final String res;

    res = TextUtils.prepare(id);
    if (res == null) {
      throw new IllegalArgumentException(//
          "Example ID cannot be empty, null, or just consist of white spaces, but '" //$NON-NLS-1$
              + id + "' is/does.");//$NON-NLS-1$
    }
    if (res.indexOf('/') >= 0) {
      throw new IllegalArgumentException(//
          "Example ID cannot contain '/', but '" //$NON-NLS-1$
              + id + "' does.");//$NON-NLS-1$
    }
    return res;
  }

  /**
   * Set the example id
   *
   * @param id
   *          the example id
   * @return this builder
   */
  public final ExampleDownloadJobBuilder setExampleId(final String id) {
    this.m_id = ExampleDownloadJobBuilder._checkExampleId(id);
    return this;
  }

  /**
   * Check the destination path
   *
   * @param path
   *          the destination path
   * @return the checked/normalize path
   */
  static final Path _checkDestinationPath(final Path path) {
    return PathUtils.normalize(path);
  }

  /**
   * Set the example destination path
   *
   * @param path
   *          the path
   * @return the download job builder
   */
  public final ExampleDownloadJobBuilder setDestinationPath(
      final Path path) {
    this.m_dest = ExampleDownloadJobBuilder._checkDestinationPath(path);
    return this;
  }

  /**
   * Set the example destination path
   *
   * @param path
   *          the path
   * @return the download job builder
   */
  public final ExampleDownloadJobBuilder setDestinationPath(
      final String path) {
    return this.setDestinationPath(PathUtils.normalize(path));
  }

  /** {@inheritDoc} */
  @Override
  final ICallableToolJob<Path> _create() {
    return new _ExampleDownloadJob(this);
  }
}
