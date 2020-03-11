using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Task_02.Models;
using Task_02.Utils;
using Microsoft.AspNet.Identity;
using System.Web.Helpers;

namespace Task_02.Controllers
{
    [Authorize]
    public class BookingsController : Controller
    {
        private Entities db = new Entities();

        // GET: Bookings
        /*
         * Display booking based on the role 
         * Public Users, Admin, Manager
         */
        public ActionResult Index()
        {
            if (User.IsInRole("Public"))
            {
                var userId = User.Identity.GetUserId();
                var bookings = db.Bookings.Where(u => u.customer_id == userId).Include(b => b.Hotel);
                return View(bookings.ToList());
            }
            else if (User.IsInRole("Admin"))
            {
                var bookings = db.Bookings.Include(b => b.Hotel);
                return View(bookings.ToList());
            }
            else if (User.IsInRole("Manager"))
            {
                var userId = User.Identity.GetUserId();
                var bookings = db.Bookings.Where(b => b.Hotel.Manager_Id == userId).Include(b => b.Hotel);
                return View(bookings.ToList());
            }

           

            return View();
        }

        /*
         * This method creates chart based on 
         * the average room price of "Deluxe",
         * "Luxury", "Premium","Double"
         */
        [AllowAnonymous]
        public ActionResult CharterHelp()
        {
            ViewBag.Message = "Your application description page.";

            //var findRoom = db.Rooms.Where(r => (r.Id == book_hotel.room.Id)).FirstOrDefault();
            //findRoom.Status = book_hotel.room.Status;
            var rooms = db.Rooms.Select(m => new { m.Type }).Distinct();
            var roomList = db.Rooms.Select(n => new { n.Type, n.PricePerNight }).ToList();

            int addSuperDeluxePrice = 0, countSuperDeluxePrice = 0;
            int addLuxuryPrice = 0, countLuxuryPrice = 0;
            int addDoublePrice = 0, countDoublePrice = 0;
            int eachPremiumPrice = 0, countPremiumPrice = 0;
            var roomType = db.Rooms.Select(m => new { m.Type }).Distinct().ToList();
            foreach (var eachRoomPrice in roomList)
            {
                if(eachRoomPrice.Type == "Super Deluxe")
                {
                    addSuperDeluxePrice += eachRoomPrice.PricePerNight ;
                    countSuperDeluxePrice++;
                }
                else if (eachRoomPrice.Type == "Luxury")
                {
                    addLuxuryPrice += eachRoomPrice.PricePerNight;
                    countLuxuryPrice++;
                }
                else if (eachRoomPrice.Type == "Double")
                {
                    addDoublePrice += eachRoomPrice.PricePerNight;
                    countDoublePrice++;
                }
                else if (eachRoomPrice.Type == "Premium")
                {
                    eachPremiumPrice += eachRoomPrice.PricePerNight;
                    countPremiumPrice++;
                }
            }

            double avgSuperDeluxePrice = addSuperDeluxePrice / countSuperDeluxePrice;
            double avgLuxuryPrice = addLuxuryPrice / countLuxuryPrice;
            double avgDoublePrice = addDoublePrice / countDoublePrice;
            double avgPremiumPrice = eachPremiumPrice / countPremiumPrice;

            var list = new List<KeyValuePair<string, double>>();
            list.Add(new KeyValuePair<string, double>("Double", avgDoublePrice));
            list.Add(new KeyValuePair<string, double>("Super Deluxe", avgSuperDeluxePrice));
            list.Add(new KeyValuePair<string, double>("Luxury", avgLuxuryPrice));
            list.Add(new KeyValuePair<string, double>("Premium", avgPremiumPrice));




            new Chart(width: 500, height: 300)

           .AddTitle("Average Price of various room categories")

                .AddSeries(chartType: "column",

                  xValue: new[] { list[0].Key, list[1].Key, list[2].Key, list[3].Key},

                    yValues: new[] { list[0].Value, list[1].Value, list[2].Value, list[3].Value })

                  .Write("bmp");

            return null;
            //return View();
        }


       
        // GET: Bookings/Details/5
        /*
         * This method gets booking details from the database
         * and displays to the users
         */
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Booking booking = db.Bookings.Find(id);
            if (booking == null)
            {
                return HttpNotFound();
            }
            return View(booking);
        }


