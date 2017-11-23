package com.zbar.lib.manager;

import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;

import java.util.regex.Pattern;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * 时间: 2014年5月9日 下午12:22:12
 * 版本: V_1.0.0
 * 描述: 相机参数配置
 */
final class CameraConfigurationManager {

	private static final String TAG = CameraConfigurationManager.class.getSimpleName();

	private static final int TEN_DESIRED_ZOOM = 27;
	private static final Pattern COMMA_PATTERN = Pattern.compile(",");

	private Point screenResolution;
	private Point cameraResolution;
	private int previewFormat;
	private String previewFormatString;

	@SuppressWarnings("deprecation")
	void initFromCameraParameters(Camera camera, int width, int height) {
		Camera.Parameters parameters = camera.getParameters();
		previewFormat = parameters.getPreviewFormat();
		previewFormatString = parameters.get("preview-format");
//		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//		Display display = manager.getDefaultDisplay();
//		screenResolution = new Point(display.getWidth(), display.getHeight());
		//有改动
		//使用自定义的预览宽高，不使用屏幕大小作为宽高
		screenResolution = new Point(width, height);

		Point screenResolutionForCamera = new Point();
		screenResolutionForCamera.x = screenResolution.x;
		screenResolutionForCamera.y = screenResolution.y;

		//保证宽大于高，因为默认横屏显示的，后面要调整角度，所以调换宽高
		if (screenResolution.x < screenResolution.y) {
			screenResolutionForCamera.x = screenResolution.y;
			screenResolutionForCamera.y = screenResolution.x;
		}
		//获取最终显示的预览大小
		cameraResolution = getCameraResolution(parameters, screenResolutionForCamera);
	}

	/**
	 * 设置需要的相机参数
	 */
	void setDesiredCameraParameters(Camera camera) {
		Camera.Parameters parameters = camera.getParameters();
		//设置预览大小
		parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
		setFlash(parameters);
		setZoom(parameters);
		//设置显示角度，因为默认是横屏显示，所以需要调整角度
		camera.setDisplayOrientation(90);
		camera.setParameters(parameters);
	}

	Point getCameraResolution() {
		return cameraResolution;
	}

	Point getScreenResolution() {
		return screenResolution;
	}

	int getPreviewFormat() {
		return previewFormat;
	}

	String getPreviewFormatString() {
		return previewFormatString;
	}

	/**
	 * 获取相机显示的分辨率
	 */
	private static Point getCameraResolution(Camera.Parameters parameters, Point screenResolution) {
		//获取系统提供的一些分辨率比，1280x720,1056x864...
		String previewSizeValueString = parameters.get("preview-size-values");
		if (previewSizeValueString == null) {
			previewSizeValueString = parameters.get("preview-size-value");
		}

		Point cameraResolution = null;
		//根据我们设置的预览大小，选择一个和系统提供的最接近的预览大小
		if (previewSizeValueString != null) {
			cameraResolution = findBestPreviewSizeValue(previewSizeValueString, screenResolution);
		}

		if (cameraResolution == null) {

			cameraResolution = new Point((screenResolution.x >> 3) << 3, (screenResolution.y >> 3) << 3);
		}

		return cameraResolution;
	}

	private static Point findBestPreviewSizeValue(CharSequence previewSizeValueString, Point screenResolution) {
		int bestX = 0;
		int bestY = 0;
		int diff = Integer.MAX_VALUE;
		for (String previewSize : COMMA_PATTERN.split(previewSizeValueString)) {
//			LogUtil.e("zhang","previewSize = " + previewSize);

			previewSize = previewSize.trim();

			int dimPosition = previewSize.indexOf('x');
			if (dimPosition < 0) {
				continue;
			}

			int newX;
			int newY;
			try {
				newX = Integer.parseInt(previewSize.substring(0, dimPosition));
				newY = Integer.parseInt(previewSize.substring(dimPosition + 1));

			} catch (NumberFormatException nfe) {
				continue;
			}

			int newDiff = Math.abs(newX - screenResolution.x) + Math.abs(newY - screenResolution.y);//以宽和高同时为标准

//			int newDiff = Math.abs(newX - screenResolution.x);//只以宽为判断标准
//			int newDiff = Math.abs(newY - screenResolution.y);//只以高为判断标准
			if (newDiff == 0) {
				bestX = newX;
				bestY = newY;
				break;
			} else if (newDiff < diff) {
				bestX = newX;
				bestY = newY;
				diff = newDiff;
			}

		}

		if (bestX > 0 && bestY > 0) {
			return new Point(bestX, bestY);
		}
		return null;
	}

