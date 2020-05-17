/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.factory.datatype;

import io.github.mmm.ui.api.factory.UiSingleWidgetFactoryDatatype;
import io.github.mmm.ui.api.widget.input.UiInput;
import io.github.mmm.ui.api.widget.number.UiDoubleInput;

/**
 * Implementation of {@link UiSingleWidgetFactoryDatatype} for type {@link Double}.
 *
 * @since 1.0.0
 */
public class UiFactoryDatatypeDouble implements UiSingleWidgetFactoryDatatype<Double> {

  @Override
  public Class<Double> getType() {

    return Double.class;
  }

  @Override
  public UiInput<Double> create() {

    return UiDoubleInput.of(null);
  }

}
