package model.dto;

public class UserDto {
	private int user_id; //사용자 고유 ID
	private String handle; //백준 handle (백준 ID)
	private String password; //비밀번호 
	private int level; //백준 티어
	
	
	
	public UserDto(int user_id, String handle, String password, int level) {
		super();
		this.user_id = user_id;
		this.handle = handle;
		this.password = password;
		this.level = level;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
