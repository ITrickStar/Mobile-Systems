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
    [Activity(Label = "Activity3")]
    public class Activity3 : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.activity3);

            var obj = FindViewById<TextView>(Resource.Id.txt);
            var bat = FindViewById<Button>(Resource.Id.btnTrans);
            bat.Click += (s, e) => {
                obj.ScaleX = 0.5f;
                obj.Rotation = 45;
            };

            var button = FindViewById<Button>(Resource.Id.btnNext3);
            button.Click += (s, e) => {
                Intent nextActivity = new Intent(this, typeof(Activity4));
                StartActivity(nextActivity);
            };
        }
    }
}