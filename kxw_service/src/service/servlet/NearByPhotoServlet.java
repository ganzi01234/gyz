package service.servlet;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Message;
import service.NearbyPhoto;
import service.NearbyPhotoDetail;
import service.utils.LocationUtil;

public class NearByPhotoServlet extends CommonServlet
{

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		List<NearbyPhoto> nearPhotos = new ArrayList<NearbyPhoto>();
		try
		{
			double latitude = Double.parseDouble(request.getParameter("latitude"));
			double longitude = Double.parseDouble(request.getParameter("longitude"));
			PreparedStatement preparedStatement = null;
			String sql = "select p.*, u.*, RAND() as Random from t_kx_photos p " +
							"left join t_kx_users u on u.id = p.user_id " +
							"where p.latitude > ? -1 and " +
							"p.latitude < ? +1 and " +
							"p.longitude > ? -1 and " +
							"p.longitude < ? +1 and " +
							"u.email <> ? " +
							"order by Random, ACOS(SIN((? * 3.1415) / 180 ) *SIN((p.latitude * 3.1415) / 180 ) +COS((? * 3.1415) / 180 ) * COS((p.latitude * 3.1415) / 180 ) *COS((? * 3.1415) / 180 - (p.longitude * 3.1415) / 180 ) ) * 6380 asc limit 50";
			preparedStatement = mConnection
						.prepareStatement(sql);
			preparedStatement.setDouble(1, latitude);
			preparedStatement.setDouble(2, latitude);
			preparedStatement.setDouble(3, longitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setString(5, mUsername);
			preparedStatement.setDouble(6, latitude);
			preparedStatement.setDouble(7, latitude);
			preparedStatement.setDouble(8, longitude);

			ResultSet rs = preparedStatement.executeQuery();
			String address = "";
			List<NearbyPhotoDetail> photos = null;
			NearbyPhoto nearPhoto = null;
			while (rs.next())
			{
				if(address.equals(rs.getString("location"))){
					nearPhoto = nearPhotos.get(nearPhotos.size() - 1);
					photos = nearPhotos.get(nearPhotos.size() - 1).getImages();
					NearbyPhotoDetail photo = new NearbyPhotoDetail();
					photo.setImage(rs.getString("photo_filename"));
					photo.setOwners_avatar(rs.getString("u.avatar"));
					photo.setLike_count(rs.getInt("like_count"));
					photo.setComment_count(rs.getInt("comment_count"));
					photo.setOwners_name(rs.getString("u.name"));
					photo.setOwners_uid(rs.getString("u.id"));
					photos.add(photo);
					nearPhoto.setImages(photos);
					nearPhoto.setCount(nearPhoto.getCount() + 1);
					nearPhotos.set(nearPhotos.size() - 1, nearPhoto);
				}else{
					nearPhoto = new NearbyPhoto();
					photos = new ArrayList<NearbyPhotoDetail>();
					NearbyPhotoDetail photo = new NearbyPhotoDetail();
					photo.setImage(rs.getString("photo_filename"));
					photo.setOwners_avatar(rs.getString("u.avatar"));
					photo.setLike_count(rs.getInt("like_count"));
					photo.setComment_count(rs.getInt("comment_count"));
					photo.setOwners_name(rs.getString("u.name"));
					photo.setOwners_uid(rs.getString("u.id"));
					photos.add(photo);
					nearPhoto.setPid(rs.getString("p.id"));
					nearPhoto.setImage(rs.getString("photo_filename"));
					nearPhoto.setLocation(rs.getString("p.location"));
					nearPhoto.setImages(photos);
					nearPhoto.setCount(nearPhoto.getCount() + 1);
					double distance = LocationUtil.getDistance(longitude, latitude, rs.getDouble("p.longitude"), rs.getDouble("p.latitude"));
					nearPhoto.setDistance(distance);
					nearPhotos.add(nearPhoto);
					address = rs.getString("location");
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		sendJSON(nearPhotos, response);
//		sendObject(diaries, response);
	}

}