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
import service.utils.StringUtil;

public class UploadVoiceServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8711882267599349679L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		Connection connection = null;
		try
		{
			ServletContext s1=this.getServletContext(); 
			String voiceRootPath=s1.getRealPath("/") + "voice/";
//			String photoRootPath = getServletContext().getInitParameter(
//					"photoRootPath");
			connection = MyOpenconnection.getconnection();

			//获得磁盘文件条目工厂。
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。
			//设置临时文件存储位置         
			String base = voiceRootPath + "tempBase";        
			File tempBase = new File(base);  
			if(!tempBase.exists())            
				tempBase.mkdirs();   
			factory.setRepository(tempBase); 
			//设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
			factory.setSizeThreshold(1024*1024);
			//上传处理工具类（高水平API上传处理？）
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置单个文件的最大上传值        
			upload.setFileSizeMax(10002400000l);      
			// 设置整个request的最大值       
			upload.setSizeMax(10002400000l);    
			upload.setHeaderEncoding("UTF-8"); 
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
					String voicePath = voiceRootPath
							+ request.getAttribute("username").hashCode();
					String savedPath = request.getAttribute("username").hashCode() + "/" ;
					File file = new File(voicePath);
					if(!file.exists()){
						file.mkdirs();
					}
					/*第三方提供的方法直接写到文件中。
					 * item.write(new File(path,filename));*/
					//收到写到接收的文件中。
//					item.write(new File(photoPath,filename));
					OutputStream out = new FileOutputStream(new File(voicePath,filename));
					InputStream in = item.getInputStream();
					
					int length = 0;
					byte[] buf = new byte[1024];
					
					String sql = "insert into t_kx_voices(email, filename, content_type, time, longitude, latitude, location, calling_number, called_number) values(?,?,?,?,?,?,?,?,?)";
					if(connection == null){
						connection = MyOpenconnection.getconnection();
					}
					PreparedStatement preparedStatement = connection.prepareStatement(sql);

					preparedStatement.setString(1, request.getAttribute("username").toString());
					preparedStatement.setString(2, savedPath + filename);
					preparedStatement.setString(3, "amr/3gp");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date date = new Date();
					preparedStatement.setString(4, StringUtil.isNull(request.getAttribute("time").toString()) ? sdf.format(date) : request.getAttribute("time").toString());
					if(!StringUtil.isNull(request.getAttribute("longitude").toString())){
						preparedStatement.setDouble(5, Double.parseDouble(request.getAttribute("longitude").toString()));
						preparedStatement.setDouble(6, Double.parseDouble(request.getAttribute("latitude").toString()));
					}else{
						preparedStatement.setDouble(5, 0);
						preparedStatement.setDouble(6, 0);
					}
					preparedStatement.setString(7, new String(request.getAttribute("address").toString().getBytes(
							"iso-8859-1"), "utf-8"));
					preparedStatement.setString(8, StringUtil.isNull(request.getAttribute("call").toString()) ? "未知" : request.getAttribute("call").toString());
					preparedStatement.setString(9, StringUtil.isNull(request.getAttribute("called").toString()) ? "未知" : request.getAttribute("called").toString());
					preparedStatement.execute();
					response.getWriter().println("ok");
					
					while((length = in.read(buf))!=-1){
						out.write(buf,0,length);
					}
					in.close();
					out.close();
				}
			}
			
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
