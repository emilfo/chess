package chess;

public class Chess
{
   private GUI gui;
   Black b;
   White w;
   int move[];
   int blackKing[];
   int whiteKing[];
   
   public static void main(String args[]) {
      new Chess();
   }
   
   public enum Color
   {
      white, black
   };

   public enum Type
   {
      pawn, knight, queen, bishop, rook, king
   }

   private Piece board[][];
   private Color turn;

   Chess()
   {
      move = new int[4];
      board = new Piece[8][8];
      gui = new GUI();
      new Thread(gui).start();
      gui.waitForBoard();
      startGame();
   }

   private void init()
   {
      // Setting up white pieces
      board[0][0] = new Piece(Color.white, Type.rook);
      board[0][1] = new Piece(Color.white, Type.knight);
      board[0][2] = new Piece(Color.white, Type.bishop);
      board[0][3] = new Piece(Color.white, Type.king);
      whiteKing = new int[]{0, 3};
      board[0][4] = new Piece(Color.white, Type.queen);
      board[0][5] = new Piece(Color.white, Type.bishop);
      board[0][6] = new Piece(Color.white, Type.knight);
      board[0][7] = new Piece(Color.white, Type.rook);

      board[1][0] = new Piece(Color.white, Type.pawn);
      board[1][1] = new Piece(Color.white, Type.pawn);
      board[1][2] = new Piece(Color.white, Type.pawn);
      board[1][3] = new Piece(Color.white, Type.pawn);
      board[1][4] = new Piece(Color.white, Type.pawn);
      board[1][5] = new Piece(Color.white, Type.pawn);
      board[1][6] = new Piece(Color.white, Type.pawn);
      board[1][7] = new Piece(Color.white, Type.pawn);

      // Setting up empty board
      for (int i = 2; i < 6; i++)
      {
         for (int j = 0; j < 8; j++)
         {
            board[i][j] = null;
         }
      }

      // Setting up black pieces
      board[7][0] = new Piece(Color.black, Type.rook);
      board[7][1] = new Piece(Color.black, Type.knight);
      board[7][2] = new Piece(Color.black, Type.bishop);
      board[7][3] = new Piece(Color.black, Type.king);
      blackKing = new int[]{7, 3};
      board[7][4] = new Piece(Color.black, Type.queen);
      board[7][5] = new Piece(Color.black, Type.bishop);
      board[7][6] = new Piece(Color.black, Type.knight);
      board[7][7] = new Piece(Color.black, Type.rook);

      board[6][0] = new Piece(Color.black, Type.pawn);
      board[6][1] = new Piece(Color.black, Type.pawn);
      board[6][2] = new Piece(Color.black, Type.pawn);
      board[6][3] = new Piece(Color.black, Type.pawn);
      board[6][4] = new Piece(Color.black, Type.pawn);
      board[6][5] = new Piece(Color.black, Type.pawn);
      board[6][6] = new Piece(Color.black, Type.pawn);
      board[6][7] = new Piece(Color.black, Type.pawn);
   }

   public Piece[][] getBoard()
   {
      movePiece(0, 0, 0, 0, Color.black);
      return board;
   }
   
   private void startGame()
   {
      init();
      gui.DrawBoard(board);
      
      b = new Black();
      w = new White();
      loopMoves();
      
   }
   
   private void loopMoves() 
   {
      while (true) {
         
         turn = Color.white;
         move = w.getMove();
         if(!movePiece(move[0], move[1], move[2], move[3], turn)) {
            //blackWins
            System.out.println("White illegal move, black wins!");
            return;
         }
         gui.drawMove(move[0], move[1], move[2], move[3], board[ move[0] ][ move[1] ] );
         movePiece(move[0], move[1], move[2], move[3]);
         //check for chess here
         if (inChess(whiteKing[0], whiteKing[1], Color.white)) {
            System.out.println("White in chess after move, black wins!");
            return;
         }
         
         
         
         turn = Color.black;
         move = b.getMove();
         if(!movePiece(move[0], move[1], move[2], move[3], turn)) {
            //white wins
            System.out.println("Black illegal move, white wins!");
            return;
         }
         gui.drawMove(move[0], move[1], move[2], move[3], board[ move[0] ][ move[1] ]);
         movePiece(move[0], move[1], move[2], move[3]);
         
         if (inChess(blackKing[0], blackKing[1], Color.black)) {
            System.out.println("Black in chess after move, white wins!");
            return;
         }
      } 
   }

