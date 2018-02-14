package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class SudokuMenu extends JFrame{

    MenuButton startGame;
        
    JTextField sudokuSize,
               getSudokuSize,
               errorField;
    
    JPanel menuPanel;
    
    class MenuButton extends JButton implements ActionListener{
        int size;
        
        public MenuButton() {
            this.setText( "Start");
            
            addActionListener(this);
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            
            try{
                size = Integer.parseInt( getSudokuSize.getText());
                
                if( size <= 0)
                    throw new Exception();
                
                new SudokuBoard( size);     
                SudokuMenu.this.setVisible( false);
                
            }
            catch( Exception e){
                getSudokuSize.setText( "Zła wartość!");
            }
        }
    }
    
    public SudokuMenu() throws HeadlessException {
        super( "Menu");
        setSize( 400,100);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground( Color.decode( "#238E23"));
        setResizable( false);
        
        this.startGame = new MenuButton();
        this.sudokuSize = new JTextField( "Sudoku size: ");
        this.getSudokuSize = new JTextField( 8);
        this.menuPanel = new JPanel();       
        
        this.sudokuSize.setEnabled( false);
        this.sudokuSize.setDisabledTextColor( Color.BLACK);
        this.getSudokuSize.setText( "3");
        this.sudokuSize.setEnabled( false);
        this.getSudokuSize.setPreferredSize(new Dimension( 90, 26));
        this.sudokuSize.setPreferredSize(new Dimension( 100, 26));
        this.startGame.setPreferredSize(new Dimension( 70, 26));
        
        this.menuPanel.setLayout( new GridBagLayout());
        this.menuPanel.add( this.sudokuSize);
        this.menuPanel.add( this.getSudokuSize);
        this.menuPanel.add( this.startGame);
        this.menuPanel.setBorder( BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.menuPanel.setBackground( Color.decode( "#238E23"));
        add( this.menuPanel);
        
        setVisible( true);
    }    
    
}
