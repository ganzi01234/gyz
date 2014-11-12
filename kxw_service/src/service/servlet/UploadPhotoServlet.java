package service.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import service.dao.MyOpenconnection;

public class UploadPhotoServlet extends HttpServlet
{

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "";
		try
		{
			ServletContext s1=this.getServletContext(); 
			String photoRootPath=s1.getRealPath("/") + "images/";
//			String photoRootPath = getServletContext().getInitParameter(
//					"photoRootPath");
			connection = MyOpenconnection.getconnection();

			//获得磁盘文件条目工厂。
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
			factory.setRepository(new File(photoRootPath));
			//设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
			factory.setSizeThreshold(1024*1024);
			//上传处理工具类（高水平API上传处理？）
			ServletFileUpload upload = new ServletFileUpload(factory);
			String sign = CommonServlet.getRandomFileName();
			//调用 parseRequest（request）方法  获得上传文件 FileItem 的集合list 可实现多文件上传。
			Iterator items = upload.parseRequest(request).iterator();
			while(items.hasNext()){
				FileItem item = (FileItem) items.next();
				//获取表单属性名字。
				String name = item.getFieldName();
				//如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。
				if(item.isFormField()){
					//获取用户具体输入的字符串，
					String value = item.getString();
					request.setAttribute(name, value);
				}
				//如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
				else{ 
					//截取上传文件的 字符串名字。+1是去掉反斜杠。
					String filename = CommonServlet.getRandomFileName();
					request.setAttribute(name, filename);
					//获取文件上传需要保存的路径，upload文件夹需存在。
					String photoPath = photoRootPath
							+ request.getAttribute("username").hashCode()
							+ File.separator + request.getAttribute("albumId");
					String savedPath = request.getAttribute("username").hashCode()
							+ "/" + request.getAttribute("albumId") + "/" ;
					if("0".equals(request.getAttribute("albumId").toString()) || "-1".equals(request.getAttribute("albumId").toString())){
						photoPath = photoRootPath
								+ request.getAttribute("username").hashCode();
						savedPath = request.getAttribute("username").hashCode() + "/";
					}
					
					System.out.println("我进来了");
					File file = new File(photoPath);
					if(!file.exists()){
						file.mkdirs();
					}
					/*第三方提供的方法直接写到文件中。
					 * item.write(new File(path,filename));*/
					//收到写到接收的文件中。
//					item.write(new File(photoPath,filename));
					OutputStream out = new FileOutputStream(new File(photoPath,filename));
					InputStream in = item.getInputStream();
					
					int length = 0;
					byte[] buf = new byte[1024];
					System.out.println("获取文件总量的容量:"+ item.getSize());
					
					sql = "select * from t_kx_users where email=?";

					preparedStatement = connection
							.prepareStatement(sql);
					preparedStatement.setString(1, request.getAttribute("username").toString());
					ResultSet rs = preparedStatement.executeQuery();
					if (rs.next())
					{
						if("-1".equals(request.getAttribute("albumId").toString())){
							sql = "update t_kx_users set avatar = ? where email = ?";
							preparedStatement = connection.prepareStatement(sql);

							preparedStatement.setString(1, savedPath + filename);
							preparedStatement.setString(2, request.getAttribute("username").toString());
							preparedStatement.execute();
							response.getWriter().println("ok");
						}else{
							sql = "insert into t_kx_photos(user_id,album_id,photo_filename,content_type,time, sign, content, longitude, latitude, location, competence) values(?,?,?,?,?,?,?,?,?,?,?)";
							preparedStatement = connection.prepareStatement(sql);

							preparedStatement.setString(1, rs.getString("id"));
							preparedStatement.setString(2, request.getAttribute("albumId").toString());
							preparedStatement.setString(3, savedPath + filename);
							preparedStatement.setString(4, "image/pjpeg");
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
							Date date = new Date();
							preparedStatement.setString(5, sdf.format(date));
							preparedStatement.setString(6, sign);
							preparedStatement.setString(7, new String(request.getAttribute("content").toString().getBytes(
									"iso-8859-1"), "utf-8"));
							preparedStatement.setDouble(8, Double.parseDouble(request.getAttribute("longitude").toString()));
							preparedStatement.setDouble(9, Double.parseDouble(request.getAttribute("latitude").toString()));
							preparedStatement.setString(10, new String(request.getAttribute("address").toString().getBytes(
									"iso-8859-1"), "utf-8"));
							preparedStatement.setString(11, request.getAttribute("competencePosition").toString());
							preparedStatement.execute();
							response.getWriter().println("ok");
						}
					}
					
					while((length = in.read(buf))!=-1){
						out.write(buf,0,length);
					}
					in.close();
					out.close();
				}
			}
			sql = "update t_kx_users set gold = gold + ? where email = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, 50);
			preparedStatement.setString(2, request.getAttribute("username").toString());
			preparedStatement.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			MyOpenconnection.closeConnection(connection);
			response.getWriter().println(e.getMessage());
		}finally{
			MyOpenconnection.closeConnection(connection);
		}
	}

}
