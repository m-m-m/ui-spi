/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.widget;

import io.github.mmm.ui.api.datatype.UiStyles;
import io.github.mmm.ui.api.widget.UiNativeWidget;
import io.github.mmm.ui.spi.UiStylesImpl;

/**
 * Abstract base implementation of {@link UiNativeWidget} implementing {@link #getStyles()}.
 *
 * @param <W> type of the {@link #getWidget() widget}.
 * @since 1.0.0
 */
public abstract class AbstractUiNativeWidgetWrapper<W> extends AbstractUiNativeWidget
    implements UiNativeWidgetWrapper<W> {

  private String id;

  private UiStyles styles;

  private String tooltip;

  /**
   * The constructor.
   */
  public AbstractUiNativeWidgetWrapper() {

    super();
  }

  @Override
  public UiStyles getStyles() {

    if (this.styles == null) {
      this.styles = createStyles();
    }
    return this.styles;
  }

  /**
   * @return the {@link UiStyles} implementation used by {@link #getStyles()}.
   */
  protected UiStyles createStyles() {

    return new Styles();
  }

  /**
   * @return the {@link UiStyles} implementation used by {@link #getStyles()}.
   */
  protected final UiStyles createDefaultStyles() {

    return new Styles();
  }

  /**
   * @param newStyles the new styles.
   */
  protected void onStylesChanged(String newStyles) {

    // nothing by default
  }

  @Override
  public final String getTooltip() {

    return this.tooltip;
  }

  @Override
  public final void setTooltip(String tooltip) {

    setTooltipNative(tooltip);
    this.tooltip = tooltip;
  }

  /**
   * @param newTooltip the new tooltip to apply.
   */
  protected abstract void setTooltipNative(String newTooltip);

  @Override
  public String getId() {

    return this.id;
  }

  @Override
  public void setId(String id) {

    this.id = id;
    setIdNative(id);
  }

  /**
   * @param id the new {@link #getId() ID}.
   */
  protected abstract void setIdNative(String id);

  private class Styles extends UiStylesImpl {

    @Override
    protected void onStylesChanged() {

      AbstractUiNativeWidgetWrapper.this.onStylesChanged(get());
    }
  }
}