	private static int findBestMotZoomValue(CharSequence stringValues,
	                                        int tenDesiredZoom) {
		int tenBestValue = 0;
		for (String stringValue : COMMA_PATTERN.split(stringValues)) {
			stringValue = stringValue.trim();
			double value;
			try {
				value = Double.parseDouble(stringValue);
			} catch (NumberFormatException nfe) {
				return tenDesiredZoom;
			}
			int tenValue = (int) (10.0 * value);
			if (Math.abs(tenDesiredZoom - value) < Math.abs(tenDesiredZoom
					- tenBestValue)) {
				tenBestValue = tenValue;
			}
		}
		return tenBestValue;
	}

	private void setFlash(Camera.Parameters parameters) {
		parameters.set("flash-value", 2);
		parameters.set("flash-mode", "off");
	}

	private void setZoom(Camera.Parameters parameters) {

		String zoomSupportedString = parameters.get("zoom-supported");
		if (zoomSupportedString != null
				&& !Boolean.parseBoolean(zoomSupportedString)) {
			return;
		}

		int tenDesiredZoom = TEN_DESIRED_ZOOM;

		String maxZoomString = parameters.get("max-zoom");
		if (maxZoomString != null) {
			try {
				int tenMaxZoom = (int) (10.0 * Double
						.parseDouble(maxZoomString));
				if (tenDesiredZoom > tenMaxZoom) {
					tenDesiredZoom = tenMaxZoom;
				}
			} catch (NumberFormatException nfe) {
				Log.w(TAG, "Bad max-zoom: " + maxZoomString);
			}
		}

		String takingPictureZoomMaxString = parameters
				.get("taking-picture-zoom-max");
//		LogUtil.e("zhang","takingPictureZoomMaxString = " + takingPictureZoomMaxString);
		if (takingPictureZoomMaxString != null) {
			try {
				int tenMaxZoom = Integer.parseInt(takingPictureZoomMaxString);
				if (tenDesiredZoom > tenMaxZoom) {
					tenDesiredZoom = tenMaxZoom;
				}
			} catch (NumberFormatException nfe) {
				Log.w(TAG, "Bad taking-picture-zoom-max: "
						+ takingPictureZoomMaxString);
			}
		}

		String motZoomValuesString = parameters.get("mot-zoom-values");
		if (motZoomValuesString != null) {
			tenDesiredZoom = findBestMotZoomValue(motZoomValuesString,
					tenDesiredZoom);
		}

		String motZoomStepString = parameters.get("mot-zoom-step");
		if (motZoomStepString != null) {
			try {
				double motZoomStep = Double.parseDouble(motZoomStepString
						.trim());
				int tenZoomStep = (int) (10.0 * motZoomStep);
				if (tenZoomStep > 1) {
					tenDesiredZoom -= tenDesiredZoom % tenZoomStep;
				}
			} catch (NumberFormatException nfe) {
				// continue
			}
		}

		// Set zoom. This helps encourage the user to pull back.
		// Some devices like the Behold have a zoom parameter
		if (maxZoomString != null || motZoomValuesString != null) {
			parameters.set("zoom", String.valueOf(tenDesiredZoom / 10.0));
		}

		// Most devices, like the Hero, appear to expose this zoom parameter.
		// It takes on values like "27" which appears to mean 2.7x zoom
		if (takingPictureZoomMaxString != null) {
			parameters.set("taking-picture-zoom", tenDesiredZoom);
		}
	}
}