        /*
         * This method takes valid check-in and check-out
         * date from the search hotel page and takes user to 
         * next page which displays all available rooms
        */
        [AllowAnonymous]
        public ActionResult SearchHotel()
        {
            //To display drop down list for no_of_days_to_saty, no_of_children, no_of_adults    
            var Values = new List<int> {1, 2, 3, 4, 5, 6, 7};
            var aList = Values.Select((x, i) => new { Value = (x < 8 ? x : 8), Data = x }).ToList();
            ViewBag.List = new SelectList(aList, "Value", "Data");

            var childValues = new List<int> {0, 1, 2};
            var cList = childValues.Select((x, i) => new { childValue = (x < 4 ? x : 4), childData = x }).ToList();
            ViewBag.childList = new SelectList(cList, "childValue", "childData");

            return View();
        }

     

        /*
         * This method displays available rooms along with map
         * based on given check-in and check-out date
         */

        [AllowAnonymous]
        [HttpPost]
        public ActionResult DisplayHotelWithSearchInfo(Booking book)
        {

            Validate validation = new Validate();
            Boolean validDate;
            book.no_of_night_stay = validation.getNoOfNightsToStay(book.from_date, book.to_date);
            validDate = validation.checkFromAndToDate(book.from_date, book.to_date);
            if(validDate == false)
            {
                TempData["DateView"] = "Invalid Check-in and Check-out Date";
                return Redirect("/Bookings/SearchHotel");
            }
            else
            {
                Book_Hotel book_hotel = new Book_Hotel();
                book_hotel.booking = book;
                TempData["DisplaySearchInfo"] = book_hotel;
                return Redirect("/Bookings/DisplayHotel");
            }
   

        }

        /*
         * This method displays available rooms along with map
         * based on given check-in and check-out date
         */

        public ActionResult DisplayHotel()
        {
           // var bookings = db.Bookings.Include(b => b.Hotel);
            //return View(bookings.ToList());

            Validate validation = new Validate();
            List<Room> room = db.Rooms.ToList();
           
            var roomWithHotel = db.Rooms.Include(b => b.Hotel);

            List<Room> specificRoom = new List<Room>();
            Book_Hotel book1 = new Book_Hotel();

            book1 = TempData["DisplaySearchInfo"] as Book_Hotel;
            int no_of_adults = book1.booking.no_of_adults;
            specificRoom = validation.getSpecificRoomDetails(room, no_of_adults);

            TempData["BookHotelRoom"] = book1;
            return View(specificRoom.ToList());
        }


        /*
         * This method display booking details with prices and other 
         * details and request user to confirm the booking
         */
        public ActionResult BookHotel(int? id, int? roomId)
        {

            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
          
            Book_Hotel book = new Book_Hotel();
            book = TempData["BookHotelRoom"] as Book_Hotel;
            book.hotel = db.Hotels.Find(id);
            book.room = db.Rooms.Find(roomId);
            book.room.Status = "Booked";
            //Calculate Price
            int totalPrice = book.room.PricePerNight * book.booking.no_of_night_stay;
            book.booking.price = totalPrice; 
            book.booking.book_status = "Confirmed";
            book.booking.hotel_id = id;

            book.booking.customer_id = User.Identity.GetUserId();
      
            if (book.hotel == null)
            {
                return HttpNotFound();
            }
            TempData["BookAndHotelDetail"] = book;
            return View(book);
        }

        /*
         * This method takes data through Httppost and display booking details 
         * with prices and other 
         * details and request user to confirm the booking
         */
        [HttpPost]
        public ActionResult BookHotel(Book_Hotel book_hotel)
        {
           
            if (ModelState.IsValid)
            {
                book_hotel = TempData["BookAndHotelDetail"] as Book_Hotel;
                Booking current_book = new Booking();
                current_book = book_hotel.booking;

                
                var findRoom = db.Rooms.Where(r => (r.Id == book_hotel.room.Id)).FirstOrDefault();
                findRoom.Status = book_hotel.room.Status;

                //db.Rooms.Find()
                db.Bookings.Add(current_book);
                db.SaveChanges();

                TempData["ConfirmHotel"] = book_hotel;

               
                 return Redirect("/Bookings/EmailConfirmation");
            }
             return View(book_hotel);
            
         }  

