package service.servlet;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.VoiceResult;

public class AuthMessageServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String mid = request.getParameter("mid");
		try
		{
			String sql = "select gold from t_kx_users where email = ?";
			PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
			preparedStatement.setString(1, mUsername);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				if(rs.getInt("gold") < 10){
					sendJSON(false, "��Ҳ���,��ǰ�����Ŀ"+ rs.getInt("gold") +"��ȥ������ͼƬ׬��Ұɣ�", response);
					return;
				}
				sql = "update t_kx_users set gold = gold - ? where email = ?";
				preparedStatement = mConnection.prepareStatement(sql);
				preparedStatement.setInt(1, 10);
				preparedStatement.setString(2, mUsername);
				preparedStatement.execute();
				sql = "update t_kx_sms set state = 1 where id = ? ";
				preparedStatement = mConnection.prepareStatement(sql);
				preparedStatement.setString(1, mid);
				preparedStatement.execute();
			}
			
			sendJSON(true, "��Ҽ���10����Ϣ�ĺ󼴷�", response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
