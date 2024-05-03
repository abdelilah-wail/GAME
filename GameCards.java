package projet;
import java.util.*;
class GameCards{
	    	final int MAX_N = 7;
	    	int i, j, k;
	        int n = MAX_N;
	        int numOfSymbols = n + 1;
	        Card card = new Card();
	        ArrayList<Card> cards = new ArrayList<Card>();
	        public GameCards() {
	        	for (i = 1; i <= n + 1; i++) {
		          card.Objects.add(i);
	            }
	           cards.add(card);
		        for (j = 1; j <= n; j++) {
		            card = new Card();
		            card.Objects.add(1);
			         for (k = 1; k <= n; k++) {
		                card.Objects.add(n * j + (k + 1));
		            }
		            cards.add(card);
		 	       	        }
		        for (i = 1; i <= n; i++) {
		            for (j = 1; j <= n; j++) {
		                card = new Card();
		                card.Objects.add(i + 1);
		                for (k = 1; k <= n; k++) {
		                    card.Objects.add(n + 2 + n * (k - 1) + (((i - 1) * (k - 1) + j - 1) % n));
		                }
		                cards.add(card);
		     	 }
		        }
                  }
	        	  }
