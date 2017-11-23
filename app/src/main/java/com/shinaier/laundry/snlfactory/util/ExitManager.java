package com.shinaier.laundry.snlfactory.util;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Liu_ZhiChao on 2015/9/8 15:11.
 * 统一关闭activity
 */
public class ExitManager {

	public static ExitManager instance = new ExitManager();
	private List<Activity> activityList = new LinkedList<>();
	private List<Activity> addItemList = new LinkedList<>();
	private List<Activity> addOfflineCollectList = new LinkedList<>(); //线下收衣activity集合
	private List<Activity> editOfflineCollectList = new LinkedList<>(); // 线下收衣编辑衣服

	private ExitManager(){
	}

	public void editOfflineCollectActivity(Activity activity){
		editOfflineCollectList.add(activity);
	}

	public void exitEditOfflineCollectActivity(){
		for (Activity activity : editOfflineCollectList){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
		editOfflineCollectList.clear();
	}

	public void addOfflineCollectActivity(Activity activity){
		addOfflineCollectList.add(activity);
	}

	public void exitOfflineCollectActivity(){
		for (Activity activity : addOfflineCollectList){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
		addOfflineCollectList.clear();
	}

	public void addItemActivity(Activity activity){
		addItemList.add(activity);
	}

	public void exitItemActivity(){
		for (Activity activity :addItemList){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
		addItemList.clear();
	}

	public void addActivity(Activity activity){
		activityList.add(activity);
	}

	public void exit(){
		for(Activity activity : activityList){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
		activityList.clear();
	}

	public void remove(Activity activity) {
		if (activity != null && activityList.contains(activity)){
			activityList.remove(activity);
		}
	}
}
