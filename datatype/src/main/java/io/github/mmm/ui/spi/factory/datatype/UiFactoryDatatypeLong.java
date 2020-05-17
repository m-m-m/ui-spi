/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.factory.datatype;

import io.github.mmm.ui.api.factory.UiSingleWidgetFactoryDatatype;
import io.github.mmm.ui.api.widget.input.UiInput;
import io.github.mmm.ui.api.widget.number.UiLongInput;

/**
 * Implementation of {@link UiSingleWidgetFactoryDatatype} for type {@link Long}.
 *
 * @since 1.0.0
 */
public class UiFactoryDatatypeLong implements UiSingleWidgetFactoryDatatype<Long> {

  @Override
  public Class<Long> getType() {

    return Long.class;
  }

  @Override
  public UiInput<Long> create() {

    return UiLongInput.of(null);
  }

}
