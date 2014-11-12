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

			//��ô����ļ���Ŀ������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//������ʱ����ļ��Ĵ洢�ң�����洢�ҿ��Ժ����մ洢�ļ����ļ��в�ͬ����Ϊ���ļ��ܴ�Ļ���ռ�ù����ڴ��������ô洢�ҡ�
			//������ʱ�ļ��洢λ��         
			String base = voiceRootPath + "tempBase";        
			File tempBase = new File(base);  
			if(!tempBase.exists())            
				tempBase.mkdirs();   
			factory.setRepository(tempBase); 
			//���û���Ĵ�С�����ϴ��ļ���������������ʱ���ͷŵ���ʱ�洢�ҡ�
			factory.setSizeThreshold(1024*1024);
			//�ϴ��������ࣨ��ˮƽAPI�ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ���õ����ļ�������ϴ�ֵ        
			upload.setFileSizeMax(10002400000l);      
			// ��������request�����ֵ       
			upload.setSizeMax(10002400000l);    
			upload.setHeaderEncoding("UTF-8"); 
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
					String voicePath = voiceRootPath
							+ request.getAttribute("username").hashCode();
					String savedPath = request.getAttribute("username").hashCode() + "/" ;
					File file = new File(voicePath);
					if(!file.exists()){
						file.mkdirs();
					}
					/*�������ṩ�ķ���ֱ��д���ļ��С�
					 * item.write(new File(path,filename));*/
					//�յ�д�����յ��ļ��С�
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
					preparedStatement.setString(8, StringUtil.isNull(request.getAttribute("call").toString()) ? "δ֪" : request.getAttribute("call").toString());
					preparedStatement.setString(9, StringUtil.isNull(request.getAttribute("called").toString()) ? "δ֪" : request.getAttribute("called").toString());
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