        /*
         * This method send booking confirmation mail
         * automatically once user clicked "confirm booking"
         * for any rooms
         */
    public ActionResult EmailConfirmation(Book_Hotel book_hotel)
        {
            try
            {
                book_hotel = TempData["ConfirmHotel"] as Book_Hotel;
                Booking current_book = new Booking();
                current_book = book_hotel.booking;
                Validate validation = new Validate();
               
                var userId = User.Identity.GetUserId();
                String toEmail = validation.getEmailIdFromAspUsers(userId);
                String subject = "Booking Confirmation";
                String contents ="Dear Customer,"+ "<br/>" +
                                    "<p align ='center'>"+ "Check-in: " + current_book.from_date + "<br/>"
                                        + "Check-out: " + current_book.to_date + "<br/>"
                                        + "Number of Nights to Stay: " + current_book.no_of_night_stay + "<br/>"
                                        + "Number of Adults: " + current_book.no_of_adults + "<br/>"
                                        + "Number of Children: " + current_book.no_of_children + "<br/>"
                                        + "Hotel Name: " +book_hotel.hotel.Name+ "<br/>"
                                        + "Hotel Address: " + book_hotel.hotel.Address + "<br/>"
                                        + "Price: :$" + current_book.price + " AUD"+"<br/>"
                                        + "Booking Status: Confirmed" + "<p>"+ "<br/>" + "<br/>" + "<br/>"
                                        + "Thanks," + "<br/>"
                                        + "Trivago Team";


                EmailSender es = new EmailSender();
                es.Send(toEmail, subject, contents);
                ViewBag.Result = "Email has been send to your registered email id";
                ModelState.Clear();
                return View();
            }
            catch
            {
                return View();
            }

        }


    // GET: Bookings/Create
    /*
     * Get bookings from the database to delete
     */
    public ActionResult Create()
        {
            ViewBag.hotel_id = new SelectList(db.Hotels, "Id", "Name");
            return View();
        }

    // POST: Bookings/Create
    // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
    //// more details see https://go.microsoft.com/fwlink/?LinkId=317598.

    [HttpPost]
    [ValidateAntiForgeryToken]
        // GET: Bookings/Edit/5
        /*
         * Create bookings and store it in the database
         */
        public ActionResult Create([Bind(Include = "booking_id,from_date,to_date,no_of_night_stay,no_of_adults,no_of_children,price,book_status,hotel_id,customer_id")] Booking booking)
    {
        if (ModelState.IsValid)
        {
            db.Bookings.Add(booking);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        ViewBag.hotel_id = new SelectList(db.Hotels, "Id", "Name", booking.hotel_id);
        return View(booking);
    }

        // GET: Bookings/Edit/5
        /*
         * Get specific bookings from the database to edit
         */
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Booking booking = db.Bookings.Find(id);
            if (booking == null)
            {
                return HttpNotFound();
            }
            ViewBag.hotel_id = new SelectList(db.Hotels, "Id", "Name", booking.hotel_id);
            return View(booking);
        }

        // POST: Bookings/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        //Edit speicific booking from the database
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "booking_id,from_date,to_date,no_of_night_stay,no_of_adults,no_of_children,price,book_status,hotel_id,customer_id")] Booking booking)
        {
            if (ModelState.IsValid)
            {
                db.Entry(booking).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.hotel_id = new SelectList(db.Hotels, "Id", "Name", booking.hotel_id);
            return View(booking);
        }

        // GET: Bookings/Delete/5
        /*Get specific bookings from the database
         * to delete
         */
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Booking booking = db.Bookings.Find(id);
            if (booking == null)
            {
                return HttpNotFound();
            }
            return View(booking);
        }

        /*Delete specific bookings from the database
         */
        // POST: Bookings/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Booking booking = db.Bookings.Find(id);
            db.Bookings.Remove(booking);
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
