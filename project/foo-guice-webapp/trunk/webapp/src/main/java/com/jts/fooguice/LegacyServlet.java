package com.jts.fooguice;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Legacy applet with no annotation (not guice aware).
 */
public class LegacyServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   /**
    * Public ctor.
    */
   public LegacyServlet() {
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Writer out = resp.getWriter();
      out.append("<HTML><BODY>");
      out.append("<H1>Hello legacy!</H1>");
      out.append("</BODY></HTML>");
   }
}