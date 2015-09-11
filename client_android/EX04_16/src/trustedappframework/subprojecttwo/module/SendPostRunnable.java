package trustedappframework.subprojecttwo.module;

import static trustedappframework.subprojecttwo.module.ProjectConfig.mAppContext;

import java.util.HashMap;
import android.util.Log;

public class SendPostRunnable implements Runnable {
	private static final String TAG = "SendPostRunnable";

	private String appId = null;
	private String appId2 = null;
	public static String UUID = null;
	// private String IMEI = null;

	private int postStatus = 0;
	private boolean authStatus = false;
	private boolean tracingStatus = false;
	private String jarFlag = null;

	// 主要是記錄用戶會話過程中的一些用戶的基本訊息
	private HashMap<String, String> session;

	// download file from server
	private String fileName = null;
	private String filePath = null;

	private CheckUser cu;

	public SendPostRunnable() {

	}

	@Override
	public void run() {
		if (postStatus == 0) {
			sendPostCheckUser();
		}

		if (postStatus == 1) {
			sendPostDownload();
		}

		if (postStatus == 2) {
			sendPostTrancing();
		}
	}

	private void sendPostCheckUser() {
		// check user -----
		appId = AuthInfo.getAppId(mAppContext);
		Log.i(TAG, "appId=" + appId + ", appId length= " + appId.length());
		appId2 = AuthInfo.getAppId2(mAppContext);
		Log.i(TAG, "appId2=" + appId2 + ", appId2 length= " + appId2.length());
		UUID = AuthInfo.getUUID(mAppContext);
		Log.i(TAG, "UUID=" + UUID + ", UUID length= " + UUID.length());

		cu = new CheckUser(appId, appId2, UUID, fileName, jarFlag);
		authStatus = cu.checkUser();
		session = cu.getSession();
	}

	private void sendPostDownload() {
		// Asnyc Dowload -----
		new DownloadFileFromURL().execute(filePath);
		// Log.i(TAG, "download encrypted Jar, file_url= " + filePath);
	}

	private void sendPostTrancing() {
		TracingLog trace = new TracingLog(session);
		tracingStatus = trace.sendTracingLog(fileName);
		session = trace.getSession();
	}

	public void setPostStatus(int postStatus) {
		this.postStatus = postStatus;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setJarFlag(String jarFlag) {
		this.jarFlag = jarFlag;

	}

	public boolean getAuthStatus() {
		return authStatus;
	}

	public boolean getTracingStatus() {
		return tracingStatus;
	}

	public HashMap<String, String> getSession() {
		return session;
	}

}
