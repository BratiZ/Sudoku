package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SudokuBoard extends JFrame{

    GameLogic gameLogic;
    
    final String colourButtonField = "#f6f6f6",
                 colourBackGround = "#000000",
                 colourButtonUnlocked = "#c2c2c2";
    
    int windowSizeX,
        windowSizeY,              
        gridBorder,  
        gridSizeX,
        gridSizeY,
        gridSize,
        partBoardSizeX,
        partBoardSizeY,
        partBoardSize,
        boardSizeX,
        boardSizeY ,
        boardSize;
    
    JPanel boardPart[],
           boardBoard,
           boardTop;
    
    boardField fields[];
      
    BoardTopButton topButtonCheck,
                   topButtonReset,
                   topButtonShowBadAnser,
                   topButtonShowCorrectAnswer;
                   
    class BoardTopButton extends JButton implements ActionListener{

        public BoardTopButton( String buttonName) {
            this.setText("<html><center><font color=\"cc0099\">" + buttonName +"</font></center></html>");
            this.setPreferredSize( new Dimension( 120, 40));
            addActionListener( this);
            
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            Object source = ae.getSource();
            
            if( source == topButtonCheck)
                System.out.println("Check");
        }
    }
    
    class boardField extends JButton implements ActionListener{
        int value;
        
        public boardField() {
            addActionListener( this);            
            this.value = 0;
            this.setText( "");
            this.setBackground( Color.decode( colourButtonField));
        }
        
        public boardField( int fieldIndex){
            addActionListener( this); 
            if( gameLogic.isFieldUnlocked( fieldIndex )){
                setEnabled( false);
                this.value = gameLogic.getValueOfField( fieldIndex);
                setText("" + this.value);
                setBackground( Color.decode( colourButtonUnlocked) );

            }
            else{
                this.value = 0;
                this.setText( "");
                this.setBackground( Color.decode( colourButtonField));
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            this.value = ++this.value % 10;
            Object source = ae.getSource();
            
            if( this.value != 0)
                this.setText( this.value + "");
            else
                this.setText("");
        }
        
    }
    
    void ResetAllButtons(){
        
    }
    
    public SudokuBoard() throws HeadlessException {
        this(3);
    }
    
    public SudokuBoard( int SudokuSize) throws HeadlessException {
        super( "Sudoku");
        this.gameLogic = new GameLogic( SudokuSize);
        this.windowSizeX = 200 * SudokuSize;
        this.windowSizeY = 300 * SudokuSize;              
        this.gridBorder = 4;  
        this.gridSizeX = SudokuSize;
        this.gridSizeY = SudokuSize+1;
        this.gridSize = this.gridSizeX * this.gridSizeY;
        this.partBoardSizeX = SudokuSize;
        this.partBoardSizeY = SudokuSize;
        this.partBoardSize = this.partBoardSizeX * this.partBoardSizeY;
        this.boardSizeX = this.partBoardSizeX * this.gridSizeX;
        this.boardSizeY = this.partBoardSizeY * this.gridSizeY;
        this.boardSize = this.boardSizeX * this.boardSizeY;
        
        this.topButtonCheck = new BoardTopButton("Check");
        this.topButtonReset = new BoardTopButton("Reset");
        this.topButtonShowBadAnser = new BoardTopButton("Good Answer");
        this.topButtonShowCorrectAnswer = new BoardTopButton("Bad Answer");
        
        this.boardTop = new JPanel();
        this.boardBoard = new JPanel();
        this.fields = new boardField[ this.boardSize];
        this.boardPart = new JPanel[ gridSize];
        
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        this.setSize( windowSizeX, windowSizeY);
        this.setLayout( new BorderLayout());
        boardBoard.setLayout( new GridLayout( gridSizeY, gridSizeX, gridBorder ,gridBorder));
        boardBoard.setBackground( Color.decode( colourBackGround));
        this.setResizable( false);
        this.boardTop.setBackground( Color.decode( colourBackGround));
        this.boardTop.setLayout( new FlowLayout());
        
        
        this.boardTop.add( this.topButtonCheck);
        this.boardTop.add( this.topButtonReset);
        this.boardTop.add( this.topButtonShowBadAnser);
        this.boardTop.add( this.topButtonShowCorrectAnswer);

        
        this.add( BorderLayout.NORTH, this.boardTop);
        this.add( BorderLayout.CENTER, boardBoard);
        
        for( int f = 0; f < this.boardSize; ++f){
            this.fields[f] = new boardField(f);
        }
                    
        for( int f = 0; f < this.gridSize; ++f){
            this.boardPart[f] = new JPanel();
            boardBoard.add( this.boardPart[f]);
            this.boardPart[f].setLayout( new GridLayout( partBoardSizeY, partBoardSizeX));
                for( int h = f*partBoardSize; h < f*partBoardSize + this.partBoardSize; ++h)
                    this.boardPart[f].add( this.fields[h]);
        }
        
        setVisible( true);
    }
}