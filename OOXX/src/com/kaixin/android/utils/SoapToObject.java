package com.kaixin.android.utils;

import java.lang.reflect.Method;
import java.util.Date;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.util.Log;

public class SoapToObject
{
	public static Object to(Class c, SoapObject soapObject) throws Exception
	{
		Object obj = c.newInstance();
		int propertyCount = soapObject.getPropertyCount();
		for (int i = 0; i < propertyCount; i++)
		{
			PropertyInfo propertyInfo = new PropertyInfo();

			soapObject.getPropertyInfo(i, propertyInfo);
			Class typeClass = obj.getClass().getMethod(
					"get"
							+ propertyInfo.getName().substring(0, 1)
									.toUpperCase()
							+ propertyInfo.getName().substring(1), null)
					.getReturnType();

			Method method = obj.getClass().getMethod(
					"set"
							+ propertyInfo.getName().substring(0, 1)
									.toUpperCase()
							+ propertyInfo.getName().substring(1), new Class[]
					{ typeClass });
			String value = soapObject.getProperty(i).toString();
	

			if (typeClass == String.class)
			{
				method.invoke(obj, value);

			}
			else if(typeClass == int.class)
			{
				method.invoke(obj, Integer.parseInt(value));

			}


		}
		return obj;
	}
}
