package sudoku;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SudokuBoard extends JFrame{

    final String colorButtonField = "#f6f6f6",
                 colorBackGround = "#000000";
    
    final int windowSizeX = 600,
              windowSizeY = 600,              
              gridBorder = 4,  
              gridSizeX = 3,
              gridSizeY = 3,
              gridSize = this.gridSizeX * this.gridSizeY,
              partBoardSizeX = 3,
              partBoardSizeY = 3,
              partBoardSize = this.partBoardSizeX * this.partBoardSizeY,
              boardSizeX = this.partBoardSizeX * this.gridSizeX,
              boardSizeY = this.partBoardSizeY * this.gridSizeY,
              boardSize = this.boardSizeX * this.boardSizeY;
    
    JPanel[] boardPart;
    
    boardField[] fields;
    
    class boardField extends JButton implements ActionListener{
        int value;
        
        public boardField() {
            addActionListener( this);
            this.value = 0;
            this.setText( "");
            this.setBackground( Color.decode( colorButtonField));
            
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            this.value = ++this.value % 10;
            
            if( this.value != 0)
                this.setText( this.value + "");
            else
                this.setText("");
        }
    }
    
    public SudokuBoard() throws HeadlessException {
        super( "Sudoku");
        this.fields = new boardField[ this.boardSize];
        this.boardPart = new JPanel[gridSize];
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        setSize( windowSizeY, windowSizeX);
        setLayout( new GridLayout( gridSizeY, gridSizeX, gridBorder ,gridBorder));
        getContentPane().setBackground( Color.decode( colorBackGround));
        setResizable( false);
        setVisible( true);
        
        for( int f = 0; f < this.boardSize; ++f){
            this.fields[f] = new boardField();
        }
                           
        for( int f = 0; f < this.gridSize; ++f){
            this.boardPart[f] = new JPanel();
            add( this.boardPart[f]);
            this.boardPart[f].setLayout( new GridLayout( partBoardSizeY, partBoardSizeX));
                for( int h = f*partBoardSize; h < f*partBoardSize + this.partBoardSize; ++h)
                    this.boardPart[f].add( this.fields[h]);
                    
        }
        
        
    }
}