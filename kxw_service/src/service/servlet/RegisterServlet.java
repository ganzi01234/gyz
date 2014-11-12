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
		
				sendJSON(true, "注册成功！", response);
			}else{
				sendJSON(false, "用户名已存在！", response);
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
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    //设置请求超时为5s
	    con.setConnectTimeout(5*1000);
	    // 输入流
	    InputStream is = con.getInputStream();
	
	    // 1K的数据缓冲
	    byte[] bs = new byte[1024];
	    // 读取到的数据长度
	    int len;
	    // 输出的文件流
	   File sf=new File(savePath);
	   if(!sf.exists()){
		   sf.mkdirs();
	   }
	   OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
	    // 开始读取
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // 完毕，关闭所有链接
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
