using Android.App;
using Android.OS;
using Android.Widget;
using System.Text;

namespace App3
{
    [Activity(Label = "Activity1")]
    public class Activity1 : Activity
    {
        DatePicker datePicker;
        TimePicker timePicker;
        protected override void OnCreate(Bundle savedInstanceState)
        {

            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.date);
            datePicker = FindViewById<DatePicker>(Resource.Id.datePicker);
            timePicker = FindViewById<TimePicker>(Resource.Id.timePicker);
            var btnChange = FindViewById<Button>(Resource.Id.change_date_button);
            var txtDate = FindViewById<TextView>(Resource.Id.txtViewDate);
            var txtTime = FindViewById<TextView>(Resource.Id.txtViewDate);

            txtDate.Text = (getDate());
            btnChange.Click += (s, e) =>
            {
                txtDate.Text = getDate();
            };
        }
        private string getDate()
        {
            StringBuilder strCurrentDate = new StringBuilder();
            strCurrentDate.Append("Time : " + timePicker.Hour + ":" + timePicker.Minute + " ");
            strCurrentDate.Append("Date : " + datePicker.DayOfMonth + "/" + (datePicker.Month+1) + "/" + datePicker.Year);
            return strCurrentDate.ToString();
        }

    }
}