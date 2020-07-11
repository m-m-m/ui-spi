/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.mmm.ui.spi;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import io.github.mmm.base.i18n.Localizable;
import io.github.mmm.ui.api.UiLocalizer;
import io.github.mmm.validation.Validatable;

/**
 * Abstract base implementation of {@link UiLocalizer}.
 */
public class AbstractUiLocalizer implements UiLocalizer {

  private Locale locale;

  private final Map<String, UiLocalizerBundle> bundleMap;

  /**
   * The constructor.
   */
  public AbstractUiLocalizer() {

    super();
    this.locale = Locale.getDefault();
    this.bundleMap = new HashMap<>();
  }

  @Override
  public Locale getLocale() {

    return this.locale;
  }

  /**
   * @param locale new value of {@link #getLocale()}.
   */
  public void setLocale(Locale locale) {

    if (locale == null) {
      locale = Locale.getDefault();
    }
    if (this.locale.equals(locale)) {
      return;
    }
    this.locale = locale;
    this.bundleMap.clear();
  }

  /**
   * @return the {@link ResourceBundle#getBaseBundleName() bundle name} of the default {@link ResourceBundle} for
   *         localization.
   */
  protected String getBundleName() {

    return "l10n.io.github.mmm.ui.UiMessages";
  }

  /**
   * @param context the context {@link Object} - see {@link #localizeOrNull(String, Object, boolean)}.
   * @return the derived {@link ResourceBundle#getBaseBundleName() bundle name}.
   */
  protected String getBundleName(Object context) {

    if (context == null) {
      return getBundleName();
    } else if (context instanceof CharSequence) {
      return context.toString();
    }
    Class<?> type;
    if (context instanceof Class) {
      type = (Class<?>) context;
    } else {
      type = context.getClass();
    }
    return Localizable.createBundleName(type);
  }

  /**
   * @return the {@link UiLocalizerBundle} for the given {@link ResourceBundle#getBundle(String, Locale) bundle name}.
   */
  protected UiLocalizerBundle getBundle() {

    return getBundle(null);
  }

  /**
   * @param context the context {@link Object} - see {@link #localizeOrNull(String, Object, boolean)}.
   * @return the {@link UiLocalizerBundle} for the given {@code bundleName}.
   */
  protected UiLocalizerBundle getBundle(Object context) {

    String bundleName = getBundleName(context);
    UiLocalizerBundle bundle = this.bundleMap.get(bundleName);
    if (bundle == null) {
      Class<?> contextType;
      if (context instanceof Class) {
        contextType = (Class<?>) context;
      } else if (context != null) {
        contextType = context.getClass();
      } else {
        contextType = null;
      }
      bundle = createBundle(bundleName, contextType);
      this.bundleMap.put(bundleName, bundle);
    }
    return bundle;
  }

  /**
   * @param bundleName the {@link ResourceBundle#getBundle(String, Locale) bundle name}.
   * @param contextType the optional {@link Class} reflecting the context.
   * @return the {@link UiLocalizerBundle}.
   */
  protected UiLocalizerBundle createBundle(String bundleName, Class<?> contextType) {

    UiLocalizerBundle parentBundle = null;
    if (contextType != null) {
      Class<?> superclass = contextType.getSuperclass();
      if ((superclass != null) && isInheritContext(superclass)) {
        parentBundle = getBundle(superclass);
      }
    }
    ResourceBundle resourceBundle;
    try {
      resourceBundle = ResourceBundle.getBundle(bundleName, this.locale);
    } catch (Exception e) {
      System.out.println("Could not find bundle for base name " + bundleName);
      if (parentBundle == null) {
        return UiLocalizerBundle.EMPTY;
      } else {
        return parentBundle;
      }
    }
    return new UiLocalizerBundle(resourceBundle, parentBundle);
  }

  private boolean isInheritContext(Class<?> superclass) {

    // ReadableBean is not in our dependencies here...
    return Validatable.class.isAssignableFrom(superclass);
  }

  @Override
  public String localizeOrNull(String key, Object context, boolean contextOnly) {

    String message = null;
    if (context != null) {
      message = getBundle(context).localize(key);
    }
    if (!contextOnly && (message == null)) {
      message = getBundle().localize(key);
    }
    return message;
  }

}
