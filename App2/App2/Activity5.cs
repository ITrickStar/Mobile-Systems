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
    [Activity(Label = "Activity5")]
    public class Activity5 : Activity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.activity5);

            var Num1 = FindViewById<EditText>(Resource.Id.editText1);
            var Num2 = FindViewById<EditText>(Resource.Id.editText2);
            var button = FindViewById<Button>(Resource.Id.btnSum);

            button.Click += (s, e) =>
            {
                int sum = int.Parse(Num1.Text) + int.Parse(Num2.Text);
                button.Text = sum.ToString();
            };
    }
    }
}