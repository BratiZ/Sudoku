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
    
    boolean buttonHelpPressed;
    
    JPanel boardPart[],
           boardBoard,
           boardTop;
    
    boardField fields[];
      
    BoardTopButton topButtonCheck,
                   topButtonReset,
                   topButtonShowBadAnswer,
                   topButtonShowCorrectAnswer,
                   topButtonGetHelp;
    
    class BoardTopButton extends JButton implements ActionListener{

        public BoardTopButton( String buttonName) {
            this.setText("<html><center><font color=\"cc0099\">" + buttonName +"</font></center></html>");
            this.setPreferredSize( new Dimension( 100, 40));
            addActionListener( this);
            
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            Object source = ae.getSource();
            
            if( source == topButtonCheck){
                CheckBoard();
            }
            else if( source == topButtonReset){
                ResetBoard();
            }
            else if( source == topButtonShowBadAnswer){
                ShowBadAnswer();
            }
            else if( source == topButtonShowCorrectAnswer){
                ShowCorrectAnswer();
            }
            else if( source == topButtonGetHelp){
                GetHelp();
            }
        }
    }
    
    void CheckBoard(){
        
    }
    
    void ResetBoard(){
        
    }
    
    void ShowBadAnswer(){
        
    }
    
    void ShowCorrectAnswer(){
        
    }
    
    void GetHelp(){
        if( this.buttonHelpPressed)
            this.buttonHelpPressed = false;
        else 
            this.buttonHelpPressed = true;
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
                setText( "" + this.value);
                setBackground( Color.decode( colourButtonUnlocked) );

            }
            else{
                this.value = 0;
                this.setText( "");
                this.setBackground( Color.decode( colourButtonField));
            }
        }
        
        void ShowValue( int fieldIndex){
            setEnabled( false);
            this.value = gameLogic.getValueOfField( fieldIndex);
            setText( "" + this.value);
            setBackground( Color.decode( colourButtonUnlocked) );
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            this.value = ++this.value % 10;
            Object source = ae.getSource();
            
            if( buttonHelpPressed){
                for( int f = 0; f < boardSize; ++f)
                    if( source == fields[f]){
                        fields[f].ShowValue( f);
                        buttonHelpPressed = false;
                        System.out.println(f);
                    }
            }
            else          
            
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
        this.windowSizeY = 250 * SudokuSize;              
        this.gridBorder = 4;  
        this.gridSizeX = SudokuSize;
        this.gridSizeY = SudokuSize;
        this.gridSize = this.gridSizeX * this.gridSizeY;
        this.partBoardSizeX = SudokuSize;
        this.partBoardSizeY = SudokuSize;
        this.partBoardSize = this.partBoardSizeX * this.partBoardSizeY;
        this.boardSizeX = this.partBoardSizeX * this.gridSizeX;
        this.boardSizeY = this.partBoardSizeY * this.gridSizeY;
        this.boardSize = this.boardSizeX * this.boardSizeY;
        
        this.buttonHelpPressed = false;
        
        this.topButtonCheck = new BoardTopButton("Check");
        this.topButtonReset = new BoardTopButton("Reset");
        this.topButtonShowBadAnswer = new BoardTopButton("Good Answer");
        this.topButtonShowCorrectAnswer = new BoardTopButton("Bad Answer");
        this.topButtonGetHelp = new BoardTopButton("Help");
        
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
        this.boardTop.add( this.topButtonShowBadAnswer);
        this.boardTop.add( this.topButtonShowCorrectAnswer);
        this.boardTop.add( this.topButtonGetHelp);

        
        this.add( BorderLayout.NORTH, this.boardTop);
        this.add( BorderLayout.CENTER, boardBoard);
        
        for( int f = 0; f < this.boardSize; ++f){
            this.fields[f] = new boardField(f);
        }
       
        for( int s = 0;  s < gridSizeX; ++s){
            for( int d = 0; d < gridSizeX; ++d){
                this.boardPart[ ( ( s*gridSizeX) + ( d))] = new JPanel();
                this.boardBoard.add( this.boardPart[ ( ( s*gridSizeX) + ( d))]);
                this.boardPart[ ( ( s*gridSizeX) + ( d))].setLayout( new GridLayout( partBoardSizeY, partBoardSizeX));
                for( int f = 0; f < gridSizeX; ++f){
                    for( int g = 0; g < gridSizeX; ++g){
                        this.boardPart[ (( s*gridSizeX) + ( d))].add( this.fields[ ( ( gridSizeX*gridSize*s)+ ( d*gridSizeX) + ( f*gridSize) + g)]);
                    }
                }
            }
        }
                
        System.out.println( gameLogic);
        setVisible( true);
    }
}