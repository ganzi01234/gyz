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

			//��ô����ļ���Ŀ������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//������ʱ����ļ��Ĵ洢�ң�����洢�ҿ��Ժ����մ洢�ļ����ļ��в�ͬ����Ϊ���ļ��ܴ�Ļ���ռ�ù����ڴ��������ô洢�ҡ�
			factory.setRepository(new File(photoRootPath));
			//���û���Ĵ�С�����ϴ��ļ���������������ʱ���ͷŵ���ʱ�洢�ҡ�
			factory.setSizeThreshold(1024*1024);
			//�ϴ��������ࣨ��ˮƽAPI�ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			String sign = CommonServlet.getRandomFileName();
			//���� parseRequest��request������  ����ϴ��ļ� FileItem �ļ���list ��ʵ�ֶ��ļ��ϴ���
			Iterator items = upload.parseRequest(request).iterator();
			while(items.hasNext()){
				FileItem item = (FileItem) items.next();
				//��ȡ���������֡�
				String name = item.getFieldName();
				//�����ȡ�ı���Ϣ����ͨ���ı���Ϣ����ͨ��ҳ�����ʽ���������ַ�����
				if(item.isFormField()){
					//��ȡ�û�����������ַ�����
					String value = item.getString();
					request.setAttribute(name, value);
				}
				//���������ǷǼ��ַ���������ͼƬ����Ƶ����Ƶ�ȶ������ļ���
				else{ 
					//��ȡ�ϴ��ļ��� �ַ������֡�+1��ȥ����б�ܡ�
					String filename = CommonServlet.getRandomFileName();
					request.setAttribute(name, filename);
					//��ȡ�ļ��ϴ���Ҫ�����·����upload�ļ�������ڡ�
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
					
					System.out.println("�ҽ�����");
					File file = new File(photoPath);
					if(!file.exists()){
						file.mkdirs();
					}
					/*�������ṩ�ķ���ֱ��д���ļ��С�
					 * item.write(new File(path,filename));*/
					//�յ�д�����յ��ļ��С�
//					item.write(new File(photoPath,filename));
					OutputStream out = new FileOutputStream(new File(photoPath,filename));
					InputStream in = item.getInputStream();
					
					int length = 0;
					byte[] buf = new byte[1024];
					System.out.println("��ȡ�ļ�����������:"+ item.getSize());
					
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
