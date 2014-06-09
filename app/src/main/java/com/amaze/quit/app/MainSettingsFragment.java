package com.amaze.quit.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Robin on 9-6-2014.
 */
public class MainSettingsFragment extends PreferenceFragment {
    public static final String PREFS_NAME = "QuitPrefs";
    SharedPreferences settings = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Can retrieve arguments from preference XML.
        Log.i("args", "Arguments: " + getArguments());

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        bindOnClicks();
    }

    protected void bindOnClicks(){
        Preference quitDate = findPreference("quitDate");
        Preference quitTime = findPreference("quitTime");
        Preference brand = findPreference("brand");
        EditTextPreference dayAmount = (EditTextPreference) findPreference("dayAmount");
        dayAmount.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        EditTextPreference packAmount = (EditTextPreference) findPreference("packAmount");
        packAmount.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        Preference startFrag = findPreference("startFrag");

        //quitDate listener
        quitDate.setOnPreferenceClickListener(new OnPreferenceClickListener(){
            public boolean onPreferenceClick(Preference preference){
                    showDatePickerDialog(getView());
                return true;
            }
        });
        //quitTime listener
        quitTime.setOnPreferenceClickListener(new OnPreferenceClickListener(){
            public boolean onPreferenceClick(Preference preference){
                showTimePickerDialog(getView());
                return true;
            }
        });
        //brand listener
        brand.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int sId = Integer.parseInt(newValue.toString());
                DatabaseHandler db = new DatabaseHandler(getActivity());
                User user;
                user = db.getUser(1);
                user.setsID(sId);
                db.updateUser(user);
                db.close();
                return false;
            }
        });

        dayAmount.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int dayAmount = Integer.parseInt(newValue.toString());
                DatabaseHandler db = new DatabaseHandler(getActivity());
                User user;
                user = db.getUser(1);
                user.setPerDag(dayAmount);
                db.updateUser(user);
                db.close();
                return false;
            }
        });

        packAmount.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int packAmount = Integer.parseInt(newValue.toString());
                DatabaseHandler db = new DatabaseHandler(getActivity());
                User user;
                user = db.getUser(1);
                int sId = user.getsID();
                Sigaretten sigaretten = db.getSigaret(sId);
                sigaretten.setAantal(packAmount);
                db.updateUser(user);
                db.close();
                return false;
            }
        });

        startFrag.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue){
                int preferedFragment = Integer.parseInt(newValue.toString());
                settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                settings.edit().putInt("pref_frag",preferedFragment).commit();
                return false;
            }
        });

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        TimePicker timePicker;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            TimePickerDialog tpd = new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
            tpd.setButton(TimePickerDialog.BUTTON_POSITIVE,getString(R.string.setup_complete), new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    if(timePicker != null)
                    onTimeSet(timePicker, timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                }
            });
            return tpd;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
            DatabaseHandler db = new DatabaseHandler(getActivity());
            User user;
            user = db.getUser(1);
            user.setQuitHour(hourOfDay);
            user.setQuitMinute(minuteOfHour);
            db.updateUser(user);
            db.close();
        }


        public void onTimeChanged(TimePicker view, int hourOfDay, int minute){
            timePicker = view;
        }


    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            final DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);;
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.setup_complete), new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                 DatePicker datePicker = dpd.getDatePicker();
                    onDateSet(datePicker,
                            datePicker.getYear(),
                            datePicker.getMonth(),
                            datePicker.getDayOfMonth());
                }
            });
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            DatabaseHandler db = new DatabaseHandler(getActivity());
            User user;
            user = db.getUser(1);
            user.setQuitYear(year);
            user.setQuitMonth(month);
            user.setQuitDay(day);
            db.updateUser(user);
            db.close();
        }





    }
}
