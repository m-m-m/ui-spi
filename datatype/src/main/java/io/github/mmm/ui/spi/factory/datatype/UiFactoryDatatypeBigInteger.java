/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.factory.datatype;

import java.math.BigInteger;

import io.github.mmm.ui.api.factory.UiSingleWidgetFactoryDatatype;
import io.github.mmm.ui.api.widget.input.UiInput;
import io.github.mmm.ui.api.widget.number.UiBigIntegerInput;

/**
 * Implementation of {@link UiSingleWidgetFactoryDatatype} for type {@link BigInteger}.
 *
 * @since 1.0.0
 */
public class UiFactoryDatatypeBigInteger implements UiSingleWidgetFactoryDatatype<BigInteger> {

  @Override
  public Class<BigInteger> getType() {

    return BigInteger.class;
  }

  @Override
  public UiInput<BigInteger> create() {

    return UiBigIntegerInput.of(null);
  }

}
