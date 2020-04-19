/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides the service provider interface (SPI) for UI framework to share base classes between different UI
 * implementations.
 */
module io.github.mmm.ui.spi.core {

  requires transitive io.github.mmm.ui.api.core;

  exports io.github.mmm.ui.spi;

  exports io.github.mmm.ui.spi.range;

  exports io.github.mmm.ui.spi.widget;

}
