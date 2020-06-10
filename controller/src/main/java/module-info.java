/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides SPI for controller management and navigation.
 * 
 * @provides io.github.mmm.ui.api.controller.UiController
 * @uses io.github.mmm.ui.api.controller.UiController
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.ui.spi.controller {

  requires transitive io.github.mmm.ui.api.controller;

  uses io.github.mmm.ui.api.controller.UiController;

  provides io.github.mmm.ui.api.controller.UiController with //
      io.github.mmm.ui.spi.controller.UiControllerRoot;

  exports io.github.mmm.ui.spi.controller;

}
