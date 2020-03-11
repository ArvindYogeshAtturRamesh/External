using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Task_02.Models;

namespace Task_02.Utils
{
    public class Validate
    {

        
     //Get all registered email-id from the database
        public string getEmailIdFromAspUsers(string userId)
        {
            String toEmail = null;
            var context = new ApplicationDbContext();
            
            var users = context.Users.ToList();
          
            foreach (var user in users)
            {
                if (user.Id == userId)
                {
                    toEmail = user.Email;
                }
            }
            return toEmail;
        }

        //Get all specific room details from the database based on given "Number of adults"
        public List<Room> getSpecificRoomDetails(List<Room> room, int no_of_adults)
        {
            
            List<Room> specificRoom = new List<Room>();

            if(no_of_adults > 0 && no_of_adults <= 2)
            {
                foreach (Room eachRoom in room)
                {
                    if (eachRoom.Type == "Double")
                    {
                        specificRoom.Add(eachRoom);
                    }
                }
             }
            else if(no_of_adults > 2 && no_of_adults <= 5)
            {
                foreach (Room eachRoom in room)
                {
                    if (eachRoom.Type == "Super Deluxe" || eachRoom.Type == "Luxury")
                    {
                        specificRoom.Add(eachRoom);
                    }
                }
            }
            else if (no_of_adults > 5 && no_of_adults <= 7)
            {
                foreach (Room eachRoom in room)
                {
                    if (eachRoom.Type == "Premium")
                    {
                        specificRoom.Add(eachRoom);
                    }
                }
            }

            return specificRoom;
        }

        /*
         * Calculate total number of noght stay 
         * based on check-in and check-out date
         */
        public int getNoOfNightsToStay(string fromDate, string toDate)
        {
            int no_of_night_stay = 0;

            DateTime actualFromDate = DateTime.ParseExact(fromDate, "MM/dd/yyyy", System.Globalization.CultureInfo.InvariantCulture);
            DateTime actualToDate = DateTime.ParseExact(toDate, "MM/dd/yyyy", System.Globalization.CultureInfo.InvariantCulture);

            if (actualToDate > actualFromDate)
            {
                TimeSpan difference = actualToDate - actualFromDate;
                int days = (int)difference.TotalDays;
                no_of_night_stay = days;
            }
            else if (toDate == fromDate)
            {
                no_of_night_stay = 1;
            }
            return no_of_night_stay;

        }

        /*
         * validate check-in and check-out date
         * based on the current date
         */
        public Boolean checkFromAndToDate(string fromDate, string toDate)
        {
            Boolean validDate = false;

            DateTime actualFromDate = DateTime.ParseExact(fromDate, "MM/dd/yyyy", System.Globalization.CultureInfo.InvariantCulture);
            DateTime actualToDate = DateTime.ParseExact(toDate, "MM/dd/yyyy", System.Globalization.CultureInfo.InvariantCulture);

            if(actualFromDate >= DateTime.Now && actualToDate >= DateTime.Now)
            {
                if (actualFromDate < actualToDate || actualFromDate == actualToDate)
                {
                    validDate = true;
                }
            }

            return validDate;

        }
    }
}