package KDD.testRunner;

import org.openqa.selenium.grid.Main;
import org.testng.annotations.Test;

import KDD.utils.ReusableFunction;

public class ExecuteTestRunner {
	
	@Test
	public void execute() throws InterruptedException {
		ReusableFunction func=new ReusableFunction();
		String[][] keywordsData=func.fetchDataFromExcel();

		int rownum=keywordsData.length;
		int colnum=keywordsData[0].length;

		String TC_Name=keywordsData[0][0];
		String TC_Step_No=keywordsData[0][1];
		String TC_Step_Name=keywordsData[0][2];
		String TC_Func=keywordsData[0][3];
		String TC_Location=keywordsData[0][4];
		String TC_LV=keywordsData[0][5];
		String TC_Param=keywordsData[0][6];
		String TC_Execute=keywordsData[0][7];

		for (int counter =1; counter < rownum; counter++) {
			String function = keywordsData[counter][3];
			String LocatorBy = keywordsData[counter][4];
			String locatorElement = func.fetchprop(keywordsData[counter][5]);
			String content_param = keywordsData[counter][6];
			String execute=keywordsData[counter][7];
			if (execute.equals("Y")) {
				System.out.println(function + "\t" + LocatorBy + "\t" + locatorElement + "\t" + content_param);

				switch (function) {
				case "LaunchAppl" :
					func.LaunchAppl();
					break;
				case "FillText":
					func.FillText(LocatorBy, locatorElement, content_param);
					break;
				case "FillNumber":
					func.FillNumber(LocatorBy, locatorElement, content_param);
					break;
				case "click":
					func.click(LocatorBy, locatorElement);
					break;
				case "isPresent":
					func.isPresent(LocatorBy, locatorElement);
					break;
				case "Wait":
					func.Wait();
					break;
				case "scroll":
					func.scroll();
					break;
				case "Scroll_Ele_click":
					func.Scroll_Ele_click(LocatorBy, locatorElement);
					break;
				case "validateValue":
					func.validateValue(LocatorBy, locatorElement, content_param);

				}
			}

		}
	}
}
