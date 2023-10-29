import java.util.ArrayList;
import java.util.Collections;  

public class MoveChooser {
	
	
	public static int static_eval(BoardState boardState) {
		int eval = 0;
		int sq_val[][]= {
				{120,-20,20,5,5,20,-20,120},
				{-20,-40,-5,-5,-5,-5,-40,-20},
				{20,-5,15,3,3,15,-5,20},
				{5,-5,3,3,3,3,-5,5},
				{5,-5,3,3,3,3,-5,5},
				{20,-5,15,3,3,15,-5,20},
				{-20,-40,-5,-5,-5,-5,-40,-20},
				{120,-20,20,5,5,20,-20,120}
		};
		for (int i=0; i<8; i++) {
			for (int j=0; j<8; j++) {
				eval=eval+sq_val[i][j]*boardState.getContents(i, j);
			}
		}
		return eval;
	}
	public static int bestnode(BoardState boardState, int searchDepth) {
		if (searchDepth==0 || boardState.gameOver()) {
			return static_eval(boardState);
		}
		else if (boardState.colour==1) {
	    	ArrayList<Move> moves= boardState.getLegalMoves();
	    	int minimaxeval=-10000;
	    	for (int i=0;i<moves.size();i++) {
		    	BoardState newboardState= boardState.deepCopy();
	    		newboardState.makeLegalMove(newboardState.getLegalMoves().get(i).x, newboardState.getLegalMoves().get(i).y);
				minimaxeval=Math.max(minimaxeval,bestnode(newboardState, searchDepth-1));
	    	}
	    	return minimaxeval;
		}
		else{
	    	ArrayList<Move> moves= boardState.getLegalMoves();
	    	int minimaxeval=10000;
	    	for (int i=0;i<moves.size();i++) {
		    	BoardState newboardState= boardState.deepCopy();
	    		newboardState.makeLegalMove(newboardState.getLegalMoves().get(i).x, newboardState.getLegalMoves().get(i).y);
				minimaxeval=Math.min(minimaxeval,bestnode(newboardState, searchDepth-1));
	    	}
	    	return minimaxeval;
		}
	}
    public static Move chooseMove(BoardState boardState){
    	int searchDepth= Othello.searchDepth;
      	ArrayList<Move> moves= boardState.getLegalMoves();
        if(moves.isEmpty()){
            return null;
        }
        else {
    		ArrayList<Integer> bc= new ArrayList<>();
        	for (int i=0; i<moves.size();i++) {
        		BoardState newboardstate = boardState.deepCopy();
        		newboardstate.makeLegalMove(moves.get(i).x, moves.get(i).y);
        		bc.add(bestnode(newboardstate, searchDepth));
        	}
        	int mc= Collections.max(bc);
        	int index=bc.indexOf(mc);
            return moves.get(index);	
        }
    }
}
