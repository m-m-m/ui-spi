/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import io.github.mmm.ui.api.controller.UiController;
import io.github.mmm.ui.api.controller.UiEmbedding;
import io.github.mmm.ui.api.controller.UiPlace;
import io.github.mmm.ui.api.widget.composite.UiSlot;
import io.github.mmm.ui.api.widget.window.UiMainWindow;

/**
 * {@link io.github.mmm.ui.api.controller.UiController} with {@link #getId() ID}
 * {@link io.github.mmm.ui.api.controller.UiController#ID_ROOT root}.
 */
public class UiControllerRoot extends AbstractUiController<UiSlot> {

  private UiSlot slot;

  @Override
  public String getId() {

    return "root"; // ID_ROOT;
  }

  @Override
  public String getTitle() {

    // This title will never be displayed...
    return ID_ROOT;
  }

  @Override
  protected UiSlot createView() {

    if (this.slot == null) {
      this.slot = UiSlot.of(ID_ROOT);
      UiMainWindow.get().addChild(this.slot);
    }
    return this.slot;
  }

  @Override
  protected boolean doEmbed(String slotId, UiController<?> childController) {

    if (ID_PAGE.equals(slotId)) {
      this.slot.setChild(childController.getView());
      return false;
    } else {
      return super.doEmbed(slotId, childController);
    }
  }

  @Override
  protected void onShow() {

    super.onShow();
    UiMainWindow.get().setVisible(true);
  }

  @Override
  protected final UiEmbedding doShow(UiPlace newPlace, UiEmbedding newSlot) {

    return null;
  }

}
