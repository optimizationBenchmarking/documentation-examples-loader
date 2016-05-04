package org.optimizationBenchmarking.documentation.examples;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.tools.spec.ICallableToolJob;

/** The builder for example list jobs. */
public final class ExampleListJobBuilder extends
    ExampleJobBuilderBase<ArrayListView<Example>, _ExampleListJob, ExampleListJobBuilder> {

  /** create the job builder */
  ExampleListJobBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  final ICallableToolJob<ArrayListView<Example>> _create() {
    return new _ExampleListJob(this);
  }
}
