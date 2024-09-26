package model.dto;

public class UserDto {
	private int user_id; 
	private String handle; 
	private String password; 
	private int level; 
	
	/**
	 * 
	 * @param user_id 사용자 고유 ID
	 * @param handle 백준 handle(백준ID)
	 * @param password 비밀번호
	 * @param level 백준 티어
	 */
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
