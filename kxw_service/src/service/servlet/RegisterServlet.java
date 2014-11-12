package service.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.dao.MyOpenconnection;
import service.utils.HttpUtil;
import service.utils.JSONUtil;
import service.utils.StringUtil;

public class RegisterServlet extends HttpServlet
{

	private String savedPath;
	private String filename;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("jinlail ");
		Connection connection = null;
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String nickname = new String(request.getParameter("nickname").getBytes(
				"iso-8859-1"), "utf-8");
		String password = request.getParameter("pwd");
		String avatar = request.getParameter("avatar");
		if(!StringUtil.isNull(avatar)){
			ServletContext s1 = this.getServletContext(); 
			String photoRootPath=s1.getRealPath("/") + "images/";
			String photoPath = photoRootPath + email.hashCode();
			savedPath = email.hashCode() + "/";
			filename = CommonServlet.getRandomFileName();
			try {
				download(avatar, filename, photoPath);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}

		String sql = "select * from t_kx_users where email = ?";
		try
		{
			connection = MyOpenconnection.getconnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();
			if(!rs.next()){
				sql = "insert into t_kx_users(email, name, password, avatar, sex_id) values(?,?,?,?,?)";
				preparedStatement = connection
						.prepareStatement(sql);
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, nickname);
				preparedStatement.setString(3, password);
				preparedStatement.setString(4, savedPath + filename);
				preparedStatement.setString(5, gender);
				preparedStatement.execute();
		
				sendJSON(true, "ע��ɹ���", response);
			}else{
				sendJSON(false, "�û����Ѵ��ڣ�", response);
			}
			
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
			MyOpenconnection.closeConnection(connection);
		}finally{
			MyOpenconnection.closeConnection(connection);
		}

	}
	
	public static void download(String urlString, String filename,String savePath) throws Exception {
	    // ����URL
	    URL url = new URL(urlString);
	    // ������
	    URLConnection con = url.openConnection();
	    //��������ʱΪ5s
	    con.setConnectTimeout(5*1000);
	    // ������
	    InputStream is = con.getInputStream();
	
	    // 1K�����ݻ���
	    byte[] bs = new byte[1024];
	    // ��ȡ�������ݳ���
	    int len;
	    // ������ļ���
	   File sf=new File(savePath);
	   if(!sf.exists()){
		   sf.mkdirs();
	   }
	   OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
	    // ��ʼ��ȡ
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // ��ϣ��ر���������
	    os.close();
	    is.close();
	} 
	
	protected void sendJSON(boolean success, Object obj, HttpServletResponse response)
	{
		try
		{
			Map resultmap = new HashMap();
			resultmap = HttpUtil.getResult(success, obj, 0, null);
			
			OutputStream os = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(JSONUtil.map2json(resultmap));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
