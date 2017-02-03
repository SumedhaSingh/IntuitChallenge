import java.util.HashMap;

public class UserInfo {
	
	HashMap<String, Float> categoryMap = new HashMap<String, Float>();
	Float totalExpenses;
	/**
	 * @return the categoryMap
	 */
	public HashMap<String, Float> getCategoryMap() {
		return categoryMap;
	}
	/**
	 * @param categoryMap the categoryMap to set
	 */
	public void setCategoryMap(HashMap<String, Float> categoryMap) {
		this.categoryMap = categoryMap;
	}
	/**
	 * @return the totalExpenses
	 */
	public Float getTotalExpenses() {
		return totalExpenses;
	}
	/**
	 * @param totalExpenses the totalExpenses to set
	 */
	public void setTotalExpenses(Float totalExpenses) {
		this.totalExpenses = totalExpenses;
	}
	
	
}
