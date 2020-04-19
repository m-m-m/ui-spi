/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi;

import java.util.Locale;
import java.util.ResourceBundle;

import io.github.mmm.ui.api.UiLocalizer;

/**
 * Abstract base implementation of {@link UiLocalizer}.
 */
public class AbstractUiLocalizer implements UiLocalizer {

  private Locale locale;

  private ResourceBundle bundle;

  /**
   * The constructor.
   */
  public AbstractUiLocalizer() {

    super();
    this.locale = Locale.getDefault();
  }

  @Override
  public Locale getLocale() {

    return this.locale;
  }

  /**
   * @param locale new value of {@link #getLocale()}.
   */
  public void setLocale(Locale locale) {

    this.locale = locale;
    this.bundle = null;
  }

  /**
   * @return the {@link ResourceBundle#getBaseBundleName() bundle name} of the {@link #getBundle() resource bundle} used
   *         for localization.
   */
  protected String getBundleName() {

    return "io.github.mmm.ui.nls.UiMessages";
  }

  /**
   * @return bundle
   */
  public ResourceBundle getBundle() {

    if (this.bundle == null) {
      this.bundle = ResourceBundle.getBundle(getBundleName(), this.locale);
    }
    return this.bundle;
  }

  private String doLocalize(String key) {

    if (getBundle().containsKey(key)) {
      return this.bundle.getString(key);
    }
    return null;
  }

  @Override
  public String localize(String key, Object context, boolean contextOnly) {

    String message = null;
    if (context != null) {
      message = localizeWithContext(key, context);
    }
    if (!contextOnly) {
      if (message == null) {
        message = doLocalize(key);
      }
      if (message == null) {
        message = key;
      }
    }
    return message;
  }

  /**
   * @param key the {@link #localize(String) message key}.
   * @param context the {@link #localize(String, Object) context}. Will not be {@code null}.
   * @return the localized message or {@code null} if no context specific localization was found.
   */
  protected String localizeWithContext(String key, Object context) {

    String suffix;
    if (context instanceof CharSequence) {
      suffix = context.toString();
    } else {
      suffix = "_" + context.getClass().getSimpleName();
    }
    String contextKey = key + suffix;
    return doLocalize(contextKey);
  }

}