   private boolean movePiece(int oldX, int oldY, int newX, int newY, Color c)
   {
      if (c != turn)
      {
         return false;
      }

      if (! (oldX > -1 && oldX < 8) || ! (oldY > -1 && oldY < 8))
      {
         return false;
      }

      Piece p = board[oldX][oldY];

      if (p == null)
      {
         return false;
      }

      if (board[oldX][oldY].getColor() != c)
      {
         return false;
      }

      if (board[newX][newY] != null)
      {
         if (board[newX][newY].getColor() == c)
         {
            return false;
         }
      }

      if (!legalMove(p, oldX, oldY, newX, newY))
      {
         return false;
      }

      if (p.getType() == Type.king) {
         if(p.getColor() == Color.white) {
            whiteKing = new int[] {newX, newY};
         }
         else
            blackKing = new int[] {newX, newY};
      }
      return true;
   }

   private boolean legalMove(Piece p, int oldX, int oldY, int newX, int newY)
   {
      if (p.getType() == Type.pawn)
      {
         return pawnLegalMove(p, oldX, oldY, newX, newY);
      }
      if (p.getType() == Type.rook)
      {
         return rookLegalMove(p, oldX, oldY, newX, newY);
      }
      if (p.getType() == Type.bishop)
      {
         return bishopLegalMove(p, oldX, oldY, newX, newY);
      }
      if (p.getType() == Type.queen)
      {
         return queenLegalMove(p, oldX, oldY, newX, newY);
      }
      if (p.getType() == Type.king)
      {
         return kingLegalMove(p, oldX, oldY, newX, newY);
      }
      if (p.getType() == Type.knight)
      {
         return knightLegalMove(p, oldX, oldY, newX, newY);
      }

      return false;
   }

   private boolean knightLegalMove(Piece p, int oldX, int oldY, int newX,
         int newY)
   {
      if (oldX == newX + 1 && (oldY == newY + 2 || oldY == newY - 2))
      {
         return true;
      }

      if (oldY == newY + 1 && (oldX == newX + 2 || oldX == newX - 2))
      {
         return true;
      }

      return false;
   }

   private boolean kingLegalMove(Piece p, int oldX, int oldY, int newX, int newY)
   {
      if (oldX == newX && oldY == newY + 1 || oldY == newY - 1)
      {
         return true;
      }

      if (oldY == newY && oldX == newX + 1 || oldX == newX - 1)
      {
         return true;
      }

      if (oldX == newX + 1 && oldY == newY + 1 || oldY == newY - 1)
      {
         return true;
      }

      if (oldX == newX - 1 && oldY == newY + 1 || oldY == newY - 1)
      {
         return true;
      }

      return false;
   }

   private boolean queenLegalMove(Piece p, int oldX, int oldY, int newX,
         int newY)
   {
      return (bishopLegalMove(p, oldX, oldY, newX, newY) || rookLegalMove(p,
            oldX, oldY, newX, newY));

   }

   private boolean bishopLegalMove(Piece p, int oldX, int oldY, int newX,
         int newY)
   {
      if (newX == oldX || newY == oldY)
         return false;

      if (Math.abs(oldX - newX) != Math.abs(oldY - newY))
         return false;

      int loopX, loopY;

      if (newX > oldX)
         loopX = 1;
      else
         loopX = -1;
      if (newY > oldY)
         loopY = 1;
      else
         loopY = -1;

      for (int x = oldX, y = oldY; Math.abs(x) < Math.abs(oldX - newX); x += loopX, y += loopY)
      {
         if (board[x][y] != null)
            return false;
      }
      return true;
   }

