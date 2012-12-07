package chessGUI;

public class Black
{
   public int[] getMove() {
      try
      {
         Thread.sleep(1000);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      return new int[] {0,6,0,5};
   }

}
