package com.jts.fooguice;

import com.google.inject.Singleton;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet must be annotated with "@Singleton".
 */
@Singleton
public class AppServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   /**
    * Single constructor must exist, either a no arg constructor or one annotated with "@Inject".
    */
   public AppServlet() {
   }

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Writer out = resp.getWriter();
      out.append("<HTML><BODY>");
      out.append("<H1>Hello foo!</H1>");
      out.append("</BODY></HTML>");
   }
}