/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.range;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.github.mmm.base.number.NumberType;
import io.github.mmm.base.range.AbstractRange;
import io.github.mmm.base.range.WritableRange;
import io.github.mmm.validation.Validator;

/**
 * Implementation of {@link WritableRange} for {@code UiNumericInput}.
 *
 * @param <V> type of the contained values.
 * @since 1.0.0
 */
public class NumericRange<V extends Number> extends AbstractRange<V> implements WritableRange<V> {

  private final NumberType<V> type;

  private V min;

  private V minValidator;

  private V max;

  private V maxValidator;

  /**
   * The constructor.
   *
   * @param type the {@link NumberType}.
   */
  public NumericRange(NumberType<V> type) {

    super();
    this.type = type;
  }

  @Override
  public V getMin() {

    if (this.min == null) {
      return this.minValidator;
    }
    return this.min;
  }

  @Override
  public void setMin(V min) {

    this.min = min;
    onValueChange();
  }

  @Override
  public V getMax() {

    if (this.max == null) {
      return this.maxValidator;
    }
    return this.max;
  }

  @Override
  public void setMax(V max) {

    this.max = max;
    onValueChange();
  }

  /**
   * @param validator the {@link Validator} to apply.
   */
  public void setValidator(Validator<? super V> validator) {

    if (validator != null) {
      this.minValidator = this.type.valueOf((Number) validator.getMin());
      this.maxValidator = this.type.valueOf((Number) validator.getMax());
    } else {
      this.minValidator = null;
      this.maxValidator = null;
    }
    onValueChange();
  }

  /**
   * @param factor the scaled value in the range from {@code 0} to {@code 1}.
   * @return the value within this range scaled by the given {@code factor}. It will be {@link #getMin() min} in case
   *         the given {@code factor} is {@code 0}, while {@code 0} is used in case {@link #getMin() min} is
   *         {@code null}. In case the {@code factor} is {@code 1}, it will return {@link #getMax() max} or the maximum
   *         value instead of {@code null}. Any other factor is interpolated between {@link #getMin() min} and
   *         {@link #getMax() max}.
   */
  public V fromFactor(double factor) {

    V minimum = getMin();
    if (minimum == null) {
      minimum = this.type.getZero();
    }
    if (factor <= 0) {
      return minimum;
    }
    V maximum = getMax();
    if (maximum == null) {
      maximum = this.type.getMax();
      if (maximum == null) {
        maximum = this.type.valueOf(NumberType.INTEGER.getMax());
      }
    }
    if (factor >= 1) {
      return maximum;
    }
    V delta = this.type.subtract(maximum, minimum);
    V scaled = this.type.multiply(delta, Double.valueOf(factor));
    return this.type.add(scaled, minimum);
  }

  /**
   * @param value the value within this range.
   * @return the scaled value. It will be {@link #getMin() min} in case the given {@code factor} is {@code 0}, while
   *         {@code 0} is used in case {@link #getMin() min} is {@code null}. In case the {@code factor} is {@code 1},
   *         it will return {@link #getMax() max} or the maximum value instead of {@code null}. Any other factor is
   *         interpolated between {@link #getMin() min} and {@link #getMax() max}.
   */
  public double toFactor(V value) {

    V minimum = getMin();
    if (minimum == null) {
      minimum = this.type.getZero();
    }
    V maximum = getMax();
    if (maximum == null) {
      maximum = this.type.getMax();
      if (maximum == null) {
        maximum = this.type.valueOf(NumberType.INTEGER.getMax());
      }
    }
    V delta = this.type.subtract(maximum, minimum);
    V offset = this.type.subtract(value, minimum);
    if (this.type.isDecimal()) {
      return this.type.divide(offset, delta).doubleValue();
    } else if (this.type.getExactness() == NumberType.BIG_INTEGER.getExactness()) {
      return new BigDecimal((BigInteger) offset).divide(new BigDecimal((BigInteger) delta)).doubleValue();
    } else {
      return offset.doubleValue() / delta.doubleValue();
    }
  }

  /**
   * Called whenever {@link #getMin() min} or {@link #getMax() max} changes.
   */
  protected void onValueChange() {

  }

}
