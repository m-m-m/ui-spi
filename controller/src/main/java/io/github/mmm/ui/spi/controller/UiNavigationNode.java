/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

import io.github.mmm.ui.api.controller.UiPlace;

/**
 * Node in the navigation history containing the {@link #getPlace() place}.
 *
 * @since 1.0.0
 */
public class UiNavigationNode {

  final UiPlace place;

  UiNavigationNode previous;

  UiNavigationNode next;

  /**
   * The constructor.
   *
   * @param value the initial {@link #getPlace() value}.
   */
  public UiNavigationNode(UiPlace value) {

    super();
    this.place = value;
  }

  /**
   * @return the value (element) stored in this {@link UiNavigationNode}.
   */
  public UiPlace getPlace() {

    return this.place;
  }

  /**
   * @return the previous node. Will be {@code null} if this is the first node.
   */
  public UiNavigationNode getPrevious() {

    return this.previous;
  }

  /**
   * @param previous new value of {@link #getPrevious()}.
   */
  public void setPrevious(UiNavigationNode previous) {

    this.previous = previous;
  }

  /**
   * @return the next node. Will be {@code null} if this is the last node.
   */
  public UiNavigationNode getNext() {

    return this.next;
  }

  /**
   * @param next new value of {@link #getNext()}.
   */
  public void setNext(UiNavigationNode next) {

    this.next = next;
  }

}
