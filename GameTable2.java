package projet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;
public class GameTable2 extends JFrame {   
	private ArrayList< ImageIcon> imageIcons = new ArrayList< ImageIcon>();
    private ArrayList< JLabel> MiddleObjects = new ArrayList<JLabel>();
    private ArrayList< JButton> UserButtons = new ArrayList<JButton>();
    private User User = new User(); 
    private JMenuBar exitBar = new JMenuBar();
    private JButton exitButton = new JButton("Exit");
    private JPanel card1Panel = new JPanel();
    private JPanel card2Panel = new JPanel();
    private JPanel card11Panel = new JPanel();
    private JPanel tablePanel = new JPanel();
    private JPanel MiddlePanel = new JPanel();
    private JLabel user1LabelName = new JLabel();
    private JLabel user2LabelName = new JLabel();
    private JLabel GreatLabel1 = new JLabel();
    private JLabel GreatLabel2 = new JLabel();
    private int User1score = 0;
    private int User2score = 0;
    private final int NUM_ROUNDS = 3;
    private JLabel user1LabelScore = new JLabel();
    private JLabel user2LabelScore = new JLabel();
    private Card UserCard = new Card();
    private Card MiddleCard = new Card();
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public GameTable2(User user)throws IOException, ClassNotFoundException {
    	
    	this.User = user;
        
    	try {
    		//socket = new Socket("192.168.43.76",12345);
    		socket = new Socket("192.168.154.1",12345);
    		
    		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    		objectInputStream = new ObjectInputStream(socket.getInputStream());
         	
    		objectOutputStream.writeObject("classic2");
	        objectOutputStream.flush();	      
			
	        objectOutputStream.writeObject(User);
	        objectOutputStream.flush();
	 
	        Object gameStatus = (Object) objectInputStream.readObject();
		    String status = (String) gameStatus;
	   
//	            if(status.equals("wait")) {
////		    	
//	            System.out.println(status);
//            	dialog.setVisible(true);
//	            gameStatus = (Object) objectInputStream.readObject();
//            	status = (String) gameStatus;       
//	            }
//		    
	          	System.out.println(status);
//		          
//	            if(status.equals("wait")) {
//		        	System.out.println(status);
//	            	dialog.setVisible(true);
//	            }
//	        	gameStatus = (Object) objectInputStream.readObject();
//            	status = (String) gameStatus;	    	
//         
	            if(status.equals("start")) {
		        	System.out.println(status);
	            	//dialog.dispose();     
	            }	
    		
    		}
    		catch (IOException e) {
    			System.out.println("IOException occurred: " + e.getMessage());
    			e.printStackTrace();
    		//	closeEverything(socket, objectInputStream, objectOutputStream);
    		} 
    		catch (ClassNotFoundException e) {
    		
    			System.out.println("ClassNotFoundException occurred: " + e.getMessage());
    			e.printStackTrace();
    	//		closeEverything(socket, objectInputStream, objectOutputStream);
    		}
    	setimageIcons();
        user1LabelName = new JLabel(User.getName());
        user2LabelName = new JLabel();
        
        User.setCoins(User.getCoins() - 500);
        setTitle("Classic Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
     
        tablePanel.setLayout(new GridLayout(8, 1));
        exitBar.add(exitButton);

        //Add card2panel 
        card2Panel.setLayout(new GridLayout(1, 3));      
        String Score2 = String.valueOf(User2score);
        user2LabelScore.setText(Score2);
        
        card2Panel.add(user2LabelName);
        card2Panel.add(user2LabelScore);
        card2Panel.add(GreatLabel2);
        
        tablePanel.add(card2Panel);
        
        //add space
        tablePanel.add(new JLabel());   
             
        //middle panel
        MiddlePanel.setLayout(new GridLayout(1, 11));
        MiddlePanel.add(new JPanel());
        MiddlePanel.add(new JPanel());
        MiddlePanel.add(new JPanel());
        for (int i = 0 ; i < 8 ; i ++) {
            MiddleObjects.add(new JLabel());
        }
        for (JLabel jLabel : MiddleObjects) {
            MiddlePanel.add(jLabel);
        }
        tablePanel.add(MiddlePanel); 
        
        //add space
        tablePanel.add(new JLabel());   
        //add space
        tablePanel.add(new JLabel());   
        
        // add card1Panel 
        card1Panel.setLayout(new GridLayout(1, 11));
        tablePanel.add(card1Panel);
        card1Panel.add(new JPanel());
        card1Panel.add(new JPanel());
        card1Panel.add(new JPanel());
        for (int i = 0 ; i < 8 ; i ++) {
            UserButtons.add(new JButton());
        }
        for (JButton jButton : UserButtons) {
            jButton.setOpaque(false);
            jButton.setContentAreaFilled(false);
            jButton.setBorderPainted(false);

        	card1Panel.add(jButton);
        }

        //
      
        //add space
        tablePanel.add(new JLabel());   
        
        //add card11panel 
        card11Panel.setLayout(new GridLayout(1, 3));
        String Score1 = String.valueOf(User2score);
        user2LabelScore.setText(Score1);
        
        card11Panel.add(user1LabelName);
        card11Panel.add(user1LabelScore);
        card11Panel.add(GreatLabel1);
        
        tablePanel.add(card11Panel);
     
        
        add(exitBar, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        for (JButton jButton : UserButtons) {
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ImageIcon clickedIcon = (ImageIcon) jButton.getIcon();
                    int k = getImageNumber(clickedIcon);
                    check(k); 
                }
            });

        }
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Main2(User);
            }
        });
        
    }
    private void check(int ObjectInt) {
        
        int j = 0;
        int i = 0;
    
        while (i < MiddleCard.NumberofObjects) {

        	if (MiddleCard.Objects.get(i) == ObjectInt) {
        		if (User1score <= NUM_ROUNDS - 1) {
        		 great(1, true);
                 try {
                	MiddleCard = UserCard;
                	User1score++;
                	update();
                	objectOutputStream.writeObject(new Object[] {MiddleCard,UserCard,1});
					objectOutputStream.flush();
                 } catch (IOException e) {
           //     closeEverything(socket, objectInputStream, objectOutputStream);
					e.printStackTrace();
				}
                    i = 8;
                    j = 1;
                } else {
                    try {
                    win();
                    objectOutputStream.writeObject(new Object[] {MiddleCard,UserCard,2});
    				objectOutputStream.flush();
    			     } catch (IOException e) {
    	//		     closeEverything(socket, objectInputStream, objectOutputStream);
    			     e.printStackTrace();
    				}
                    i = 8;
                    j = 1;
                }
            }
            i++;
        }
        if (j == 0) {
        User1score--;
        great(1, false);
	    update();
        try {
			objectOutputStream.writeObject(new Object[] {MiddleCard,UserCard,0});
			objectOutputStream.flush();
	     } catch (IOException e) {
        //    closeEverything(socket, objectInputStream, objectOutputStream);
	    	 e.printStackTrace();
		}        }    
}
    public void listenFromUser() {
    	JDialog dialog = new JDialog((JFrame) null, "Waiting for player", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(null); 
        JPanel messagePanel = new JPanel();
        messagePanel.add(new JLabel("Waiting for another player to join..."));
        dialog.add(messagePanel);
        dialog.setResizable(false);
        dialog.setVisible(true);
	    new Thread(new Runnable() {
            @Override
            public void run() {
                Object[] messageFromUser;
             	
                while (socket.isConnected()) {
                    try {
                        messageFromUser = (Object[]) objectInputStream.readObject();
                        Card middleCard = (Card) messageFromUser[0];
                        Card userCard = (Card) messageFromUser[1];
                        int i = (int) messageFromUser[2];
                        switch(i) {
                        case 22 : {
                        MiddleCard = middleCard;
                        UserCard = userCard;
                        user2LabelName.setText((String) messageFromUser[3]);
                        dialog.dispose();
                        update();	
                        setVisible(true);
                        break;
                        }
                        case 0 : {
                        great(2,false);	
                        User2score--;
                        update();	
                        break;
                        }
                        case 1 : {
                        	great(2,true);
                            MiddleCard = middleCard;
                        	User2score++;
                        	update();
                            break;
                        }
                        case 2 : {
                         lose();
                         break;
                        }
                        default : {
                       // user2LabelName.setText((String) messageFromUser[3]);
                        UserCard = userCard;
                        update();
                       }
                        }                        
                    } catch (Exception e) {
       //                 closeEverything(socket, objectInputStream, objectOutputStream);
                    }
                }
            }
        }).start();
    }
    private int getImageNumber(ImageIcon icon) {
        String filename = icon.getDescription();
        String[] parts = filename.split("/");
        String filenameWithExtension = parts[parts.length - 1];
        String[] filenameParts = filenameWithExtension.split("\\.");
        String numberString = filenameParts[0];
        return Integer.parseInt(numberString);
    }
   
    private void update() {
        String objectButton;
      
        for (int i = 0 ; i < 8 ; i ++) {
            MiddleObjects.get(i).setIcon(imageIcons.get(MiddleCard.Objects.get(i)));
        }
        
        for (int i = 0 ; i < 8 ; i ++) {
            UserButtons.get(i).setIcon(imageIcons.get(UserCard.Objects.get(i)));
        }
        objectButton = String.valueOf(User1score);
        user1LabelScore.setText(objectButton);
        objectButton = String.valueOf(User2score);
        user2LabelScore.setText(objectButton);
    }
  public void win() {
	 // closeEverything(socket, objectInputStream, objectOutputStream);
      String message = "You  wins!     : 1000 Coins";
      String[] options = {"Replay", "Exit"};
      int choice = JOptionPane.showOptionDialog(this, message, "Congratulations!",
              JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
              options[0]);

      if (choice == JOptionPane.YES_OPTION) {
          dispose();
          new Main3(User);
      } else if (choice == JOptionPane.NO_OPTION) {
          dispose();
          new Main2(User);
      }
}
  public void lose() {
	  //closeEverything(socket, objectInputStream, objectOutputStream);
      String message = "You  Lose!     : 1000 Coins";
      String[] options = {"Replay", "Exit"};
      int choice = JOptionPane.showOptionDialog(this, message, "Congratulations!",
              JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options,
              options[0]);

      if (choice == JOptionPane.YES_OPTION) {
          dispose();
          new Main3(User);
      } else if (choice == JOptionPane.NO_OPTION) {
          dispose();
          new Main2(User);
      }
}
    private void setimageIcons() {
        imageIcons.add(null);
        for (int i = 1; i < 58; i++) {
            String filename = String.format("%02d.png", i);
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(filename));
            originalIcon.setDescription(filename); 
            originalIcon.setImage(originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            imageIcons.add(originalIcon);
          	}
        }
        
    public void closeEverything(Socket socket, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
     private void great(int userNumber,boolean found) {
            JLabel greatLabel;
            if (userNumber == 1) {
                greatLabel = GreatLabel1;
            } else {
                greatLabel = GreatLabel2;
            }
            if (found) {
            	 greatLabel.setForeground(Color.GREEN);
                 greatLabel.setText("              Great ");
            } else {
            	greatLabel.setForeground(Color.RED);
                greatLabel.setText("              Couldn't Find The Object.");
            }
            Timer timer = new Timer(3000, e -> greatLabel.setText(""));
            timer.setRepeats(false);
            timer.start();
        
     }
     public static void main(String[] args) throws ClassNotFoundException, IOException {
	
    	 
//    	 User User1 = new User();
//    	 User1.setName("monder");//    	 User1.setUser_id(1);	
//    	 GameTable3 g1=new GameTable3(User1);
//         g1.listenFromUser();

    
    	 User User2 = new User();
         User2.setName("monder");
         User2.setUser_id(1);	
    	 GameTable2 g2=new GameTable2(User2);
         g2.listenFromUser();	             
     }
}