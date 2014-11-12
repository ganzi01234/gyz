package service.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Update;
import service.UserInfoResult;
import service.dao.MyOpenconnection;

public class UpdateServlet extends CommonServlet
{
	private static String info;
	private static String code;
	private static String name;
	private static boolean type;
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			InputStream is;
			try {
				is = MyOpenconnection.class.getClassLoader().getResourceAsStream("service/servlet/update.properties");
				Properties props=new Properties();
				props.load(is);
				info=props.getProperty("info");
				code=props.getProperty("code");
				name=props.getProperty("name");
				type=Boolean.parseBoolean(props.getProperty("type"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Update update = new Update(code, info, name, type);
			sendJSON(update, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
