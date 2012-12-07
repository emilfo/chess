package chessGUI;

public class White
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
      
      return new int[] {0,1,0,2};
   }

}
