package org.optimizationBenchmarking.documentation.examples;

import java.net.URI;

import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.Textable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The example descriptor */
public final class Example extends Textable {

  /** the base uri */
  private final URI m_baseUri;

  /** the id of the example */
  private final String m_id;

  /** the human-readable name of the example */
  private final String m_name;

  /** the description of the example */
  private final String m_description;

  /**
   * Create the example record
   *
   * @param baseUri
   *          the base uri
   * @param id
   *          the example ID
   * @param name
   *          the example name
   * @param description
   *          the example's description
   */
  public Example(final URI baseUri, final String id, final String name,
      final String description) {
    super();
    final String p;
    String desc;

    this.m_baseUri = ExampleJobBuilderBase._checkBaseUri(baseUri);
    this.m_id = ExampleDownloadJobBuilder._checkExampleId(id);

    if ((this.m_name = TextUtils.prepare(name)) == null) {
      throw new IllegalArgumentException(((((((//
      "Example name cannot be null, empty, or just consist of white spaces, but '" //$NON-NLS-1$
          + name) + "' is/does (for example '") + //$NON-NLS-1$
          this.m_id) + "' in repository '") + //$NON-NLS-1$
          this.m_baseUri) + '\'') + '.');
    }

    if ((desc = TextUtils.prepare(description)) == null) {
      throw new IllegalArgumentException(((((((//
      "Example description cannot be null, empty, or just consist of white spaces, but '" //$NON-NLS-1$
          + description) + "' is/does (for example '") + //$NON-NLS-1$
          this.m_id) + "' in repository '") + //$NON-NLS-1$
          this.m_baseUri) + '\'') + '.');
    }

    p = "<p>"; //$NON-NLS-1$
    if (!desc.startsWith(p)) {
      desc = (p + desc + "</p>"); //$NON-NLS-1$
    }

    this.m_description = desc;
  }

  /**
   * Get the base URI of the example repository
   *
   * @return the base URI of the example repository
   */
  public final URI getBaseUri() {
    return this.m_baseUri;
  }

  /**
   * Get the example's id
   *
   * @return the example's id
   */
  public final String getId() {
    return this.m_id;
  }

  /**
   * Get the URI where we can obtain further information about the example
   *
   * @return the info uri
   */
  public final URI getInfoUri() {
    return _ExampleDownloadJob._resolveExampleURI(this.m_baseUri,
        this.m_id);
  }

  /**
   * Get the example's name
   *
   * @return the example's name
   */
  public final String getName() {
    return this.m_name;
  }

  /**
   * Get the example's description
   *
   * @return the example's description
   */
  public final String getDescription() {
    return this.m_description;
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    textOut.append(this.m_baseUri);
    textOut.append(':');
    textOut.append(this.m_id);
    textOut.append(' ');
    textOut.append('-');
    textOut.append(' ');
    textOut.append(this.m_name);
    textOut.appendLineBreak();
    textOut.append(this.m_description);
  }
}
