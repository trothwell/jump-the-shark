package com.jts.fooguice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class AppServletConfig extends GuiceServletContextListener {

   @Override
   protected Injector getInjector() {
      Injector injector = Guice.createInjector(new AppServletModule());
      return injector;
   }
}