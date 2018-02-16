package sudoku;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameLogic {
    final int boardSize = 81,
              gridSize = 3,
              numberAmount = 9,
              unlockedFields = 35,
              primaryBoard[] = new int[81],
              gameBoard[] = new int[81];
        
    ArrayList< Integer> unlockedIndexs = new ArrayList<>();
    
    public GameLogic() {
        this.CreatePrimaryBoard();
        this.CreateGameBoard();
        this.CreateListOfUnlockedFields();
    }
    
    void CreatePrimaryBoard(){
        try{
            File file = new File("SudokuPrimaryBoard.txt");
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(";");
            int f = 0;
            
            while( scanner.hasNext()){
                this.primaryBoard[ f++] = scanner.nextInt();
            }
        }
        catch(Exception e){
            System.err.println( "FILE NOT FOUND");
        }
    }

    void CreateGameBoard(){
        
        for( int f = 0; f < this.boardSize; ++f)
            this.gameBoard[f] = this.primaryBoard[f];
        
        Random rand = new Random();
        ArrayList< Integer> randChangingNumbers = new ArrayList<>(),
                            indexsOfGameBoard = new ArrayList<>(),
                            tmpIndexToRemove = new ArrayList<>();
        
        int randValuePermutation = rand.nextInt(10),
            tmpValueIndex;
        
        for( int f = 1; f <= this.numberAmount; ++f)
            randChangingNumbers.add(f);
        
        for( int f = 0; f < this.boardSize; ++f)
            indexsOfGameBoard.add(f);
        
        for( int f = 1; f <= this.numberAmount; ++f){
            tmpValueIndex = rand.nextInt( randChangingNumbers.size());
            
            for( Integer g : indexsOfGameBoard){
                 if( this.gameBoard[g] == f){
                     this.gameBoard[g] = randChangingNumbers.get( tmpValueIndex);
                     tmpIndexToRemove.add(g);
                 }
            }
            indexsOfGameBoard.removeAll( tmpIndexToRemove);
            randChangingNumbers.remove( tmpValueIndex);
        }

        for( int f = 0; f < this.boardSize; ++f)
            this.gameBoard[f] = ((this.gameBoard[f] + randValuePermutation-1)%9)+1;
    }
    
    int getValueOfField( int fieldIndex){
        return this.gameBoard[ fieldIndex];
    }
    
    void CreateListOfUnlockedFields(){
        ArrayList< Integer> allIndexs = new ArrayList<>();
        this.unlockedIndexs.clear();
        
        Random rand = new Random();
        int randValue;
        
        for( int f = 0; f < this.boardSize; ++f)
            allIndexs.add(f);
        
        for( int f = 0; f < this.unlockedFields; ++f){
            randValue = rand.nextInt( allIndexs.size());
            this.unlockedIndexs.add( allIndexs.get( randValue));
            allIndexs.remove( randValue);
        }
    }
    
    boolean isFieldUnlocked( int fieldIndex){
        return this.unlockedIndexs.indexOf( ((Integer)fieldIndex)) != -1;
    }
    
    void ResetGame(){
        this.CreateGameBoard();
        this.CreateListOfUnlockedFields();
    }
    
    @Override
    public String toString(){
        String resoult = "";
        for( int f = 0; f < this.boardSize; ++f){
            if( this.gameBoard[f]  < 10 ) resoult += "  " + this.gameBoard[f] + " ";
            else if ( f < 100 ) resoult += " " + this.gameBoard[f]  + " ";
            else resoult += this.gameBoard[f]  + " " ;
            if( ( f + 1) % this.gridSize == 0 && ( f + 1) % numberAmount != 0) resoult += " | ";
            if( ( f + 1) % ( this.gridSize * this.gridSize * this.gridSize) == 0 && f % ( this.gridSize * this.gridSize * this.gridSize * this.gridSize) != 0) resoult += "\n";
            if( ( f + 1) % ( this.gridSize * this.gridSize) == 0) resoult += "\n";
        }
        return resoult;
    }
}
