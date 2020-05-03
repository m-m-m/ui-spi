/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import io.github.mmm.ui.api.controller.AbstractUiController;
import io.github.mmm.ui.api.controller.UiPlace;
import io.github.mmm.ui.api.widget.UiWidget;

/**
 * Abstract default implementation of {@link io.github.mmm.ui.api.controller.UiNavigationManager}.
 *
 * @since 1.0.0
 */
public class AbstractUiNavigationManagerImpl extends AbstractUiNavigationManager {

  private UiNavigationNode current;

  private int index;

  private int size;

  /**
   * The constructor.
   */
  public AbstractUiNavigationManagerImpl() {

    super();
  }

  @Override
  protected void doInit() {

    navigateTo(UiPlace.HOME);
  }

  @Override
  protected void addHistory(UiPlace place) {

    UiNavigationNode next = new UiNavigationNode(place);
    if (this.current != null) {
      clearTail(this.current.next);
      this.size = this.index;
      this.current.next = next;
      next.previous = this.current;
    }
    this.current = next;
    this.index++;
    this.size++;
  }

  @Override
  protected void replaceHistory(UiPlace place) {

    UiNavigationNode next = new UiNavigationNode(place);
    if (this.current != null) {
      clearTail(this.current.next);
      this.size = this.index;
      this.current.next = next;
      next.previous = this.current;
    }
    this.current = next;
  }

  private void clearTail(UiNavigationNode node) {

    while (node != null) {
      String id = node.getPlace().getId();
      AbstractUiController<UiWidget> controller = getController(id);
      if (controller != null) {
        controller.reset();
      }
      node = node.next;
    }
  }

  @Override
  public UiPlace navigateBack() {

    if (this.current == null) {
      return null;
    }
    UiNavigationNode previous = this.current.previous;
    if (previous == null) {
      return null;
    }
    this.current = previous;
    this.index--;

    UiPlace place = this.current.place;
    navigateTo(place, UiNavigationMode.NONE, true);
    return place;
  }

  @Override
  public UiPlace navigateForward() {

    if (this.current == null) {
      return null;
    }
    UiNavigationNode next = this.current.getNext();
    if (next == null) {
      return null;
    }
    this.current = next;
    this.index++;
    UiPlace place = this.current.getPlace();
    navigateTo(place, UiNavigationMode.NONE, true);
    return place;
  }

  /**
   * @return the total number of places in the history.
   */
  public int getSize() {

    return this.size;
  }

  /**
   * @return the index of the current place inside the history.
   */
  public int getIndex() {

    return this.index;
  }

}
