/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi;

import java.util.Objects;
import java.util.function.Function;

import io.github.mmm.base.lang.ToStringFormatter;

/**
 * Formatter {@link Function} that delegates to {@link Object#toString()} including localization. Use {@link #get() this
 * formatter} as default with option to override in order to give flexiblity.
 *
 * @param <V> type of the value to {@link #apply(Object) format}.
 * @since 1.0.0
 */
public final class DefaultFormatter<V> implements Function<V, String> {

  @SuppressWarnings("rawtypes")
  private static final DefaultFormatter INSTANCE = new DefaultFormatter<>();

  private DefaultFormatter() {

    super();
  }

  @Override
  public String apply(V t) {

    if (Boolean.TRUE.equals(t)) {
      return "yes";
    } else if (Boolean.FALSE.equals(t)) {
      return "no";
    }
    return Objects.toString(t);
  }

  /**
   * @param <V> type of the value to {@link #apply(Object) format}.
   * @return the singleton instance of {@link ToStringFormatter}.
   */
  public static <V> DefaultFormatter<V> get() {

    return INSTANCE;
  }

}
