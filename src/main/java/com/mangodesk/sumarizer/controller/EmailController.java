// package com.mangodesk.sumarizer.controller;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api")
// public class EmailController {

//     @Autowired
//     private JavaMailSender mailSender;

//     // DTO class inside the controller
//     public static class EmailRequest {
//         private String recipient;
//         private String subject;
//         private String summary;

//         public String getRecipient() { return recipient; }
//         public void setRecipient(String recipient) { this.recipient = recipient; }

//         public String getSubject() { return subject; }
//         public void setSubject(String subject) { this.subject = subject; }

//         public String getSummary() { return summary; }
//         public void setSummary(String summary) { this.summary = summary; }
//     }

//     @PostMapping("/send-email")
//     public Map<String, String> sendEmail(@RequestBody EmailRequest emailRequest) {
//         Map<String, String> response = new HashMap<>();

//         try {
//             SimpleMailMessage message = new SimpleMailMessage();
//             message.setFrom("noreply@example.com"); // can be anything when using MailHog
//             message.setTo(emailRequest.getRecipient());
//             message.setSubject(emailRequest.getSubject());
//             message.setText(emailRequest.getSummary());

//             mailSender.send(message);

//             response.put("message", "Email sent successfully!");
//         } catch (Exception e) {
//             response.put("message", "Error sending email: " + e.getMessage());
//         }

//         return response; // ✅ always JSON
//     }
// }
