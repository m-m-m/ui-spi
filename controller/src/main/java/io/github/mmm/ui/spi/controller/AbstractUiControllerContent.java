/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import io.github.mmm.ui.api.controller.UiEmbedding;
import io.github.mmm.ui.api.controller.UiPlace;
import io.github.mmm.ui.api.widget.UiRegularWidget;

/**
 * {@link AbstractUiController} for {@link #ID_CONTENT content}.
 *
 * @param <V> type of the {@link #getView() view}.
 * @since 1.0.0
 */
public abstract class AbstractUiControllerContent<V extends UiRegularWidget> extends AbstractUiController<V> {

  @Override
  public String getId() {

    return ID_CONTENT;
  }

  @Override
  public String getTitle() {

    return "Content";
  }

  @Override
  protected UiEmbedding doShow(UiPlace newPlace, UiEmbedding newSlot) {

    return UiEmbedding.CONTENT;
  }

}
