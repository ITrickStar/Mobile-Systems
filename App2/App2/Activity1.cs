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
    [Activity(Label = "Activity1")]
    public class Activity1 : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.activity1);

            var button = FindViewById<Button>(Resource.Id.btnNext1);
            button.Click += (s, e) => {
                Intent nextActivity = new Intent(this, typeof(Activity2));
                StartActivity(nextActivity);
            };
        }
    }
}