package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
                 colourButtonUnlocked = "#c2c2c2",
                 colourButtonGoodAnswer = "#00ff66",
                 colourButtonWrongAnswer = "#ff0033";
    
    final int windowSizeX = 600,
              windowSizeY = 750,              
              gridBorder = 4,  
              gridSizeX = 3,
              gridSizeY = 3,
              gridSize = gridSizeX * gridSizeY,
              partBoardSizeX = gridSizeX,
              partBoardSizeY = gridSizeY,
              partBoardSize = partBoardSizeX * partBoardSizeY,
              boardSizeX = partBoardSizeX * gridSizeX,
              boardSizeY = partBoardSizeY * gridSizeY,
              boardSize = boardSizeX * boardSizeY;
    
    boolean buttonHelpPressed,
            buttonClearPressed;
    
    JPanel boardPart[],
           boardBoard,
           boardTop;
    
    boardField fields[];
      
    BoardTopButton topButtonCheck,
                   topButtonReset,
                   topButtonClearField,                   
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
                gameLogic.ResetGame();
                ResetFieldsButtons();
            }
            else if( source == topButtonClearField){
                ClearField();
            }
            else if( source == topButtonGetHelp){
                GetHelp();
            }
        }
    }
    
    class boardField extends JButton implements ActionListener{
        int value;
        boolean Unlocked;
        
        public boardField() {
            addActionListener( this);    
            this.Unlocked = false;
            this.value = 0;
            this.setText( "");
            this.setBackground( Color.decode( colourButtonField));
        }
        
        public boardField( int fieldIndex){
            addActionListener( this); 
            this.setFont( new Font( "Arial", Font.BOLD, 50));
            
            if( gameLogic.isFieldUnlocked( fieldIndex )){
                this.Unlocked = true;
                this.setEnabled( false);
                this.value = gameLogic.getValueOfField( fieldIndex);
                this.setText( "" + this.value);
                this.setBackground( Color.decode( colourButtonUnlocked) );
            }
            else{
                this.setEnabled( true);
                this.Unlocked = false;
                this.value = 0;
                this.setText( "");
                this.setBackground( Color.decode( colourButtonField));
            }
        }
        
        void ResetButton(int fieldIndex){
            if( gameLogic.isFieldUnlocked( fieldIndex )){
                this.Unlocked = true;
                setEnabled( false);
                this.value = gameLogic.getValueOfField( fieldIndex);
                setText( "" + this.value);
                setBackground( Color.decode( colourButtonUnlocked) );
            }
            else{
                this.setEnabled( true);
                this.Unlocked = false;
                this.value = 0;
                this.setText( "");
                this.setBackground( Color.decode( colourButtonField));
            }
        }
        
        void ShowValue( int fieldIndex){
            this.Unlocked = true;
            this.setEnabled( false);
            this.value = gameLogic.getValueOfField( fieldIndex);
            this.setText( "" + this.value);
            this.setBackground( Color.decode( colourButtonUnlocked) );
        }
        
        void MakeAsGood(){
            this.setEnabled( false);
            this.setBackground( Color.decode( colourButtonGoodAnswer));
        }
        
        void MakeAsWrong(){
            this.setBackground( Color.decode( colourButtonWrongAnswer));
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            this.value = ++this.value % 10;
            this.setBackground( Color.decode( colourButtonField));
            Object source = ae.getSource();
            
            if( buttonHelpPressed){
                for( int f = 0; f < boardSize; ++f)
                    if( source == fields[f]){
                        fields[f].ShowValue( f);
                        buttonHelpPressed = false;
                    }
            }
            else if( buttonClearPressed){
                for( int f = 0; f < boardSize; ++f)
                    if( source == fields[f]){
                        fields[f].value = 0;
                        fields[f].setText("");
                        buttonClearPressed = false;
                    }
            }
            else          
                if( this.value != 0)
                    this.setText( this.value + "");
                else
                    this.setText("");
        }
    }
    
    void CheckBoard(){
        for( int f = 0; f < this.boardSize; ++f){
            if( !this.fields[f].Unlocked && this.fields[f].value != 0)
                if( this.fields[f].value == this.gameLogic.getValueOfField(f)){
                    this.fields[f].MakeAsGood();
                }
                else
                    this.fields[f].MakeAsWrong();
        }
    }
    
    void ResetFieldsButtons(){
        for( int f = 0; f< this.fields.length; ++f){
            this.fields[f].ResetButton(f);
        }
    }
    
    void ClearField(){
        if( this.buttonClearPressed)
            this.buttonClearPressed = false;
        else 
            this.buttonClearPressed = true;
    }
    
    void GetHelp(){
        if( this.buttonHelpPressed)
            this.buttonHelpPressed = false;
        else 
            this.buttonHelpPressed = true;
    }
    
    void CreateFieldsButtons(){
        for( int s = 0;  s < this.gridSizeX; ++s){
            for( int d = 0; d < this.gridSizeX; ++d){
                this.boardPart[ ( ( s*this.gridSizeX) + ( d))] = new JPanel();
                this.boardBoard.add( this.boardPart[ ( ( s*this.gridSizeX) + ( d))]);
                this.boardPart[ ( ( s*this.gridSizeX) + ( d))].setLayout( new GridLayout( this.partBoardSizeY, this.partBoardSizeX));
                for( int f = 0; f < this.gridSizeX; ++f){
                    for( int g = 0; g < this.gridSizeX; ++g){
                        this.boardPart[ (( s*this.gridSizeX) + ( d))].add( this.fields[ ( ( this.gridSizeX*this.gridSize*s)+ ( d*this.gridSizeX) + ( f*this.gridSize) + g)]);
                    }
                }
            }
        }
    }
    
    public SudokuBoard() throws HeadlessException {
        super( "Sudoku");
        this.gameLogic = new GameLogic();
        
        this.buttonHelpPressed = false;
        this.buttonClearPressed = false;
        
        this.topButtonCheck = new BoardTopButton("Check");
        this.topButtonReset = new BoardTopButton("Reset");
        this.topButtonClearField = new BoardTopButton("Clear Field");
        this.topButtonGetHelp = new BoardTopButton("Help");
        
        this.boardTop = new JPanel();
        this.boardBoard = new JPanel();
        this.fields = new boardField[ this.boardSize];
        this.boardPart = new JPanel[ gridSize];
        
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        this.setSize( this.windowSizeX, this.windowSizeY);
        this.setLayout( new BorderLayout());
        this.boardBoard.setLayout( new GridLayout( this.gridSizeY, this.gridSizeX, this.gridBorder ,this.gridBorder));
        boardBoard.setBackground( Color.decode( this.colourBackGround));
        this.setResizable( false);
        this.boardTop.setBackground( Color.decode( this.colourBackGround));
        this.boardTop.setLayout( new FlowLayout());
        
        
        this.boardTop.add( this.topButtonCheck);
        this.boardTop.add( this.topButtonReset);
        this.boardTop.add( this.topButtonClearField);
        this.boardTop.add( this.topButtonGetHelp);

        
        this.add( BorderLayout.NORTH, this.boardTop);
        this.add( BorderLayout.CENTER, this.boardBoard);
        
        for( int f = 0; f < this.boardSize; ++f){
            this.fields[f] = new boardField(f);
        }
       
        this.CreateFieldsButtons();
                
        setVisible( true);
    }
}