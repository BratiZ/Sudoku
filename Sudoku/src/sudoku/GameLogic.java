package sudoku;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    
    ArrayList< Integer> unlockedIndexs = new ArrayList<>();
    
    int logicBoard[],
        sizeGrid,
        sizeBoard,
        sizeNumbers;
    
    public GameLogic( int size) {
        this.sizeBoard = size * size * size * size;
        this.sizeNumbers = size * size;
        this.sizeGrid = size;
        this.logicBoard = new int[ this.sizeBoard];
        this.CreateBoard();
    }
    
    void CreatePlayerBoard(){
        ArrayList< Integer> allIndexs = new ArrayList<>();
        
        Random rand = new Random();
        
        for( int f = 0; f < this.sizeBoard; ++f)
            allIndexs.add(f);
        
        for( int f = 0; f < this.sizeNumbers * this.sizeGrid; ++f){
            this.unlockedIndexs.add( rand.nextInt( allIndexs.size()));
            allIndexs.removeAll( this.unlockedIndexs);
        }
    }
    
    boolean isFieldUnlocked( int fieldIndex){
        return this.unlockedIndexs.indexOf( ((Integer)fieldIndex)) != -1;
    }
    
    int getValueOfField( int fieldIndex){
        return this.logicBoard[fieldIndex];
    }
    
    ArrayList< Integer> CreateElementList(){
        
        ArrayList< Integer> elementsList = new ArrayList<>();
        
        for( int f = 1; f <= this.sizeNumbers; ++f){
            elementsList.add( f);
        }
        
        return elementsList;
    }
    
    ArrayList< Integer> CreateListOfElementsInColumn( int columnIndex){
        ArrayList< Integer> elementsInColumn = new ArrayList<>();
        
        for( int f = ( columnIndex % 9); f < this.sizeBoard; f += this.sizeNumbers)
            if( this.logicBoard[f] != 0)
                elementsInColumn.add( this.logicBoard[f]);
        
        return elementsInColumn;
    }
    
    ArrayList< Integer> CreateListOfElementsInRow( int rowIndex){
        ArrayList< Integer> elementsInRow = new ArrayList<>();
        
        for( int f = 0;  f < this.sizeNumbers; ++f)
           if( this.logicBoard[f + rowIndex] != 0)
                elementsInRow.add( this.logicBoard[f + rowIndex]);
        
        return elementsInRow;
    }
    
    void CreateRow( int rowIndex){
        ArrayList< Integer> elementsList;     
        ArrayList< Integer> elementsInColumn;
        ArrayList< Integer> elementsInRows;
        
        Random rand = new Random();
        int randValue;
        
        for( int f = 0;  f < this.sizeNumbers; ++f)
            if( this.logicBoard[ rowIndex + f] == 0){
                elementsList = this.CreateElementList();
                elementsInRows = this.CreateListOfElementsInRow( rowIndex);
                elementsInColumn = this.CreateListOfElementsInColumn( rowIndex + f);
                elementsList.removeAll( elementsInColumn);
                elementsList.removeAll( elementsInRows);

                randValue = rand.nextInt( elementsList.size());

                this.logicBoard[ rowIndex + f] = elementsList.get( randValue);
                elementsList.remove( (Integer)elementsList.get( randValue));
            }
    }
    
    void CreateDiagonalField( int firstIndex){
        ArrayList< Integer> elementsList = this.CreateElementList();
        
        Random rand = new Random();
        int randValue;
        
        for( int f = 0;  f < this.sizeGrid; ++f)
            for( int g = 0; g < this.sizeGrid; ++g){
                randValue = rand.nextInt( elementsList.size());
                this.logicBoard[ ( firstIndex + f) + ( g * this.sizeNumbers)] = elementsList.get( randValue);
                elementsList.remove( ( Integer)elementsList.get( randValue));
            }
    }
    
    void CreateDiagonal(){
        for( int f = 0; f < this.sizeGrid; ++f){
            this.CreateDiagonalField( ( f * this.sizeGrid) + ( f * this.sizeGrid * this.sizeNumbers));
        }
    }
    
    void CreateBoard(){
        this.CreatePlayerBoard();
        this.CreateDiagonal();
        int f;
        
        for( f = 0; f < 9; ++f){
            try{
                this.CreateRow( f * this.sizeNumbers);
            }
                catch( Exception e){                       
                    this.logicBoard = new int[ this.sizeBoard];
                    this.CreateDiagonal();
                    f = -1;
            }
        }
    }
    
    void gameReset(){
        this.logicBoard = new int[ this.sizeBoard];
        this.CreateBoard();
    }
    
    //--
    void print(){
        
        for( int f = 1; f <= this.sizeBoard; ++f){
            if( f-1 < 10 ) System.out.print( "  " + (f-1) + " " );
            else if ( f < 100 ) System.out.print( " " + (f-1) + " " );
            else System.out.print( (f-1) + " " );
            if( f % sizeGrid == 0 && f % (sizeGrid*sizeGrid) != 0) System.out.print( " | ");
            if( f % (sizeGrid*sizeGrid*sizeGrid) == 0 && f % (sizeGrid*sizeGrid*sizeGrid*sizeGrid) != 0) System.out.print( "\n");
            if( f % (sizeGrid*sizeGrid) == 0) System.out.print( "\n");
        }
    }
    
    @Override
    public String toString(){
        String resoult = "";
        
        
        for( int f = 0; f < this.sizeBoard; ++f){
            if( this.logicBoard[f]  < 10 ) resoult += "  " + this.logicBoard[f] + " ";
            else if ( f < 100 ) resoult += " " + this.logicBoard[f]  + " ";
            else resoult += this.logicBoard[f]  + " " ;
            if( ( f + 1) % sizeGrid == 0 && f % ( sizeGrid * sizeGrid) != 0) resoult += " | ";
            if( ( f + 1) % ( sizeGrid * sizeGrid * sizeGrid) == 0 && f % ( sizeGrid * sizeGrid * sizeGrid * sizeGrid) != 0) resoult += "\n";
            if( ( f + 1) % ( sizeGrid * sizeGrid) == 0) resoult += "\n";
        }
        
        return resoult;
    }
}
