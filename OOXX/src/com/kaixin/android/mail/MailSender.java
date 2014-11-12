package com.kaixin.android.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ����ʵ����sendHtmlMail sendTextMail ,ֻ�Ƿֱ�text ,html ���ֵ���Ϣװ��Message �����У�����from ,
 * to , port �ȣ�δ���в��� ���Ǵ��ಢ����ֱ��ʹ�ã� �����Ѷ�����һ��populateCommonInfo����
 * ��Ҫ�������ʵ�֣���MailSenderInfo ��Ϣ��װ��Message ������ ��Ϊ��Щ���� ��Ҫ�õ�SSL
 * ,����Щ���ã����������ǵ�����ֱ���populateCommonInfo�н�����Ϣ����װ ����������ssl
 * ���ǲ��ã���Ҫ�ͻ������ж�,Ȼ��ʹ����Ӧ������ ��֪����������MailSender SSLMailSender
 * 
 * @author Administrator
 * 
 */
public class MailSender implements AbstractMailSender {

	Properties props = null;

	protected Message populateCommonInfo(MailSenderInfo mailInfo)
			throws MessagingException {
		props = new Properties();
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		if (mailInfo.getMailServerHost().equals("smtp.gmail.com")
				|| mailInfo.getMailServerHost().equals("smtp.qq.com")) {
			props.setProperty("mail.smtp.host", mailInfo.getMailServerHost());
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.port", "465");
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.auth", "true");
		} else if (mailInfo.getMailServerHost().equals("smtp.163.com")) {
			props.setProperty("mail.smtp.host", mailInfo.getMailServerHost());
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.stmp.port", "25");
		} else {
			props.put("mail.smtp.host", mailInfo.getMailServerHost());
			props.put("mail.smtp.auth", "true");
			props.put("mail.stmp.port", "25");
		}
		Session session = Session.getInstance(props, new MyAuthenticator(
				mailInfo.getUserName(), mailInfo.getPassword()));
		System.out.println(mailInfo.getUserName() + mailInfo.getPassword()
				+ mailInfo.getMailServerHost());

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(mailInfo.getFromAddress()));

		msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mailInfo.getToAddress(), false));
		msg.setSubject(mailInfo.getSubject());
		msg.setSentDate(new Date());
		return msg;
	}

	public boolean sendHtmlMail(MailSenderInfo mailInfo)
			throws AddressException, MessagingException {
		try {
			Message msg = populateCommonInfo(mailInfo);

			Multipart mainPart = new MimeMultipart();
			// 创建�?��包含HTML内容的MimeBodyPart

			// 设置信件的附�?
			for (File f : mailInfo.getAttachFileNames()) {
				BodyPart filePart = new MimeBodyPart();
				DataHandler dh = new DataHandler(new FileDataSource(f));
				filePart.setFileName(f.getName());//
				filePart.setDataHandler(dh);

				mainPart.addBodyPart(filePart);
			}

			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内�?
			msg.setContent(mainPart);

			Transport.send(msg);

			for (File f : mailInfo.getAttachFileNames()) {
				System.out.println("delete");
				f.delete();
			}

			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean sendTextMail(MailSenderInfo mailInfo)
			throws AddressException, MessagingException {
		try {
			Message msg = populateCommonInfo(mailInfo);
			msg.setText(mailInfo.getContent());
			Transport.send(msg);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
