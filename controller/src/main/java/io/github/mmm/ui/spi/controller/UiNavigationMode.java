/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi.controller;

/**
 * Enum with the possible modes for a
 * {@link io.github.mmm.ui.api.controller.UiNavigationManager#navigateTo(io.github.mmm.ui.api.controller.UiPlace)
 * navigation}.
 *
 * @since 1.0.0
 */
public enum UiNavigationMode {

  /** Add to the navigation history. */
  ADD,

  /** Replace the current entry in the navigation history. */
  REPLACE,

  /** Do not touch navigation history. */
  NONE

}
