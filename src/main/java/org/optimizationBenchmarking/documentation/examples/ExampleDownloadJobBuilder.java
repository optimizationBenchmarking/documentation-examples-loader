package org.optimizationBenchmarking.documentation.examples;

import java.net.URI;
import java.nio.file.Path;
import java.util.logging.Level;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.parsers.URIParser;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ConfigurableToolJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.ICallableToolJob;
import org.optimizationBenchmarking.utils.versioning.Version;

/** The builder for example download jobs. */
public final class ExampleDownloadJobBuilder extends
    ConfigurableToolJobBuilder<ICallableToolJob<Path>, ExampleDownloadJobBuilder> {

  /** the base URI */
  public static final String DEFAULT_BASE_URI = "https://raw.githubusercontent.com/optimizationBenchmarking/documentation-examples/master/data/"; //$NON-NLS-1$

  /** the parameter for the base uri of the example repository */
  public static final String PARAM_BASE_URI = "baseUri"; //$NON-NLS-1$
  /** the parameter for the example id */
  public static final String PARAM_EXAMPLE = "example"; //$NON-NLS-1$
  /** the parameter for the destination dir */
  public static final String PARAM_DEST = "dest"; //$NON-NLS-1$
  /** the parameter for the version dir */
  public static final String PARAM_VERSION = "requiredVersion"; //$NON-NLS-1$

  /** the base uri */
  URI m_baseUri;
  /** the example's id */
  String m_id;
  /** the destination directory */
  Path m_dest;
  /** the version */
  Version m_version;
  /** the logging level to denote success */
  Level m_successLevel;
  /** the logging level to denote failure */
  Level m_failureLevel;

  /** create the job builder */
  ExampleDownloadJobBuilder() {
    super();
    this.m_successLevel = Level.INFO;
    this.m_failureLevel = Level.WARNING;
  }

  /** {@inheritDoc} */
  @Override
  public ExampleDownloadJobBuilder configure(final Configuration config) {
    final ExampleDownloadJobBuilder res;
    String string;
    Path path;
    URI uri;

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

    uri = config.get(ExampleDownloadJobBuilder.PARAM_BASE_URI,
        URIParser.INSTANCE, this.m_baseUri);
    if (uri != null) {
      this.setBaseUri(uri);
    }

    string = config.getString(ExampleDownloadJobBuilder.PARAM_VERSION,
        null);
    if (string != null) {
      this.setVersion(Version.parseVersion(string));
    }

    return res;
  }

  /**
   * check the base uri
   *
   * @param baseUri
   *          the base uri
   * @return the usri to use
   */
  static final URI _checkBaseUri(final URI baseUri) {
    final URI use;

    if (baseUri == null) {
      throw new IllegalArgumentException("Base URI cannot be null."); //$NON-NLS-1$
    }

    try {
      use = baseUri.normalize();
    } catch (final IllegalArgumentException illegal) {
      throw illegal;
    } catch (final Throwable error) {
      throw new IllegalArgumentException("Base uri '" //$NON-NLS-1$
          + baseUri + "' cannot be normalized.", error);//$NON-NLS-1$
    }

    if (!(use.isAbsolute())) {
      throw new IllegalArgumentException(//
          "Base uri must be absolute, but '" //$NON-NLS-1$
              + baseUri + "' is not.");//$NON-NLS-1$
    }
    return use;
  }

  /**
   * check the success level.
   *
   * @param level
   *          the level to check
   */
  static final void _checkSuccessLevel(final Level level) {
    if (level == null) {
      throw new IllegalArgumentException(//
          "Success log level must not be null."); //$NON-NLS-1$
    }
  }

  /**
   * set the success log level
   *
   * @param level
   *          the new success log level
   * @return this builder
   */
  public final ExampleDownloadJobBuilder setSuccessLevel(
      final Level level) {
    ExampleDownloadJobBuilder._checkSuccessLevel(level);
    this.m_successLevel = level;
    return this;
  }

  /**
   * check the failure level.
   *
   * @param level
   *          the level to check
   */
  static final void _checkFailureLevel(final Level level) {
    if (level == null) {
      throw new IllegalArgumentException(//
          "Failure log level must not be null."); //$NON-NLS-1$
    }
  }

  /**
   * set the failure log level
   *
   * @param level
   *          the new failure log level
   * @return this builder
   */
  public final ExampleDownloadJobBuilder setFailureLevel(
      final Level level) {
    ExampleDownloadJobBuilder._checkFailureLevel(level);
    this.m_failureLevel = level;
    return this;
  }

  /**
   * set the base uri
   *
   * @param uri
   *          the new base uri
   * @return this builder
   */
  public final ExampleDownloadJobBuilder setBaseUri(final URI uri) {
    this.m_baseUri = ExampleDownloadJobBuilder._checkBaseUri(uri);
    return this;
  }

  /**
   * set the base uri
   *
   * @param uri
   *          the new base uri
   * @return this builder
   */
  public final ExampleDownloadJobBuilder setBaseUri(final String uri) {
    final String res;
    final URI baseUri;

    res = TextUtils.prepare(uri);
    if (uri == null) {
      throw new IllegalArgumentException(//
          "Base URI string cannot be empty, null, or just consist of white spaces, but '" //$NON-NLS-1$
              + uri + "' is/does.");//$NON-NLS-1$
    }
    try {
      baseUri = new URI(res);
    } catch (final IllegalArgumentException illegal) {
      throw illegal;
    } catch (final Throwable error) {
      throw new IllegalArgumentException("Invalid base uri '" //$NON-NLS-1$
          + uri + '\'' + '.', error);
    }

    return this.setBaseUri(baseUri);
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

  /**
   * Set the version for which we will download the example
   *
   * @param version
   *          the version string
   * @return this builder
   */
  public final ExampleDownloadJobBuilder setVersion(
      final Version version) {
    this.m_version = version;
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final ICallableToolJob<Path> create() throws Exception {
    if (this.m_baseUri == null) {
      this.setBaseUri(ExampleDownloadJobBuilder.DEFAULT_BASE_URI);
    }
    return new _ExampleDownloadJob(this);
  }
}
