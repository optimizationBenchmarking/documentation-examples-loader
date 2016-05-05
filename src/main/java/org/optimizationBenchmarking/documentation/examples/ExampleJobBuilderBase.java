package org.optimizationBenchmarking.documentation.examples;

import java.net.URI;
import java.util.logging.Level;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.parsers.URIParser;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ConfigurableToolJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.ICallableToolJob;
import org.optimizationBenchmarking.utils.versioning.Version;

/**
 * The builder for example download jobs.
 *
 * @param <T>
 *          the return type of the created job
 * @param <JT>
 *          the actual job type
 * @param <JBT>
 *          the actual job builder type
 */
public abstract class ExampleJobBuilderBase<T, JT extends _ExampleJobBase<T>, JBT extends ExampleJobBuilderBase<T, JT, JBT>>
    extends ConfigurableToolJobBuilder<ICallableToolJob<T>, JBT> {

  /** the base URI */
  public static final String DEFAULT_BASE_URI = "http://optimizationBenchmarking.github.io/documentation-examples/"; //$NON-NLS-1$

  /** the parameter for the base uri of the example repository */
  public static final String PARAM_BASE_URI = "baseUri"; //$NON-NLS-1$
  /** the parameter for the version dir */
  public static final String PARAM_VERSION = "requiredVersion"; //$NON-NLS-1$

  /** the base uri */
  URI m_baseUri;
  /** the version */
  Version m_version;
  /** the logging level to denote success */
  Level m_successLevel;
  /** the logging level to denote failure */
  Level m_failureLevel;

  /** create the job builder */
  ExampleJobBuilderBase() {
    super();
    this.m_successLevel = Level.INFO;
    this.m_failureLevel = Level.WARNING;
  }

  /** {@inheritDoc} */
  @Override
  public JBT configure(final Configuration config) {
    final JBT res;
    String string;
    URI uri;

    res = super.configure(config);

    uri = config.get(ExampleJobBuilderBase.PARAM_BASE_URI,
        URIParser.INSTANCE, this.m_baseUri);
    if (uri != null) {
      this.setBaseUri(uri);
    }

    string = config.getString(ExampleJobBuilderBase.PARAM_VERSION, null);
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
  @SuppressWarnings("unchecked")
  public final JBT setSuccessLevel(final Level level) {
    ExampleJobBuilderBase._checkSuccessLevel(level);
    this.m_successLevel = level;
    return ((JBT) this);
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
  @SuppressWarnings("unchecked")
  public final JBT setFailureLevel(final Level level) {
    ExampleJobBuilderBase._checkFailureLevel(level);
    this.m_failureLevel = level;
    return ((JBT) this);
  }

  /**
   * set the base uri
   *
   * @param uri
   *          the new base uri
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  public final JBT setBaseUri(final URI uri) {
    this.m_baseUri = ExampleJobBuilderBase._checkBaseUri(uri);
    return ((JBT) this);
  }

  /**
   * set the base uri
   *
   * @param uri
   *          the new base uri
   * @return this builder
   */
  public final JBT setBaseUri(final String uri) {
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
   * Set the version for which we will download the example
   *
   * @param version
   *          the version string
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  public final JBT setVersion(final Version version) {
    this.m_version = version;
    return ((JBT) this);
  }

  /**
   * create the job
   *
   * @return the job
   */
  abstract ICallableToolJob<T> _create();

  /** {@inheritDoc} */
  @Override
  public final ICallableToolJob<T> create() {
    if (this.m_baseUri == null) {
      this.setBaseUri(ExampleJobBuilderBase.DEFAULT_BASE_URI);
    }
    return this._create();
  }
}
