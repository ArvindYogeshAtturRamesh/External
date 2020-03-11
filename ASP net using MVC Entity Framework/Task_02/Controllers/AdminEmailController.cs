using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.Mvc;
using Task_02.Models;
using Task_02.Utils;

namespace Task_02.Controllers
{
    [Authorize]
    public class AdminEmailController : Controller
    {
        private Entities db = new Entities();
        // GET: AdminEmail
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult Admin_Send_Email()
        {
            return View(new SendEmailModel());
        }

        [HttpPost]
        public ActionResult Admin_Send_Email(SendEmailModel model)
        {
             //  model.ToEmail = "abc@gmail.com";
           // if (ModelState.IsValid)
            //{
                try
                {
                    String toEmail = model.ToEmail;
                    String subject = model.Subject;
                    String message = model.Message; 
                     string totalPath = null;
                string filePath = null;

                //StringBuilder sbMessage = new StringBuilder();
                //sbMessage.Append(HttpUtility.HtmlEncode(message));
                //sbMessage.Replace("&lt;b&gt;","<b>");
                //sbMessage.Replace("&lt;u&gt;", "<u>");
                //sbMessage.Replace("&lt;/b&gt;", "</b>");
                //sbMessage.Replace("&lt;/u&gt;", "</u>");
                //sbMessage.Replace("&lt;i&gt;", "<i>");
                //sbMessage.Replace("&lt;/i&gt;", "</i>");
                //message = sbMessage.ToString();

                



                foreach (string fileName in Request.Files)
                {
                    HttpPostedFileBase uploadFile = Request.Files[fileName];
                    if (uploadFile != null && uploadFile.ContentLength > 0)
                    {
                        var myUniqueFileName = string.Format(@"{0}", Guid.NewGuid());
                        string imagePath = myUniqueFileName;

                        string serverPath = Server.MapPath("~/Uploads/");
                        string fileExtention = Path.GetExtension(uploadFile.FileName);
                        filePath = imagePath + fileExtention;
                        imagePath = filePath;

                        uploadFile.SaveAs(serverPath + imagePath);

                        totalPath = serverPath + "\\" + imagePath;

                    }
                }

                   EmailSender sender = new EmailSender();
                   int count = sender.Send(toEmail, subject, message, filePath, totalPath);
                   ViewBag.Result = "Email has been send to "+ count+ " reciepients";

                    ModelState.Clear();

                    return View(new SendEmailModel());
                }
                catch 
                {
                    return View();
                }
           // }
           // return View();
        }

        public ActionResult Manager_Send_Email()
        {
            return View(new SendEmailModel());
        }

        [HttpPost]
        public ActionResult Manager_Send_Email(SendEmailModel model)
        {
            
            if (ModelState.IsValid)
            {
            try
            {
                String toEmail = model.ToEmail;
                String subject = model.Subject;
                String message = model.Message;
                string totalPath = null;
                string filePath = null;





                foreach (string fileName in Request.Files)
                {
                    HttpPostedFileBase uploadFile = Request.Files[fileName];
                    if (uploadFile != null && uploadFile.ContentLength > 0)
                    {
                        var myUniqueFileName = string.Format(@"{0}", Guid.NewGuid());
                        string imagePath = myUniqueFileName;

                        string serverPath = Server.MapPath("~/Uploads/");
                        string fileExtention = Path.GetExtension(uploadFile.FileName);
                        filePath = imagePath + fileExtention;
                        imagePath = filePath;

                        uploadFile.SaveAs(serverPath + imagePath);

                        totalPath = serverPath + "\\" + imagePath;

                    }
                }

                EmailSender sender = new EmailSender();
                int count = sender.Send(toEmail, subject, message, filePath, totalPath);



                ViewBag.Result = "Email has been send to " + count + " reciepients";

                ModelState.Clear();

                return View(new SendEmailModel());
            }
            catch
            {
                return View();
            }
           }
            return View();
        }

    }
}