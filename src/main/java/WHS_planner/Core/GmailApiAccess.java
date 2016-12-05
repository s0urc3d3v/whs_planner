package WHS_planner.Core;


import WHS_planner.Util.Student;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by matthewelbing on 06.10.16.
 */
public class GmailApiAccess {
    private static final String APPLICATION_NAME = "whs_planner";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/whs_planner");
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;

    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_COMPOSE, GmailScopes.GMAIL_SEND, GmailScopes.GMAIL_READONLY);
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t){
            t.printStackTrace();
        }
    }
    private static Credential authorize() throws Exception {
        File client_id = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "/client_id.json");
        InputStream in = new FileInputStream(client_id);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Creditials saved to: " + DATA_STORE_DIR.getAbsolutePath());
        return credential;

    }
    private static Gmail getGmailService() throws Exception {
        Credential credential = authorize();
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void sendEmail(String to, String from, String subject, String bodyText) throws Exception {
        Gmail service = getGmailService();
        String user = "whs_planner";
        sendMessage(getGmailService(), "me", createEmail(to, from, subject, bodyText));


    }
    private static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);

        MimeBodyPart partOne = new MimeBodyPart(); //This is for text
        partOne.setText("Meeting Scheduled!");

        MimeBodyPart partTwo = new MimeBodyPart();
        String filename = "meeting.json.whsplannermeeting";
        DataSource dataSource = new FileDataSource("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + filename);
        partTwo.setDataHandler(new DataHandler(dataSource));
        partTwo.setFileName(filename);

        Multipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(partOne);
        mimeMultipart.addBodyPart(partTwo);

        email.setContent(mimeMultipart);

        return email;
    }
    private static Message createMessageFromMimeMessage(MimeMessage mimeMessage) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        mimeMessage.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
    private static Message sendMessage(Gmail service, String userId, MimeMessage emailContent) throws MessagingException, IOException {
        Message message = createMessageFromMimeMessage(emailContent);
        message = service.users().messages().send(userId, message).execute();
        System.out.println("Message ID: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }

    public void sendMeetingData(Student requestingStudent, Student studentRequested, Meeting meeting){

    }


}
