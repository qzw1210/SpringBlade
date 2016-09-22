package com.smallchill.test.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BaseTest {
	public String getType() {
		try {
			BufferedReader strin = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.println("please input something...");
			String str = strin.readLine();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
