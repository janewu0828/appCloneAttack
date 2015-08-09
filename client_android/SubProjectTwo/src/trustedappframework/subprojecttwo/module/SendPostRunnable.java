package trustedappframework.subprojecttwo.module;

public class SendPostRunnable implements Runnable {
	private static final String TAG = "SendPostRunnable";

	private String appId = null;
	private String appId2 = null;
	private String UUID = null;

	private boolean authStatus = false;

	private CheckUser cu;

	public SendPostRunnable(String appId, String appId2, String UUID) {
		this.appId = appId;
		this.appId2 = appId2;
		this.UUID = UUID;
	}

	@Override
	public void run() {
		sendPostDataToInternet();

	}

	private void sendPostDataToInternet() {
		// check user -----
		cu = new CheckUser(appId, appId2, UUID);
		authStatus = cu.checkUser();
	}

	public boolean getAuthStatus() {
		return authStatus;
	}

}
