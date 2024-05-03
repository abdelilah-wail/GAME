	package projet;
	import java.awt.Image;
import java.io.Serializable;
	public class User implements Serializable{
	private int coins;
	private String username;
	private String password;
	private String name;
	private int user_id;
	private Boolean Gender;
	public User() {}
	public User(int coins, String username, String password, String name, int user_id, Boolean gender) {
		this.coins = coins;
		this.username = username;
		this.password = password;
		this.name = name;
		this.user_id = user_id;
		Gender = gender;
	}
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Boolean getGender() {
		return Gender;
	}
	public void setGender(Boolean gender) {
		Gender = gender;
	}

}
