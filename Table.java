package projet;
import java.util.ArrayList;
import java.util.Random;
public class Table {
	public Card ChooseNewCard(ArrayList<Card> Cards) {
		Card Card = new Card();
		int RemainingCards = Cards.size();
	    if (RemainingCards > 1) {
	        Random random = new Random();
	        int RandNumber = random.nextInt(RemainingCards);
	        Card = Cards.get(RandNumber);
	        Cards.remove(RandNumber);
	       
	        return Card;
	    }
	    return null;
	}
}
