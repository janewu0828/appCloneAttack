package trustedappframework.subprojecttwo.interfaces;

import android.graphics.drawable.Drawable;

public interface MainInterface {

	public String sayHello();

	public String loadMethod();

	public void setS1(int s1);

	public void setAns(int ans);

	public void setChoiceStatus(int choiceStatus);

	public void setD01(Drawable d01);

	public void setD02(Drawable d02);

	public void setD03(Drawable d03);

	public void setResult(String result);

	public int getS1();

	public int getAns();

	public int getChoiceStatus();

	public Drawable getD01();

	public Drawable getD02();

	public Drawable getD03();

	public String getResult();

}
