/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import io.github.mmm.ui.api.controller.UiController;
import io.github.mmm.ui.api.controller.UiEmbedding;
import io.github.mmm.ui.api.controller.UiPlace;
import io.github.mmm.ui.api.widget.UiRegularWidget;
import io.github.mmm.ui.api.widget.panel.UiBorderPanel;

/**
 * {@link AbstractUiController Controller} for the {@link UiController#ID_PAGE page} dialog.
 *
 * @since 1.0.0
 */
public abstract class AbstractUiControllerPage extends AbstractUiControllerContent<UiBorderPanel> {

  /**
   * The constructor.
   */
  public AbstractUiControllerPage() {

    super();
  }

  @Override
  public String getId() {

    return ID_PAGE;
  }

  @Override
  protected UiEmbedding doShow(UiPlace newPlace, UiEmbedding newSlot) {

    return UiEmbedding.PAGE;
  }

  @Override
  protected UiBorderPanel createView() {

    UiBorderPanel view = UiBorderPanel.of();
    return view;
  }

  @Override
  protected boolean doEmbed(String slotId, UiController<?> childController) {

    UiBorderPanel view = getView();
    UiRegularWidget childView = childController.getView();
    switch (slotId) {
      case ID_CONTENT:
        view.getCenter().setChild(childView);
        break;
      case ID_HEADER:
        view.getTop().setChild(childView);
        break;
      case ID_FOOTER:
        view.getBottom().setChild(childView);
        break;
      case ID_NAVIGATION:
        view.getLeft().setChild(childView);
        break;
      case ID_MARGIN:
        view.getRight().setChild(childView);
        break;
      default:
        return super.doEmbed(slotId, childController);
    }
    return false;
  }

}
