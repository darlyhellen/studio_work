/**下午4:47:54
 * @author zhangyh2
 * CleanCache.java
 * TODO
 */
package com.darly.dlvideo.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.darly.dlvideo.R;
import com.darly.dlvideo.base.ConsVideo;

/**
 * @author zhangyh2 CleanCache 下午4:47:54 TODO 清理文件缓存
 */
public class CleanCache {

	/**
	 * 
	 * 下午5:44:12
	 * 
	 * @author Zhangyuhui SetFragment.java TODO 清理缓存。
	 */
	public static void cleanCach() {
		// TODO Auto-generated method stub
		boolean isSuccess = false;
		List<File> files = new ArrayList<File>();
		files.add(new File(ConsVideo.ROOT));
		files.add(new File(ConsVideo.MAINRADIO));
		files.add(new File(ConsVideo.IMAGE));
		files.add(new File(ConsVideo.LOG));
		for (File file : files) {
			isSuccess = deleteDir(file);
		}
		if (isSuccess) {
			ToastApp.showToast(R.string.set_cache_succ);
		} else {
			ToastApp.showToast(R.string.set_cache_fail);
		}
	}

	/**
	 * @param dir
	 * @return 下午5:48:03
	 * @author Zhangyuhui SetFragment.java TODO 递归删除文件
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		if (dir.isFile()) {
			dir.delete();
		}
		// 目录此时为空，可以删除
		return true;
	}
}
