/**上午10:03:25
 * @author zhangyh2
 * VitamioHelper.java
 * TODO
 */
package io.vov.vitamio;

/**
 * @author zhangyh2 VitamioHelper 上午10:03:25 TODO
 */
public class VitamioHelper {

	public static VitamioHelper instance;

	private VitamioListener mVitamioListener;

	/**
	 * 上午10:04:12
	 * 
	 * @author zhangyh2
	 */
	private VitamioHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the instance
	 */
	public static VitamioHelper getInstance() {
		if (instance == null) {
			instance = new VitamioHelper();
		}
		return instance;
	}

	public VitamioListener getmVitamioListener() {
		return mVitamioListener;
	}

	public void setmVitamioListener(VitamioListener mVitamioListener) {
		this.mVitamioListener = mVitamioListener;
	}

}
