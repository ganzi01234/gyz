package service.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Diary;

public class AddVisitorServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String visited = request.getParameter("visited");
		String userid = request.getParameter("userid");
		String nickname = request.getParameter("nickname");
		
		String sql = "select * from t_kx_visitors where visitor_uid = ? and visited_uid = ? ";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = mConnection
					.prepareStatement(sql);
			preparedStatement.setString(1, userid);
			preparedStatement.setString(2, visited);
			ResultSet rs = preparedStatement.executeQuery();

			if(rs.next()){
				sql = "update t_kx_visitors set time = ? where id = ? ";
				preparedStatement = mConnection
						.prepareStatement(sql);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date date = new Date();
				preparedStatement.setString(1, sdf.format(date));
				preparedStatement.setString(2, rs.getString("id"));
				preparedStatement.execute();
				
				response.getWriter().println("ok");
			}else{
				sql = "insert into t_kx_visitors(visitor_name, visited_uid, time, visitor_uid) values(?,?,?,?)";
				preparedStatement = mConnection
						.prepareStatement(sql);
				preparedStatement.setString(1, nickname);
				preparedStatement.setString(2, visited);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				Date date = new Date();
				preparedStatement.setString(3, sdf.format(date));
				preparedStatement.setString(4, userid);
				preparedStatement.execute();
				
				response.getWriter().println("ok");
			}
		} catch (Exception e) {
			response.getWriter().println(e.getMessage());
		}
		
	}

}
