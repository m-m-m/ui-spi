/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

import io.github.mmm.event.AbstractEventSource;
import io.github.mmm.ui.api.controller.UiController;
import io.github.mmm.ui.api.controller.UiEmbedding;
import io.github.mmm.ui.api.controller.UiNavigationEvent;
import io.github.mmm.ui.api.controller.UiNavigationEventListener;
import io.github.mmm.ui.api.controller.UiNavigationManager;
import io.github.mmm.ui.api.controller.UiPlace;
import io.github.mmm.ui.api.widget.UiRegularWidget;

/**
 * Abstract base implementation of {@link UiNavigationManager}.
 *
 * @since 1.0.0
 */
public abstract class AbstractUiNavigationManager
    extends AbstractEventSource<UiNavigationEvent, UiNavigationEventListener> implements UiNavigationManager {

  private final Map<String, AbstractUiController<?>> id2controllerMap;

  private final Map<UiEmbedding, AbstractUiController<?>> slot2controllerMap;

  private UiPlace currentPlace;

  /**
   * The constructor.
   */
  public AbstractUiNavigationManager() {

    super();
    this.id2controllerMap = new HashMap<>();
    this.slot2controllerMap = new HashMap<>();
  }

  private void register(AbstractUiController<?> controller) {

    String id = controller.getId();
    AbstractUiController<?> existing = this.id2controllerMap.put(id, controller);
    if (existing != null) {
      // throw new DuplicateObjectException(controller, id, existing);
      throw new IllegalStateException(id);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <W extends UiRegularWidget> AbstractUiController<W> getController(String id) {

    return (AbstractUiController<W>) this.id2controllerMap.get(id);
  }

  @Override
  public <W extends UiRegularWidget> AbstractUiController<W> getRequiredController(String id) {

    AbstractUiController<W> controller = getController(id);
    if (controller == null) {
      // throw new ObjectNotFoundException("UiController", id);
      System.out.println(id);
      throw new IllegalArgumentException(id);
    }
    return controller;
  }

  /**
   * Initializes this navigation manager and navigates to the initial {@link UiPlace}.
   */
  @SuppressWarnings("rawtypes")
  public final void init() {

    if (this.currentPlace != null) {
      return; // already initialized
    }
    ServiceLoader<UiController> serviceLoader = ServiceLoader.load(UiController.class);
    for (UiController<?> controller : serviceLoader) {
      register((AbstractUiController<?>) controller);
    }
    doInit();
    this.currentPlace = UiPlace.NONE;
  }

  /**
   * @see #init()
   */
  protected abstract void doInit();

  @Override
  public UiPlace getCurrentPlace() {

    return this.currentPlace;
  }

  @Override
  public void navigateTo(UiPlace place, boolean replace) {

    UiNavigationMode mode;
    if (replace) {
      mode = UiNavigationMode.REPLACE;
    } else {
      mode = UiNavigationMode.ADD;
    }
    navigateTo(place, mode, true);
  }

  /**
   * @param place is the {@link UiPlace} identifying the {@link UiController} to open.
   * @param mode the {@link UiNavigationMode}.
   * @param programmatic the {@link UiNavigationEvent#isProgrammatic() programmatic flag}.
   * @see #navigateTo(UiPlace)
   */
  protected void navigateTo(UiPlace place, UiNavigationMode mode, boolean programmatic) {

    Objects.requireNonNull(place, "place");
    UiPlace oldPlace = this.currentPlace;
    navigateRecursive(place, null);
    this.currentPlace = place;
    if (mode == UiNavigationMode.ADD) {
      addHistory(place);
    } else if (mode == UiNavigationMode.REPLACE) {
      replaceHistory(place);
    }
    fireEvent(new UiNavigationEvent(oldPlace, place, programmatic));
  }

  /**
   * @param place is the {@link UiPlace} to add to the history.
   * @see #navigateTo(UiPlace)
   */
  protected abstract void addHistory(UiPlace place);

  /**
   * @param place is the {@link UiPlace} to replace the current place in the history.
   * @see #navigateTo(UiPlace)
   */
  protected abstract void replaceHistory(UiPlace place);

  @Override
  public void updatePlace(UiPlace place) {

    if (Objects.equals(this.currentPlace.getId(), place.getId())) {
      // throw new ObjectMismatchException(place.getId(), currentPlace.getId());
      throw new IllegalArgumentException(place.getId());
    }
    this.currentPlace = place;
    replaceHistory(place);
  }

  /**
   * @param place the {@link UiPlace}.
   * @param embedding the optional {@link UiEmbedding}.
   * @return the {@link AbstractUiController} that has been {@link AbstractUiController#show(UiPlace, UiEmbedding)
   *         shown}.
   */
  protected AbstractUiController<?> navigateRecursive(UiPlace place, UiEmbedding embedding) {

    String id;
    if (embedding == null) {
      id = place.getId();
    } else {
      id = embedding.getControllerId();
    }
    AbstractUiController<?> controller = getRequiredController(id);
    if ((embedding == null) && !controller.isNavigable()) {
      throw new IllegalStateException(id);
    }
    UiEmbedding parentSlot = controller.show(place, embedding);
    if (parentSlot != null) {
      AbstractUiController<?> parentController = navigateRecursive(place, parentSlot);
      parentController.getView(); // ensure view is already created to prevent NPEs of naive controller developers
      parentController.embed(parentSlot, controller);
    } else {
      assert (UiController.ID_ROOT.equals(id));
    }
    return controller;
  }

  /**
   * Called from {@link AbstractUiController#embed(UiEmbedding, UiController)} to register {@link UiController}s in
   * their current {@link UiEmbedding}s and hide previous {@link UiController}s when replaced in that
   * {@link UiEmbedding#getSlotId() slot}.
   *
   * @param embedding the {@link UiEmbedding}.
   * @param controller the {@link AbstractUiController}.
   */
  protected void onEmbedded(UiEmbedding embedding, AbstractUiController<?> controller) {

    AbstractUiController<?> previousSlotController = this.slot2controllerMap.put(embedding, controller);
    if (previousSlotController != null) {
      previousSlotController.hide();
      // TODO: consider tracking timestamps and reset old controllers to free memory
    }

  }

}
