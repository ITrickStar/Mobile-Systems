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
    [Activity(Label = "Activity4")]
    public class Activity4 : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.activity4);

            var obj = FindViewById<TextView>(Resource.Id.TV);
            var bat = FindViewById<Button>(Resource.Id.btnAction);
            bat.Click += (s, e) => {
                obj.Animate().TranslationY(700).SetDuration(1000).Start();
                obj.Animate().ScaleX(1.5f).SetDuration(1000).Start();
                obj.Animate().ScaleY(1.5f).SetDuration(1000).Start();
            };

            var button = FindViewById<Button>(Resource.Id.btnNext4);
            button.Click += (s, e) => {
                Intent nextActivity = new Intent(this, typeof(Activity5));
                StartActivity(nextActivity);
            };
        }
    }
}