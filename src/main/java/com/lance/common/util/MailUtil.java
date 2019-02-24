package com.lance.common.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Component
public class MailUtil
{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String deliver;

    public void sendSimpleEmail(String[] receiver, String[] carbonCopy, String subject, String content) throws ServiceException
    {

        long startTimestamp = System.currentTimeMillis();
        log.info("Start send mail ... ");

        try
        {
            MimeMessage message = mailSender.createMimeMessage();
            message.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, false);

            mailSender.send(message);
            log.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        }
        catch (MailException e)
        {
            log.error("Send mail failed, error message is : {} ", e.getMessage());
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 发送html邮件
     *
     * @param receiver
     * @param carbonCopy
     * @param subject
     * @param content
     * @param isHtml
     * @throws ServiceException
     */
    public void sendHtmlEmail(String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml) throws ServiceException
    {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send email ...");

        try
        {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);

            mailSender.send(message);
            log.info("Send email success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        }
        catch (MessagingException e)
        {
            log.error("Send email failed, error message is {} \n", e.getMessage());
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param receiver
     * @param carbonCopy
     * @param subject
     * @param content
     * @param isHtml
     * @param fileName
     * @param file
     * @throws ServiceException
     */
    public void sendAttachmentFileEmail(String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml, String fileName, File file) throws ServiceException
    {
        long startTimestamp = System.currentTimeMillis();
        log.info("Start send mail ...");
        try
        {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            messageHelper.setCc(carbonCopy);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
            messageHelper.addAttachment(fileName, file);

            mailSender.send(message);
            log.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        }
        catch (MessagingException e)
        {
            log.error("Send mail failed, error message is {}\n", e.getMessage());
            e.printStackTrace();
            throw new ServiceException(e.getMessage());
        }
    }
}