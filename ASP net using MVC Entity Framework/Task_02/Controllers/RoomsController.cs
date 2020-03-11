using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Task_02.Models;
using Microsoft.AspNet.Identity;

namespace Task_02.Controllers
{
    [Authorize(Roles ="Admin")]
    public class RoomsController : Controller
    {
        private Entities db = new Entities();

        // GET: Rooms
        /*
         * Get and display all room from the database
         */
        public ActionResult Index()
        {
            var rooms = db.Rooms.Include(r => r.Hotel);
            return View(rooms.ToList());
        }

        // GET: Rooms/Details/5
       /*
        * Get and displays specific rooms from the database
        */
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Room room = db.Rooms.Find(id);
            if (room == null)
            {
                return HttpNotFound();
            }
            return View(room);
        }

        // GET: Rooms/Create
        /*
         * Allow user to enter and create
         * room details
         */
        public ActionResult Create()
        {
            ViewBag.HotelId = new SelectList(db.Hotels, "Id", "Name");
            return View();
        }

        // POST: Rooms/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        /*
         * Creates room by storing it in database
         */
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Id,RoomNumber,Type,PricePerNight,Rating,Status,HotelId")] Room room, HttpPostedFileBase postedImage)
        {
            ModelState.Clear();
            var myUniqueFileName = string.Format(@"{0}", Guid.NewGuid());
            room.Path = myUniqueFileName;
            TryValidateModel(room);
            if (ModelState.IsValid)
            {
                if(postedImage.ContentLength > 0)
                {
                    string serverPath = Server.MapPath("~/Uploads/");
                    string imageExtension = Path.GetExtension(postedImage.FileName);
                    string imagePath = room.Path + imageExtension;
                    room.Path = imagePath;
                    postedImage.SaveAs(serverPath + room.Path);
                }
                

                db.Rooms.Add(room);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            ViewBag.HotelId = new SelectList(db.Hotels, "Id", "Name", room.HotelId);
            return View(room);
        }

        // GET: Rooms/Edit/5
        /*
         * Get and display specific room details to edit
         */
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Room room = db.Rooms.Find(id);
            if (room == null)
            {
                return HttpNotFound();
            }
            ViewBag.HotelId = new SelectList(db.Hotels, "Id", "Name", room.HotelId);
            return View(room);
        }

        // POST: Rooms/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        /*
         * Edit the room and save edited information in the database
         */
        public ActionResult Edit([Bind(Include = "Id,RoomNumber,Type,PricePerNight,Rating,HotelId,Path,Status")] Room room)
        {
            if (ModelState.IsValid)
            {
                db.Entry(room).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.HotelId = new SelectList(db.Hotels, "Id", "Name", room.HotelId);
            return View(room);
        }

        // GET: Rooms/Delete/5
        /*
         * Gets specific room details to delete
         */
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Room room = db.Rooms.Find(id);
            if (room == null)
            {
                return HttpNotFound();
            }
            return View(room);
        }

        // POST: Rooms/Delete/5
        /*
         * Delete spedcific room details from the database
         */
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Room room = db.Rooms.Find(id);
            db.Rooms.Remove(room);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
