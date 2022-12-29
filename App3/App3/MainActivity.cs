using Android.App;
using Android.OS;
using Android.Runtime;
using AndroidX.AppCompat.App;
using Android.Widget;
using Android.Content;
using System;

namespace App3
{
    [Activity(Label = "@string/app_name", Theme = "@style/AppTheme", MainLauncher = true)]
    public class MainActivity : AppCompatActivity
    {
        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            Xamarin.Essentials.Platform.Init(this, savedInstanceState);
            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.activity_main);

            var textTB = FindViewById<TextView>(Resource.Id.textViewTB);
            var butTB = FindViewById<ToggleButton>(Resource.Id.toggleButton);
            butTB.Click += (o, e) =>
            {

                if (butTB.Checked)
                    textTB.Text = "нажата";
                else
                    textTB.Text = "отпущена";
            };


            var counter = 1;
            var but = FindViewById<Button>(Resource.Id.button);
            but.Click += (o, e) =>
            {
                but.Text = "Счетчик:" + counter++.ToString();
            };


            Spinner spinner = FindViewById<Spinner>(Resource.Id.spinner);
            var adapter = ArrayAdapter.CreateFromResource(
                    this, Resource.Array.planets_array, Android.Resource.Layout.SimpleSpinnerItem);

            adapter.SetDropDownViewResource(Android.Resource.Layout.SimpleSpinnerDropDownItem);
            spinner.Adapter = adapter;


            var textSpin = FindViewById<TextView>(Resource.Id.textViewSwitch);
            var butS = FindViewById<Switch>(Resource.Id.Switch);
            butS.Click += (o, e) =>
            {

                if (butS.Checked)
                    textSpin.Text = "включен";
                else
                    textSpin.Text = "выключен";
            };


            var textSeek = FindViewById<TextView>(Resource.Id.textViewSeek);
            var seekBar = FindViewById<SeekBar>(Resource.Id.seekBar);
            seekBar.ProgressChanged += (object sender, SeekBar.ProgressChangedEventArgs e) =>
            {
                if (e.FromUser)
                {
                    textSeek.Text = string.Format("{0}", e.Progress);
                }


                var button = FindViewById<Button>(Resource.Id.btnNext);
                button.Click += (s, e) =>
                {
                    Intent nextActivity = new Intent(this, typeof(Activity1));
                    StartActivity(nextActivity);
                };
            };
        }
        private void spinnerItemSelected(object sender, AdapterView.ItemSelectedEventArgs e)
        {
            Spinner spinner = (Spinner)sender;
            string toast = string.Format("The planet is {0}", spinner.GetItemAtPosition(e.Position));
            Toast.MakeText(this, toast, ToastLength.Long).Show();
        }

        public override void OnRequestPermissionsResult(int requestCode, string[] permissions, [GeneratedEnum] Android.Content.PM.Permission[] grantResults)
        {
            Xamarin.Essentials.Platform.OnRequestPermissionsResult(requestCode, permissions, grantResults);

            base.OnRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}