using SendGrid;
using SendGrid.Helpers.Mail;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Task_02.Models;

using System.Web;

namespace Task_02.Utils
{
    public class EmailSender
    {
        // Please use your API KEY here.
        private const String API_KEY = "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";

        /*
         * Get one mail id and send mail to specific user via send grid
         */
        public void Send(String toEmail, String subject, String contents)
        {
            var client = new SendGridClient(API_KEY);
            var fromMessage = new EmailAddress("arvindkandaswamy@gmail.com", "Hotel Booking Confirmation");
            var toMessage = new EmailAddress(toEmail, "");
            var plainContent = contents;
            var htmlContent = "<p>" + contents + "</p>";
            var message = MailHelper.CreateSingleEmail(fromMessage, toMessage, subject, plainContent, htmlContent);
            var response = client.SendEmailAsync(message);
          
        }

        //Gets email-id from the database and send mulitple emails at a time using Send Grid
        public int Send( String toEmail, String subject, String contents, string fileName, string attachment)
        {
            

            List<EmailAddress> multipleEmails = new List<EmailAddress>();
            int countEmail = 0;

            //To send emails to particular users
            if (toEmail != null)
            {
                List<string> emails = toEmail.Split(';').ToList();
                foreach (string email in emails)
                {
                    countEmail++;
                    multipleEmails.Add(new EmailAddress(email, "User " + countEmail));
                }

            }
            //To send emails to all registered users
            else
            {
                var context = new ApplicationDbContext();
                var users = context.Users.ToList();
                //List<string> toEmailsFromDb = new List<string>();
                foreach (var user in users)
                {
                    //toEmailsFromDb.Add(user.Email);
                    countEmail++;
                    multipleEmails.Add(new EmailAddress(user.Email, "User " + countEmail));
                }
            }

            var client = new SendGridClient(API_KEY);
            var fromMessage = new EmailAddress("arvindkandaswamy@gmail.com", "Hotel Booking Confirmation");
           // var toMessage = new EmailAddress(toEmail, "");
            var actualSubject = subject;
            var body = contents;
            var htmlContent = "<p>" + contents + "</p>";

            var message = MailHelper.CreateSingleEmailToMultipleRecipients(fromMessage, multipleEmails, actualSubject, body, htmlContent, false);

           // var message = MailHelper.CreateSingleEmail(fromMessage, toMessage, actualSubject, body, htmlContent);

            

            if (attachment != null)
            {
                //var bytes = File.ReadAllBytes("C:\\Users\\Aravind\\Desktop\\Daily Study.txt");
                var bytes = File.ReadAllBytes(attachment);
                var convertToFile = Convert.ToBase64String(bytes);
                message.AddAttachment(fileName, convertToFile);
            }
            

            var response =  client.SendEmailAsync(message);

            return countEmail;
        }
    }
}