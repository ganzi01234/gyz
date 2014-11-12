package service.servlet;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.dao.MyOpenconnection;
import service.utils.HttpUtil;
import service.utils.JSONUtil;
import service.utils.StringUtil;

public abstract class CommonServlet extends HttpServlet
{
	protected Connection mConnection;
	protected String mUsername;
	protected String mNickname;
	protected String mPassword;
	protected String mPhotoRootPath;
	protected String mDiaryRootPath;
	protected abstract void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			mPhotoRootPath = getServletContext().getRealPath("/") + "images/";
//			mPhotoRootPath = getServletContext().getInitParameter("photoRootPath");
			mDiaryRootPath = getServletContext().getInitParameter("diaryRootPath");
			
			// 获得Connection对象
			mConnection = MyOpenconnection.getconnection();
			String sql = "select * from t_kx_users where email=? and password=?";

			PreparedStatement pstmt = mConnection.prepareStatement(sql);
			mUsername = request.getParameter("username");
			mPassword = request.getParameter("password");
			if(!StringUtil.isNull(request.getParameter("nickname"))){
				mNickname = new String(request.getParameter("nickname").getBytes(
						"iso-8859-1"), "utf-8");
			}
			pstmt.setString(1, mUsername);
			pstmt.setString(2, mPassword);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				execute(request, response);
			}
			else
			{
				response.getWriter().println("Unauthorized Access.");
			}
		}
		catch (Exception e)
		{
			response.getWriter().println(e.getMessage());
			MyOpenconnection.closeConnection(mConnection);
		}finally{
			MyOpenconnection.closeConnection(mConnection);
		}
	}

	public void scaleImage(InputStream imgInputStream,
			OutputStream imgOutputStream, int scale)
	{
		try
		{

			Image src = javax.imageio.ImageIO.read(imgInputStream);
			int width = (int) (src.getWidth(null) * scale / 100.0);
			int height = (int) (src.getHeight(null) * scale / 100.0);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			bufferedImage.getGraphics().drawImage(
					src.getScaledInstance(width, height, Image.SCALE_SMOOTH),
					0, 0, null);
//			JPEGImageEncoder encoder = JPEGCodec
//					.createJPEGEncoder(imgOutputStream);
//			encoder.encode(bufferedImage);

		}
		catch (IOException e)
		{

		}
	}

	public void scaleImage(String imgSrc, OutputStream imgOutputStream,
			int scale)
	{
		try
		{
			File file = new File(imgSrc);
			if (!file.exists())
			{
				return;
			}
			InputStream is = new FileInputStream(file);
			scaleImage(is, imgOutputStream, scale);
			is.close();

		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	protected void sendObject(Object obj, HttpServletResponse response)
	{
		try
		{
			OutputStream os = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(obj);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void sendJSON(Object obj, HttpServletResponse response)
	{
		try
		{
			Map resultmap = new HashMap();
			if(obj instanceof List){
				resultmap = HttpUtil.getResult(true, obj, ((List) obj).size(), null);
			}else{
				resultmap = HttpUtil.getResult(true, obj, 0, null);
			}
			
			OutputStream os = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(JSONUtil.map2json(resultmap));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void sendJSON(boolean success, Object obj, HttpServletResponse response)
	{
		try
		{
			Map resultmap = new HashMap();
			if(obj instanceof List){
				resultmap = HttpUtil.getResult(success, obj, ((List) obj).size(), null);
			}else{
				resultmap = HttpUtil.getResult(success, obj, 0, null);
			}
			
			OutputStream os = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(JSONUtil.map2json(resultmap));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String getRandomFileName()
	{
		String s = "";
		Random random = new Random();
		for (int i = 0; i < 10; i++)
		{
			int n = random.nextInt(36);
			if (n >= 0 && n <= 9)
			{
				s += String.valueOf(n);
			}
			else
			{
				n = n - 10;
				s += (char) (97 + n);
			}
		}

		return s;
	}
}
