import java.util.ArrayList;
import java.util.List;

public class AggregatedInfo {

	List<UserInfo> userList = new ArrayList<UserInfo>();
	
	/**
	 * @return the userList
	 */
	public List<UserInfo> getUserList() {
		return userList;
	}
	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}
}
