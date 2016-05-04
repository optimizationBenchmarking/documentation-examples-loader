package org.optimizationBenchmarking.documentation.examples;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;
import org.optimizationBenchmarking.utils.tools.spec.ICallableToolJob;
import org.optimizationBenchmarking.utils.versioning.Version;

/**
 * The base class for example jobs.
 *
 * @param <T>
 *          the return type
 */
abstract class _ExampleJobBase<T> extends ToolJob
    implements ICallableToolJob<T> {

  /** the base uri */
  final URI m_baseUri;
  /** the version */
  final Version m_version;
  /** the logging level to denote success */
  final Level m_successLevel;
  /** the logging level to denote failure */
  final Level m_failureLevel;

  /**
   * create the job
   *
   * @param builder
   *          the builder
   */
  _ExampleJobBase(final ExampleJobBuilderBase<T, ?, ?> builder) {
    super(builder);

    this.m_baseUri = ExampleJobBuilderBase
        ._checkBaseUri(builder.m_baseUri);
    this.m_version = builder.m_version;
    ExampleJobBuilderBase
        ._checkSuccessLevel(this.m_successLevel = builder.m_successLevel);
    ExampleJobBuilderBase
        ._checkFailureLevel(this.m_failureLevel = builder.m_failureLevel);
  }

  /**
   * Extract the resource part from a given line and return it if the
   * currently set version permits it, return {@code null} otherwise.
   *
   * @param line
   *          the line
   * @return the resource part of the line, or {@code null} if the resource
   *         is not available for the current version
   */
  final String _getVersionResource(final String line) {
    final Logger logger;
    Version version;
    String minVersionString, maxVersionString, resource;
    int index1, index2;

    index1 = line.indexOf(',');
    checkFormat: {
      if (index1 >= 1) {
        minVersionString = TextUtils.prepare(line.substring(1, index1));
        ++index1;
        index2 = line.indexOf(']', index1);
        if (index2 >= index1) {
          maxVersionString = TextUtils
              .prepare(line.substring(index1, index2));
          ++index2;
          if (index2 < line.length()) {
            resource = TextUtils.prepare(line.substring(index2));
            if (resource != null) {
              break checkFormat;
            }
          }
        }
      }
      throw new IllegalArgumentException(("Invalid format: '" //$NON-NLS-1$
          + line)
          + "' should be in the format '[minVersion,maxVersion] resource', where minVersion and maxVersion may be omitted.");//$NON-NLS-1$
    }

    logger = this.getLogger();
    if (this.m_version != null) {
      if (minVersionString != null) {
        version = Version.parseVersion(minVersionString);
        if (this.m_version.compareTo(version) < 0) {
          if ((logger != null) && (logger.isLoggable(Level.FINE))) {
            logger.fine((("Skipping '" + line + //$NON-NLS-1$
                "', as its minimum required version '"//$NON-NLS-1$
                + version
                + "' is bigger than the current software version '" + //$NON-NLS-1$
                this.m_version) + '\'') + '.');
          }
          return null;
        }
      }

      if (maxVersionString != null) {
        version = Version.parseVersion(maxVersionString);
        if (this.m_version.compareTo(version) > 0) {
          if ((logger != null) && (logger.isLoggable(Level.FINE))) {
            logger.fine((("Skipping '" + line + //$NON-NLS-1$
                "', as its maximum permitted version '"//$NON-NLS-1$
                + version
                + "' is smaller than the current software version '" + //$NON-NLS-1$
                this.m_version) + '\'') + '.');
          }
          return null;
        }
      }
    }
    return resource;
  }
}
