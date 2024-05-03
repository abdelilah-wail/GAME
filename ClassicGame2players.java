package projet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClassicGame2players extends Thread {
    private User player1;
    private User player2;
    private ObjectOutputStream objectOutputStreamPlayer1;
    private ObjectOutputStream objectOutputStreamPlayer2;
    private ObjectInputStream objectInputStreamPlayer1;
    private ObjectInputStream objectInputStreamPlayer2;
    private boolean complete;
    private Card user1Card;
    private Card user2Card;
    private Card middleCard;
    private Table table;
    private GameCards cards;
    public ClassicGame2players() {
        player1 = new User();
        player2 = new User();
        complete = false;
        user1Card = new Card();
        user2Card = new Card();
        middleCard = new Card();
        table = new Table();
        cards = new GameCards();
    }
  

    public void setComplete(boolean complete) {
		this.complete = complete;
	}


	public User getPlayer1() {
		return player1;
	}


	public void setPlayer1(User player1) {
		this.player1 = player1;
	}


	public User getPlayer2() {
		return player2;
	}


	public void setPlayer2(User player2) {
		this.player2 = player2;
	}


	public ObjectOutputStream getObjectOutputStreamPlayer1() {
		return objectOutputStreamPlayer1;
	}
	public void setObjectOutputStreamPlayer1(ObjectOutputStream objectOutputStreamPlayer1) {
		this.objectOutputStreamPlayer1 = objectOutputStreamPlayer1;
	}
	public ObjectOutputStream getObjectOutputStreamPlayer2() {
		return objectOutputStreamPlayer2;
	}


	public void setObjectOutputStreamPlayer2(ObjectOutputStream objectOutputStreamPlayer2) {
		this.objectOutputStreamPlayer2 = objectOutputStreamPlayer2;
	}


	public ObjectInputStream getObjectInputStreamPlayer1() {
		return objectInputStreamPlayer1;
	}


	public void setObjectInputStreamPlayer1(ObjectInputStream objectInputStreamPlayer1) {
		this.objectInputStreamPlayer1 = objectInputStreamPlayer1;
	}


	public ObjectInputStream getObjectInputStreamPlayer2() {
		return objectInputStreamPlayer2;
	}


	public void setObjectInputStreamPlayer2(ObjectInputStream objectInputStreamPlayer2) {
		this.objectInputStreamPlayer2 = objectInputStreamPlayer2;
	}
	
	public boolean isComplete() {
		return complete;
	}
	public void checkComplete() {
        if (player1 == null || player2 == null) {
          //  complete = false;
        } else {
            complete = true;
        }
    }

    @Override
    public void run() {
        try {
  
        	user1Card = table.ChooseNewCard(cards.cards);
            user2Card = table.ChooseNewCard(cards.cards);
            middleCard = table.ChooseNewCard(cards.cards);

            objectOutputStreamPlayer1.writeObject(new Object[]{middleCard, user1Card,22,player2.getName()});
            objectOutputStreamPlayer1.flush();
            objectOutputStreamPlayer2.writeObject(new Object[]{middleCard, user2Card,22,player1.getName()});
            objectOutputStreamPlayer2.flush();

            Thread player1Thread = new Thread(() -> {
                try {
                    while (true) {
                        Object[] input1 = (Object[]) objectInputStreamPlayer1.readObject();
                        middleCard = (Card)input1[0];
                        user1Card = (Card)input1[1];
                        int i= (int) input1[2];
                switch(i) {
                case 0 : {
                    objectOutputStreamPlayer2.writeObject(new Object[] {middleCard,user2Card,0,player1.getName()});
                    objectOutputStreamPlayer2.flush();
            	
                	break;
                }
                case 1 : {
                	middleCard = user1Card;
                	user1Card = table.ChooseNewCard(cards.cards);
                    objectOutputStreamPlayer2.writeObject(new Object[] {middleCard,user2Card,1,player1.getName()});
                    objectOutputStreamPlayer2.flush();
                    objectOutputStreamPlayer1.writeObject(new Object[] {middleCard,user1Card,-1,player2.getName()});
                    objectOutputStreamPlayer1.flush();
                	
                    break;
                }case 2 : {
                    objectOutputStreamPlayer2.writeObject(new Object[] {middleCard,user2Card,2,player1.getName()});
                    objectOutputStreamPlayer2.flush();
            	
                	break;
                }
                }
                    if(i == 2) {
                    	break;
                    }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            Thread player2Thread = new Thread(() -> {
                try {
                    while (true) {
                        Object[] input2 = (Object[]) objectInputStreamPlayer2.readObject();
                        middleCard = (Card)input2[0];
                        user2Card = (Card)input2[1];
                        int i= (int) input2[2];
                switch(i) {
                case 0 : {
                    objectOutputStreamPlayer1.writeObject(new Object[] {middleCard,user1Card,0,player2.getName()});
                    objectOutputStreamPlayer1.flush();
            	
                	break;
                }
                case 1 : {
                	middleCard = user2Card;
                	user2Card = table.ChooseNewCard(cards.cards);
                    objectOutputStreamPlayer1.writeObject(new Object[] {middleCard,user1Card,1,player2.getName()});
                    objectOutputStreamPlayer1.flush();
                    objectOutputStreamPlayer2.writeObject(new Object[] {middleCard,user2Card,-1,player1.getName()});
                    objectOutputStreamPlayer2.flush();
                	
                    break; 
                }case 2 : {
                    objectOutputStreamPlayer1.writeObject(new Object[] {middleCard,user1Card,2,player2.getName()});
                    objectOutputStreamPlayer1.flush();
            	
                	break;
                }
                }
                if(i == 2) {
                	break;
                }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            player1Thread.start();
            player2Thread.start();
            player1Thread.join();
            player2Thread.join();
             } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
