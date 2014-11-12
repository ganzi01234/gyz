package com.kaixin.android.mail;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface AbstractMailSender {
	public boolean sendTextMail(MailSenderInfo mailInfo) throws AddressException,
			MessagingException;

	public boolean sendHtmlMail(MailSenderInfo mailInfo) throws AddressException,
			MessagingException;

}
