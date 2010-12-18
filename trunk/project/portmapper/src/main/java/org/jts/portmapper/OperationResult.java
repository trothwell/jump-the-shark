package org.jts.portmapper;

/**
 * Stream result.
 */
public interface OperationResult {
   Exception getError();

   boolean hasError();

   void withError(Exception e);
}
