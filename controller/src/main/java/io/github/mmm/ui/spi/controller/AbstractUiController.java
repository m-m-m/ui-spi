/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import java.util.Objects;

import io.github.mmm.base.exception.ObjectMismatchException;
import io.github.mmm.ui.api.UiLocalizer;
import io.github.mmm.ui.api.controller.UiController;
import io.github.mmm.ui.api.controller.UiEmbedding;
import io.github.mmm.ui.api.controller.UiNavigationManager;
import io.github.mmm.ui.api.controller.UiPlace;
import io.github.mmm.ui.api.widget.UiRegularWidget;

/**
 * Abstract base implementation of {@link UiController}.
 *
 * @param <V> type of the {@link #getView() view}.
 * @since 1.0.0
 */
public abstract class AbstractUiController<V extends UiRegularWidget> implements UiController<V> {

  private UiEmbedding embedding;

  private V view;

  /**
   * The constructor.
   */
  public AbstractUiController() {

    super();
  }

  @Override
  public V getView() {

    if (this.view == null) {
      this.view = createView();
    }
    return this.view;
  }

  /**
   * @return the new instance of the {@link #getView() view}.
   */
  protected abstract V createView();

  @Override
  public String getTitle() {

    return UiLocalizer.get().localize(getId());
  }

  /**
   * @return the {@link UiEmbedding} where this controller is {@link #embed(UiEmbedding, UiController) embedded into its
   *         parent controller}.
   */
  protected UiEmbedding getEmbedding() {

    return this.embedding;
  }

  /**
   * Shows this controller (creates/updates and opens its {@link #getView() view}) as triggered by the given
   * {@link UiPlace}.
   *
   * @param newPlace the {@link UiPlace} {@link UiPlace#getId() identifying} this {@link UiController} and providing
   *        potential {@link UiPlace#get(String) parameters}.
   * @return the {@link UiEmbedding} where to embed this controller into a parent controller or {@code null} if this is
   *         the root controller.
   */
  public final UiEmbedding show(UiPlace newPlace) {

    return show(newPlace, null);
  }

  /**
   * Shows this controller (creates/updates and opens its {@link #getView() view}) as triggered by the given
   * {@link UiPlace}.
   *
   * @param newPlace the {@link UiPlace} to open. It {@link UiPlace#getId() identifies} the leaf child
   *        {@link UiController} and provides potential {@link UiPlace#get(String) parameters}.
   * @param newEmbedding the optional {@link UiEmbedding} where to embed the child controller.
   * @return the {@link UiEmbedding} where to embed this controller into a parent controller or {@code null} if this is
   *         the root controller.
   */
  public final UiEmbedding show(UiPlace newPlace, UiEmbedding newEmbedding) {

    Objects.requireNonNull(newPlace, "place");
    String expectedId;
    if (newEmbedding == null) {
      expectedId = newPlace.getId();
    } else {
      expectedId = newEmbedding.getControllerId();
    }
    String controllerId = getId();
    if (!controllerId.equals(expectedId)) {
      throw new ObjectMismatchException(expectedId, controllerId);
    }
    onShow();
    this.embedding = doShow(newPlace, newEmbedding);
    return this.embedding;
  }

  /**
   * @param newPlace the {@link UiPlace} to open. It {@link UiPlace#getId() identifies} the leaf child
   *        {@link UiController} and provides potential {@link UiPlace#get(String) parameters}.
   * @param newSlot the optional {@link UiEmbedding} where to embed the child controller.
   * @return the {@link UiEmbedding} where to embed this controller into a parent controller or {@code null} if this is
   *         the root controller.
   */
  protected abstract UiEmbedding doShow(UiPlace newPlace, UiEmbedding newSlot);

  @Override
  public final void embed(UiEmbedding newEmbedding, UiController<?> childController) {

    boolean dynamic = doEmbed(newEmbedding.getSlotId(), childController);
    if (!dynamic) {
      AbstractUiNavigationManager navigationManager = (AbstractUiNavigationManager) UiNavigationManager.get();
      navigationManager.onEmbedded(newEmbedding, (AbstractUiController<?>) childController);
    }
  }

  /**
   * Override this method for parent controllers to embed children.
   *
   * @param slotId the {@link UiEmbedding#getSlotId() slot ID} where to embed the given child {@link UiController}s
   *        {@link #getView() view}.
   * @param childController the child controller to embed.
   * @return {@code true} if the embedded slot is dynamic (e.g. a new closable tab), {@code false} otherwise if static
   *         and reusable slot in the UI (default).
   * @see #embed(UiEmbedding, UiController)
   */
  protected boolean doEmbed(String slotId, UiController<?> childController) {

    throw new IllegalArgumentException(slotId);
  }

  @Override
  public boolean isVisible() {

    if (this.view != null) {
      return this.view.isVisible();
    }
    return false;
  }

  /**
   * This method resets this {@link AbstractUiController} and disposes its {@link #getView() view}.
   */
  public final void reset() {

    onReset();
    if (this.view != null) {
      // this.view.removeFromParent();
      this.view.dispose();
    }
    this.view = null;
  }

  /**
   * This method is called when this controller was hidden (its {@link #getView() view } is replaced in the slot it was
   * embedded by another controller).
   */
  public final void hide() {

    onHide();
  }

  /**
   * This method gets called whenever this controller is {@link #reset() reseted}. If you keep custom state information
   * you need to override and reset your state.
   */
  protected void onReset() {

    // nothing to add...
  }

  /**
   * This method gets called whenever the {@link #getView() view} is shown on the screen. It can be overridden to
   * trigger custom logic - e.g. to update data.
   */
  protected void onShow() {

    // nothing by default...
  }

  /**
   * This method gets called whenever the {@link #getView() view} is hidden from the screen. It can be overridden to
   * trigger custom logics or to clean up resources.
   */
  protected void onHide() {

    // nothing by default...
  }

}
