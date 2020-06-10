/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import io.github.mmm.ui.api.controller.UiEmbedding;
import io.github.mmm.ui.api.controller.UiPlace;
import io.github.mmm.ui.api.widget.UiRegularWidget;

/**
 * {@link AbstractUiController} for {@link #ID_NAVIGATION navigation}.
 *
 * @param <W> type of the {@link #getView() view}.
 * @since 1.0.0
 */
public abstract class AbstractUiControllerNavigation<W extends UiRegularWidget> extends AbstractUiController<W> {

  @Override
  public String getId() {

    return ID_NAVIGATION;
  }

  @Override
  public String getTitle() {

    return "Navigation";
  }

  @Override
  protected UiEmbedding doShow(UiPlace newPlace, UiEmbedding newSlot) {

    return UiEmbedding.NAVIGATION;
  }

}
