using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace App2
{
    [Activity(Label = "Activity2")]
    public class Activity2 : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.activity2);

            var button = FindViewById<Button>(Resource.Id.btnNext2);
            button.Click += (s, e) => {
                Intent nextActivity = new Intent(this, typeof(Activity3));
                StartActivity(nextActivity);
            };
        }
    }
}