   private boolean rookLegalMove(Piece p, int oldX, int oldY, int newX, int newY)
   {
      if(!((oldX==newX && oldY != newY) || (oldX != newX && newY == oldY))) {
         return false;
      }
      if ( (oldX == newX && oldY < newY))
      {
         for (int y = oldY; y < newY; y++)
         {
            if (board[oldX][y] != null)
            {
               return false;
            }
         }
      }

      if ( (oldX == newX && oldY > newY))
      {
         for (int y = oldY; y < newY; y--)
         {
            if (board[oldX][y] != null)
            {
               return false;
            }
         }
      }

      if ( (oldY == newY && oldX < newX))
      {
         for (int x = oldY; x < newY; x++)
         {
            if (board[x][oldY] != null)
            {
               return false;
            }
         }
      }

      if ( (oldY == newY && oldX > newX))
      {
         for (int x = oldY; x < newY; x--)
         {
            if (board[x][oldY] != null)
            {
               return false;
            }
         }
      }

      return true;
   }

   private boolean pawnLegalMove(Piece p, int oldX, int oldY, int newX, int newY)
   {
      Piece toMove = board[newX][newY];

      if (toMove == null)
      {
         if (newX == oldX + p.forward && oldY == newY)
         {
            return true;
         }
         else if (newX == oldX + (2 * p.forward) && oldY == newY)
         {
            if (!board[oldX][oldY].moved())
            {
               return (board[oldX + p.forward][oldY] == null);
            }
         }
      }
      else
      {
         if (newX == oldX + p.forward && (newY == newY - 1 || newY == newY + 1))
         {
            return true;
         }
      }
      return false;
   }

   public boolean[] checkCheck() {
      boolean check[] = {false, false};
      int kings[][] = findKings();
      
      for(int i = 0; i < 8; i++) {
         for(int j = 0; j < 8; j++) {
            if(board[i][j] != null) {
               if(board[i][j].getColor() == Color.white) {
                  if (legalMove(board[i][j], i, j, kings[0][0], kings[0][1])){
                     check[0] = true;
                  }
                  
               }
               else if (legalMove(board[i][j], i, j, kings[1][0], kings[1][1])){
                  check[1] = true;
               }
                  
            }
         }
      }
      
      return check;
   }

   private int[][] findKings()
   {
      int kings[][] = new int[2][2];
      
      for(int i = 0; i < 8; i++) {
         for(int j = 0; j < 8; j++) {
            if(board[i][j].getType() == Type.king) {
               
               if(board[i][j].getColor() == Color.white) {
                  kings[0][0] = i;
                  kings[0][1] = j;
               }
               if(board[i][j].getColor() == Color.black) {
                  kings[1][0] = i;
                  kings[1][1] = j;
               }
               
            }
         }
      }
      return kings;
   }
   
   private void movePiece(int i, int j, int k, int l) {
      board[k][l] = board[i][j];
      board[i][j] = null;
   }

   public class Piece
   {
      private Chess.Color color;
      private Chess.Type piece;
      private boolean moved;
      protected int forward;

      Piece(Chess.Color color, Chess.Type piece)
      {
         this.color = color;
         this.piece = piece;
         if (color == Color.white)
            forward = 1;
         else
            forward = -1;
      }

      public Chess.Color getColor()
      {
         return color;
      }

      public Chess.Type getType()
      {
         return piece;
      }

      public boolean moved()
      {
         return moved;
      }

      protected void hasMoved()
      {
         moved = false;
      }
   }
   
   private boolean inChess(int k, int l, Color c)
   {
      for(int i = 0; i < 8; i++) {
         for(int j = 0; j < 8; j++) {
            if(board[i][j] != null) {
               if(board[i][j].getColor() != c) 
               {
                  if(legalMove(board[i][j], i, j, k, l)) {
                     System.err.println(i+","+j+" : "+k+","+l);
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }
}
