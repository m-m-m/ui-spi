/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

import io.github.mmm.base.exception.DuplicateObjectException;
import io.github.mmm.base.exception.ObjectNotFoundException;
import io.github.mmm.event.AbstractEventSource;
import io.github.mmm.ui.api.controller.AbstractUiController;
import io.github.mmm.ui.api.controller.UiController;
import io.github.mmm.ui.api.controller.UiControllerSlot;
import io.github.mmm.ui.api.controller.UiNavigationEvent;
import io.github.mmm.ui.api.controller.UiNavigationEventListener;
import io.github.mmm.ui.api.controller.UiNavigationManager;
import io.github.mmm.ui.api.controller.UiPlace;
import io.github.mmm.ui.api.widget.UiWidget;
import io.github.mmm.ui.api.widget.composite.UiMutableSingleComposite;

/**
 * Abstract base implementation of {@link UiNavigationManager}.
 *
 * @since 1.0.0
 */
public abstract class AbstractUiNavigationManager
    extends AbstractEventSource<UiNavigationEvent, UiNavigationEventListener> implements UiNavigationManager {

  private final Map<String, AbstractUiController<?>> id2controllerMap;

  private final Map<String, AbstractUiController<?>> type2controllerMap;

  private UiPlace currentPlace;

  /**
   * The constructor.
   */
  @SuppressWarnings("rawtypes")
  public AbstractUiNavigationManager() {

    super();
    this.id2controllerMap = new HashMap<>();
    this.type2controllerMap = new HashMap<>();
    this.currentPlace = UiPlace.NONE;
    ServiceLoader<AbstractUiController> serviceLoader = ServiceLoader.load(AbstractUiController.class);
    for (AbstractUiController<?> controller : serviceLoader) {
      register(controller);
    }
  }

  private void register(AbstractUiController<?> controller) {

    String id = controller.getId();
    AbstractUiController<?> existing = this.id2controllerMap.put(id, controller);
    if (existing != null) {
      throw new DuplicateObjectException(controller, id, existing);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <W extends UiWidget> AbstractUiController<W> getController(String id) {

    return (AbstractUiController<W>) this.id2controllerMap.get(id);
  }

  @Override
  public <W extends UiWidget> AbstractUiController<W> getRequiredController(String id) {

    AbstractUiController<W> controller = getController(id);
    if (controller == null) {
      throw new ObjectNotFoundException("UiController", id);
    }
    return controller;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <W extends UiWidget> UiController<W> getCurrentDialog(String type) {

    return (UiController<W>) this.type2controllerMap.get(type);
  }

  /**
   * Initializes this navigation manager and navigates to the initial {@link UiPlace}.
   */
  public abstract void init();

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
   * @param slot the optional {@link UiControllerSlot}.
   * @return the {@link AbstractUiController} that has been {@link AbstractUiController#show(UiPlace, UiControllerSlot)
   *         shown}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected AbstractUiController<UiWidget> navigateRecursive(UiPlace place, UiControllerSlot slot) {

    String id;
    if (slot == null) {
      id = place.getId();
    } else {
      id = slot.getId();
    }
    AbstractUiController<UiWidget> controller = getRequiredController(id);
    UiControllerSlot parentSlot = controller.show(place, slot);
    if (parentSlot != null) {
      AbstractUiController<UiWidget> parentController = navigateRecursive(place, parentSlot);
      UiMutableSingleComposite uiSlot = parentController.getSlot(parentSlot.getSlot());
      uiSlot.setChild(controller.getView());
    }
    return controller;
  }

}
