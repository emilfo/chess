package chess;

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
      return new int[] {6,0,5,0};
   }

}
