/**
 * @incomplete trothwell
 */
package org.jts.portmapper;

/**
 * Stream result.
 */
public class OperationResults {
   private static class OperationResultImpl implements OperationResult {
      private Exception error;
      private boolean hasError;

      @Override
      public Exception getError() {
         return error;
      }

      @Override
      public boolean hasError() {
         return hasError;
      }

      @Override
      public void withError(Exception error) {
         this.error = error;
         this.hasError = true;
      }

   }

   static OperationResult createNew() {
      return new OperationResultImpl();
   }
}
