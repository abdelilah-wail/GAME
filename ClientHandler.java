package projet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static ArrayList<GameCards> gameCards = new ArrayList<>();
    public static Table table = new Table();
    int position;
    User player;
    Card middleCard, userCard;
    private Socket clientSocket;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    private static final Object lock = new Object(); // Lock object for synchronization

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            String request = (String) objectInputStream.readObject();
            if (request.equals("login")) {
                handleLogin();
            } else if (request.equals("signup")) {
                handleSignup();
            } else if (request.equals("updateProfile")) {
                handleProfile();
            } else if (request.equals("checkusername")) {
                handleCheckUserName();
            } else if (request.equals("classic2")) {
               // synchronized (lock) { // Synchronize on lock object
                    clientHandlers.add(this);
                    position = clientHandlers.size() - 1;
                    player = (User) objectInputStream.readObject();

                    if (position % 2 != 0) {
                        for (int i = 0; i < 2; i++) {
                            gameCards.add(new GameCards());
                        }
                       // notifyAll();
                      //  clientHandlers.get(position-1).lock.notifyAll();
                      //  lock.notifyAll(); // Notify all waiting threads
//                        objectOutputStream.writeObject("start");
//                        objectOutputStream.flush();
                        objectOutputStream.writeObject("start");
                        objectOutputStream.flush();
                        middleCard = table.ChooseNewCard(gameCards.get(position).cards);
                        userCard = table.ChooseNewCard(gameCards.get(position).cards);
                        broadcastMessage(middleCard, userCard, 22, player.getName());
                        userCard = table.ChooseNewCard(gameCards.get(position).cards);
                        objectOutputStream.writeObject(new Object[]{middleCard, userCard, 22, clientHandlers.get(position - 1).player.getName()});
                        objectOutputStream.flush();
                    } else {
//                        objectOutputStream.writeObject("wait");
//                        objectOutputStream.flush();
//                      //  wait();
//                        lock.wait(); // Wait for another thread to join
//                
                  
                    	objectOutputStream.writeObject("wait");
                        objectOutputStream.flush();
                
                    }
                }
                Object[] messagefromclient;
                while (clientSocket.isConnected()) {
                    try {
                        messagefromclient = (Object[]) objectInputStream.readObject();
                        middleCard = (Card) messagefromclient[0];
                        userCard = (Card) messagefromclient[1];
                        int i = (int) messagefromclient[2];

                        switch (i) {
                            case 0: {
                                broadcastMessage(middleCard, userCard, 0, player.getName());
                                break;
                            }
                            case 1: {
                                if (position % 2 != 0) {
                                    userCard = table.ChooseNewCard(gameCards.get(position).cards);
                                    objectOutputStream.writeObject(new Object[]{middleCard, userCard, 222, clientHandlers.get(position - 1).player.getName()});
                                    objectOutputStream.flush();
                                    broadcastMessage(middleCard, userCard, 1, player.getName());
                                } else {
                                    userCard = table.ChooseNewCard(gameCards.get(position + 1).cards);
                                    objectOutputStream.writeObject(new Object[]{middleCard, userCard, 222, clientHandlers.get(position + 1).player.getName()});
                                    objectOutputStream.flush();
                                    broadcastMessage(middleCard, userCard, 1, player.getName());
                                }
                                break;
                            }
                            case 2: {
                                broadcastMessage(middleCard, userCard, 2, player.getName());
            //                    removeClientHandler();
                                break;
                            }
                            case 10: {
                                broadcastMessage(middleCard, userCard, 10, player.getName());
            //                    removeClientHandler();
                                break;}
     
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
           // }
        //    removeClientHandler();
          //  closeEverything(clientSocket, objectInputStream, objectOutputStream);

        } catch (IOException | ClassNotFoundException  e) {
            e.printStackTrace();
        }

    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
    }
    private void broadcastMessage(Card middleCard2, Card userCard2, int i, String Name) {
            try {
                if (position % 2 != 0) {
                    clientHandlers.get(position - 1).objectOutputStream.writeObject(new Object[]{middleCard2, userCard2, i, Name});
                    clientHandlers.get(position - 1).objectOutputStream.flush();
                } else {
                    clientHandlers.get(position + 1).objectOutputStream.writeObject(new Object[]{middleCard2, userCard2, i, Name});
                    clientHandlers.get(position + 1).objectOutputStream.flush();
                }
            } catch (Exception e) {
            	System.out.println(1);
                e.printStackTrace();
            }
        
    }

    public void closeEverything(Socket socket, ObjectInputStream objectInputStream2, ObjectOutputStream objectOutputStream2) {
        try {
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            if (objectOutputStream2 != null) {
                objectOutputStream2.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleLogin() throws IOException {
    }

    private void handleSignup() throws IOException {
    }

    private void handleProfile() throws IOException {
    }

    private void handleCheckUserName() throws IOException {
    }

    private User fetchDataFromDatabase(String username, String password) {
        return null;
    }

    private boolean signupToDatabase(String username, String name, String password, String gender) {
        return false;
    }

    private boolean updateUserDatabase(User user, String Username) {
        return false;
    }

    private boolean isUsernameAvailable(String username) {
        return false;
    }
}
