/*
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/**
 * Provides widget factories for common datatypes.
 */
@SuppressWarnings("rawtypes") //
module io.github.mmm.ui.spi.datatype {

  requires transitive io.github.mmm.ui.api.core;

  provides io.github.mmm.ui.api.factory.UiSingleWidgetFactoryDatatype with //
      io.github.mmm.ui.spi.factory.datatype.UiFactoryDatatypeInteger, //
      io.github.mmm.ui.spi.factory.datatype.UiFactoryDatatypeBoolean, //
      io.github.mmm.ui.spi.factory.datatype.UiFactoryDatatypeString;

}
