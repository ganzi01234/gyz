package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Visitor;

public class VisitorsServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		List<Visitor> visitors = new ArrayList<Visitor>();
		try
		{
			String uid = request.getParameter("uid");
			String sql = "";
			PreparedStatement preparedStatement = null;
			
			sql = "select v.*, u.id, u.avatar from t_kx_visitors v "+
						"left join t_kx_users u on u.id = v.visitor_uid "+
						"where v.visited_uid = ? "+
						"order by v.time desc";
			preparedStatement = mConnection
						.prepareStatement(sql);
			preparedStatement.setString(1, uid);
			

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next())
			{
				Visitor visitor = new Visitor();
				visitor.setId(rs.getInt("id"));
				visitor.setVisited_uid(rs.getString("visited_uid"));
				visitor.setVisitor_name(rs.getString("visitor_name"));
				visitor.setVisitor_uid(rs.getString("visitor_uid"));
				visitor.setTime(rs.getString("time"));
				visitor.setAvatar(rs.getString("avatar"));
				visitors.add(visitor);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		sendJSON(visitors, response);
//		sendObject(diaries, response);
	}

}
