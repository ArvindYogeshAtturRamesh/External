using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Task_02.Models;

namespace Task_02.Controllers
{
    [Authorize(Roles ="Admin")]
    public class HotelsController : Controller
    {
        private Entities db = new Entities();

        // GET: Hotels
        /*
         * Get and display all hotels from the database
         */
        public ActionResult Index()
        {
            var hotels = db.Hotels.Include(h => h.AspNetUser);
            return View(hotels.ToList());
        }

        // GET: Hotels/Details/5
        /*
         * Get and display specific hotel detail
         */
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Hotel hotel = db.Hotels.Find(id);
            if (hotel == null)
            {
                return HttpNotFound();
            }
            return View(hotel);
        }

        // GET: Hotels/Create
        /*
         * Enter hotel details to create it in the database
         */
        public ActionResult Create()
        {
            ViewBag.Manager_Id = new SelectList(db.AspNetUsers, "Id", "Email");
            return View();
        }

        // POST: Hotels/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        /*
         * Creates hotel details and store it in the databse
         */
        public ActionResult Create([Bind(Include = "Name,Address,Latitude,Longitude,Phone_Number,Manager_Id")] Hotel hotel)
        {
            if (ModelState.IsValid)
            {
                db.Hotels.Add(hotel);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            ViewBag.Manager_Id = new SelectList(db.AspNetUsers, "Id", "Email", hotel.Manager_Id);
            return View(hotel);
        }

        // GET: Hotels/Edit/5
        /*
         * Get specifi hotel details to edit
         */
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Hotel hotel = db.Hotels.Find(id);
            if (hotel == null)
            {
                return HttpNotFound();
            }
            ViewBag.Manager_Id = new SelectList(db.AspNetUsers, "Id", "Email", hotel.Manager_Id);
            return View(hotel);
        }

        // POST: Hotels/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        // GET: Bookings/Edit/5
        /*
         * Edit specific hotel details and save it in database
         */
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Id,Name,Address,Latitude,Longitude,Phone_Number,Manager_Id")] Hotel hotel)
        {
            if (ModelState.IsValid)
            {
                db.Entry(hotel).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.Manager_Id = new SelectList(db.AspNetUsers, "Id", "Email", hotel.Manager_Id);
            return View(hotel);
        }

        // GET: Hotels/Delete/5
        /*
         * Get specifi hotel details to delete
         */
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Hotel hotel = db.Hotels.Find(id);
            if (hotel == null)
            {
                return HttpNotFound();
            }
            return View(hotel);
        }

        // POST: Hotels/Delete/5
        /*
         * Delete specific hotel details fron the database
         */
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Hotel hotel = db.Hotels.Find(id);
            //Room room = db.Rooms.Find();
            //var bookings = db.Bookings.Where(b => b.Hotel.Manager_Id == userId).Include(b => b.Hotel);
            // var rooms = db.Rooms.Where(b => b.HotelId == id).Include(b => b.Hotel);
            var rooms = db.Rooms.Where(b => b.HotelId == id).ToList();


            if (rooms.Count == 0)
            {
                db.Hotels.Remove(hotel);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            else
            {
                TempData["message"] = "Hotel Cannot be deleted as it has few associated rooms";
                return RedirectToAction("Index");
            }
           // return View();

           
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
