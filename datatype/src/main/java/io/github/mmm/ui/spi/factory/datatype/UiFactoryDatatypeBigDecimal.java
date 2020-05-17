/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.factory.datatype;

import java.math.BigDecimal;

import io.github.mmm.ui.api.factory.UiSingleWidgetFactoryDatatype;
import io.github.mmm.ui.api.widget.input.UiInput;
import io.github.mmm.ui.api.widget.number.UiBigDecimalInput;

/**
 * Implementation of {@link UiSingleWidgetFactoryDatatype} for type {@link BigDecimal}.
 *
 * @since 1.0.0
 */
public class UiFactoryDatatypeBigDecimal implements UiSingleWidgetFactoryDatatype<BigDecimal> {

  @Override
  public Class<BigDecimal> getType() {

    return BigDecimal.class;
  }

  @Override
  public UiInput<BigDecimal> create() {

    return UiBigDecimalInput.of(null);
  }

}
