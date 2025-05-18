/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.range;

import io.github.mmm.base.number.NumberType;
import io.github.mmm.base.range.NumberRangeBean;
import io.github.mmm.base.range.WritableRange;
import io.github.mmm.validation.Validator;

/**
 * Implementation of {@link WritableRange} for {@code UiNumericInput}.
 *
 * @param <V> type of the contained values.
 * @since 1.0.0
 */
public class NumericRange<V extends Number & Comparable<?>> extends NumberRangeBean<V> {

  private V minFromValidator;

  private V maxFromValidator;

  /**
   * The constructor.
   *
   * @param type the {@link NumberType}.
   */
  public NumericRange(NumberType<V> type) {

    super(type);
  }

  /**
   * The constructor.
   *
   * @param type the {@link #getType() number type}.
   * @param min the {@link #getMin() minimum}.
   * @param max the {@link #getMax() maximum}.
   */
  public NumericRange(NumberType<V> type, V min, V max) {

    super(type, min, max);
  }

  @Override
  public V getMin() {

    V min = super.getMin();
    if (min == null) {
      min = this.minFromValidator;
    }
    return min;
  }

  @Override
  public V getMax() {

    V max = super.getMax();
    if (max == null) {
      max = this.maxFromValidator;
    }
    return max;
  }

  /**
   * @param validator the {@link Validator} to apply.
   */
  public void setValidator(Validator<? super V> validator) {

    if (validator != null) {
      NumberType<V> type = getType();
      if (type == null) {
        this.minFromValidator = validator.getMin();
        this.maxFromValidator = validator.getMax();
      } else {
        this.minFromValidator = type.valueOf((Number) validator.getMin());
        this.maxFromValidator = type.valueOf((Number) validator.getMax());
      }
    } else {
      this.minFromValidator = null;
      this.maxFromValidator = null;
    }
    onValueChange();
  }

}
