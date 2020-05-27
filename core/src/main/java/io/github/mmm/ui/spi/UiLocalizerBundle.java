/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi;

import java.util.ResourceBundle;

import io.github.mmm.base.text.CaseHelper;

/**
 * Wrapper for {@link ResourceBundle} with inheritance hierarchy.
 */
public class UiLocalizerBundle {

  static final UiLocalizerBundle EMPTY = new UiLocalizerBundle(null, null);

  private final UiLocalizerBundle parent;

  private final ResourceBundle bundle;

  /**
   * The constructor.
   *
   * @param bundle the optional {@link ResourceBundle}.
   * @param parent the optional parent bundle.
   */
  public UiLocalizerBundle(ResourceBundle bundle, UiLocalizerBundle parent) {

    super();
    this.bundle = bundle;
    this.parent = parent;
  }

  /**
   * @param key the key to localize
   * @return the localized message text for the given {@code key} or {@code null} if no localization is available.
   */
  public String localize(String key) {

    return doLocalize(CaseHelper.toLowerCase(key));
  }

  private String doLocalize(String key) {

    if ((this.bundle != null) && this.bundle.containsKey(key)) {
      return this.bundle.getString(key);
    }
    if (this.parent != null) {
      return this.parent.doLocalize(key);
    }
    return null;
  }

  boolean isEmpty() {

    return (this == EMPTY);
  }

}
