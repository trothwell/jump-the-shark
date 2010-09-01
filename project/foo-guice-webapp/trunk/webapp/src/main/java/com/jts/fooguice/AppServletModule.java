package com.jts.fooguice;

import com.google.inject.servlet.ServletModule;

public class AppServletModule extends ServletModule {

   @Override
   protected void configureServlets() {
      serve("/").with(AppServlet.class);
   }

}