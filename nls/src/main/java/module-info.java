/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides the implementation of {@link io.github.mmm.ui.api.UiLocalizer}.
 */
module io.github.mmm.ui.spi.nls {

  requires transitive io.github.mmm.ui.spi.core;

  provides io.github.mmm.ui.api.UiLocalizer with //
      io.github.mmm.ui.spi.nls.UiLocalizerImpl;

}
