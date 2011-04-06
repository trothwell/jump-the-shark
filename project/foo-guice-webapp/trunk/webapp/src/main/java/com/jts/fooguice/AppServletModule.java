package com.jts.fooguice;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class AppServletModule extends ServletModule {

   @Override
   protected void configureServlets() {
      serve("/app").with(AppServlet.class);

      bind(LegacyServlet.class).in(Singleton.class);
      serve("/legacy").with(LegacyServlet.class);
   }
}