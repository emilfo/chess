package chess;

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
      
      return new int[] {1,0,2,0};
   }

}
