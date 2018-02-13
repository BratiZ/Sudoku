package sudoku;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import javax.swing.*;

public class SudokuMenu extends JFrame{

    JButton startGame;
    
    public SudokuMenu() throws HeadlessException {
        setSize( 600, 600);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        setVisible( true);
        setLayout( new FlowLayout());
        
        this.startGame = new JButton("Start");
        
        add( this.startGame);
        
        
    }    
    
}
