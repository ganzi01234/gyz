package service.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Album;

public class PhotoServlet extends CommonServlet
{
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String filename = request.getParameter("filename");
			
			String photoPath = mPhotoRootPath + filename;

			OutputStream os = response.getOutputStream();

			FileInputStream fis = new FileInputStream(photoPath);
			File file = new File(photoPath);
			if(!file.exists()){
				file.mkdirs();
			}
			scaleImage(fis, os, 20);
			fis.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
