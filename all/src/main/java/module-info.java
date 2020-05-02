/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Aggregates all modules of the service provider interface (SPI) for UI framework.
 */
module io.github.mmm.ui.spi.all {

  requires transitive io.github.mmm.ui.spi.controller;

  requires transitive io.github.mmm.ui.spi.datatype;

  requires transitive io.github.mmm.ui.spi.nls;

}
