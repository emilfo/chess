package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chess.Chess.Piece;


public class GUI implements Runnable
{
   Chess.Piece[][] pieces;
   JLabel[][] squares;
   ImageIcon icon;
   String path;
   JFrame window;
   Chess ch;

   public void run()
   {
      path = System.getProperty("user.dir");

      System.out.println(path + "\\src\\chess\\pieces\\wpawn.png");

      window = new JFrame("Chess");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setSize(700, 550);

      JPanel mainP = new JPanel();
      mainP.setLayout(new BorderLayout());
      window.add(mainP);

      JPanel boardBorder = new JPanel();

      boardBorder.setLayout(new BoxLayout(boardBorder, BoxLayout.LINE_AXIS));
      mainP.add(boardBorder, BorderLayout.CENTER);

      JPanel board = new JPanel();
      board.setMaximumSize(new Dimension(498, 498));
      board.setMinimumSize(new Dimension(498, 498));
      board.setBorder(BorderFactory.createLineBorder(Color.black));
      GridLayout boardG = new GridLayout(8, 8);
      board.setLayout(boardG);

      boardBorder.add(board, BorderLayout.CENTER);

      JPanel sidePanel = new JPanel();
      GridLayout sideG = new GridLayout(0, 5);
      sidePanel.setLayout(sideG);
      mainP.add(sidePanel, BorderLayout.EAST);

      squares = new JLabel[8][8];

      for (int i = 0; i < 8; i++)
      {
         for (int j = 0; j < 8; j++)
         {
            squares[i][j] = new JLabel();
            squares[i][j].setOpaque(true);
            board.add(squares[i][j]);

            if (i % 2 == 0)
            {
               if (j % 2 != 0)
               {
                  squares[i][j].setBackground(Color.gray);
               }
               else
                  squares[i][j].setBackground(Color.white);
            }
            else
            {
               if (j % 2 == 0)
               {
                  squares[i][j].setBackground(Color.gray);
               }
               else
                  squares[i][j].setBackground(Color.white);
            }

         }
      }
      icon = new ImageIcon(path + "\\src\\chess\\pieces\\bpawn.png");
      window.setVisible(true);
      
   }
   

   void DrawBoard(Chess.Piece[][] board)
   {

      for (int i = 0; i < 8; i++)
      {
         for (int j = 0; j < 8; j++)
         {
            if (board[i][j] != null)
            {
               drawIcon(i, j, board[i][j]);
            }
         }
      }
   }

   void drawIcon(int i, int j, Piece p)
   {

      if (icon != null)
         icon.getImage().flush();

      if (p.getType() == Chess.Type.pawn)
      {
         if (p.getColor() == Chess.Color.black)
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\bpawn.png");
         else
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\wpawn.png");

      }
      else if (p.getType() == Chess.Type.bishop)
      {
         if (p.getColor() == Chess.Color.black)
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\bbishop.png");
         else
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\wbishop.png");

      }
      else if (p.getType() == Chess.Type.knight)
      {
         if (p.getColor() == Chess.Color.black)
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\bknight.png");
         else
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\wknight.png");

      }
      else if (p.getType() == Chess.Type.rook)
      {
         if (p.getColor() == Chess.Color.black)
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\brook.png");
         else
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\wrook.png");

      }
      else if (p.getType() == Chess.Type.queen)
      {
         if (p.getColor() == Chess.Color.black)
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\bqueen.png");
         else
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\wqueen.png");

      }
      else if (p.getType() == Chess.Type.king)
      {
         if (p.getColor() == Chess.Color.black)
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\bking.png");
         else
            icon = new ImageIcon(path + "\\src\\chess\\pieces\\wking.png");

      }
      squares[i][j].setIcon(icon);
      window.revalidate();
      window.repaint();
   }

   void removeIcon(int i, int j)
   {
      squares[i][j].setIcon(null);
      window.revalidate();
      window.repaint();
   }

   public void waitForBoard()
   {
      while (squares == null)
      {
         try
         {
            Thread.sleep(100);
         }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }
      }

      while (squares[7][7] == null)
      {
         try
         {
            Thread.sleep(100);
         }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }
      }

   }


   public void drawMove(int i, int j, int k, int l, Chess.Piece p)
   {
      removeIcon(i,j);
      drawIcon(k,l,p);
      
      
   }
}
