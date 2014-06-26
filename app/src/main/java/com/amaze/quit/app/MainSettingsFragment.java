package com.amaze.quit.app;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Robin on 9-6-2014.
 */
public class MainSettingsFragment extends PreferenceFragment {
    public static final String PREFS_NAME = "QuitPrefs";
    SharedPreferences settings = null;
    ListPreference brand;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Can retrieve arguments from preference XML.
        Log.i("args", "Arguments: " + getArguments());

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        bindOnClicks();
        setListPreferenceData(brand,getActivity());

    }

    protected void bindOnClicks() {
        Preference quitDate = findPreference("quitDate");
        Preference quitTime = findPreference("quitTime");
        brand = (ListPreference) findPreference("brand");
        ListPreference smokePreference = (ListPreference) findPreference("smokePreference");
        EditTextPreference dayAmount = (EditTextPreference) findPreference("dayAmount");
        dayAmount.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        EditTextPreference packAmount = (EditTextPreference) findPreference("packAmount");
        packAmount.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        EditTextPreference price = (EditTextPreference) findPreference("price");
        //this makes sure you can only enter floats and doubles in the inputfield
        price.getEditText().setInputType(0x00002002);

        Preference startFrag = findPreference("startFrag");
        Preference product = findPreference("product");
        Preference spentAmount = findPreference("spentAmount");


        //product listener
        product.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), ChooseProductHost.class);
                startActivity(intent);
                return true;
            }
        });
        //quitDate listener
        quitDate.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                alertAmountResetDialog(true);
                return true;
            }
        });
        //quitTime listener
        quitTime.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                showTimePickerDialog(getView());
                return true;
            }
        });
        //smokePreference listener
        smokePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int smokeType = Integer.parseInt(newValue.toString());
                DatabaseHandler db = new DatabaseHandler(getActivity());
                User user = db.getUser(1);
                user.setShagorsig(smokeType);
                db.updateUser(user);
                db.close();
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
                if(dayAmount == 0){
                    noZero();
                    return false;
                }
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
                if(packAmount == 0){
                    noZero();
                    return false;
                }
                DatabaseHandler db = new DatabaseHandler(getActivity());
                User user;
                user = db.getUser(1);
                int sId = user.getsID();
                if (user.getShagorsig() == 1) {
                    Sigaretten sigaretten = db.getSigaret(sId);
                    sigaretten.setAantal(packAmount);
                    db.updateSigaretten(sigaretten);
                }
                else{
                    Shag shag = db.getShag(sId);
                    shag.setAantal(packAmount);
                    db.updateShag(shag);
                }

                db.close();
                return false;
            }
        });

        price.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                float price = Float.parseFloat(newValue.toString());
                if(price == 0){
                    noZero();
                    return false;
                }
                DatabaseHandler db = new DatabaseHandler(getActivity());
                User user;
                user = db.getUser(1);
                int sId = user.getsID();
                if (user.getShagorsig() == 1) {
                    Sigaretten sigaretten = db.getSigaret(sId);
                    sigaretten.setPrijs(price);
                    db.updateSigaretten(sigaretten);
                }
                else{
                    Shag shag = db.getShag(sId);
                    shag.setPrijs(price);
                    db.updateShag(shag);
                }

                db.close();
                return false;
            }
        });

        startFrag.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int preferedFragment = Integer.parseInt(newValue.toString());
                settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                settings.edit().putInt("pref_frag", preferedFragment).commit();
                return false;
            }
        });
        DatabaseHandler db = new DatabaseHandler(getActivity());
        String spentCash = " €" + String.valueOf(db.getUser(1).getSpentAmount()) +".";
        String spentSummary = getResources().getString(R.string.pref_reset_spent_summary);
        spentAmount.setSummary(spentSummary + spentCash);
        db.close();
        spentAmount.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                alertAmountResetDialog(false);
                return false;
            }
        });

    }

    protected static void setListPreferenceData(ListPreference lp, Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        List<CharSequence> entries = new ArrayList<CharSequence>();
        List<CharSequence> entryValues = new ArrayList<CharSequence>();
        if(db.getUser(1).getShagorsig() == 0){
            for (int i = 1; i <= db.getSigarettenAmount(); i++) {
                entries.add(db.getSigaret(i).getMerk());
                entryValues.add( String.valueOf(db.getSigaret(i).getsID()));
            }
        }else{
            for (int i = 1; i <= db.getShagAmount(); i++) {
                entries.add(db.getShag(i).getMerk());
                entryValues.add( String.valueOf(db.getShag(i).getsID()));
            }
        }
        CharSequence[] entriesArray = entries.toArray(new CharSequence[entries.size()]);
        CharSequence[] entriesValuesArray = entryValues.toArray(new CharSequence[entryValues.size()]);
        entryValues.toArray();
        lp.setEntries(entriesArray);
        lp.setDefaultValue("1");
        lp.setEntryValues(entriesValuesArray);
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
            tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, getString(R.string.setup_complete), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (timePicker != null)
                        onTimeSet(timePicker, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
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


        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
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
            final DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, getString(R.string.setup_complete), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
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

    //dialog for the warning if you change your quit date
    private void alertAmountResetDialog(final boolean redirect){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message;
        if(redirect)
            message = getResources().getString(R.string.alert_reset_spent);
        else
            message = getResources().getString(R.string.alert_reset_spent_second);
        builder.setMessage(message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //adds the saved amount up to the spent amount of the user since the user just bought the product
                        DatabaseHandler db = new DatabaseHandler(getActivity());
                        User user = db.getUser(1);
                        user.setSpentAmount(0);
                        db.updateUser(user);
                        db.close();
                        if(redirect)
                        showDatePickerDialog(getView());



                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setTitle(R.string.be_alert);

        // Create the AlertDialog object and show it
        builder.create();
        AlertDialog newProductDialog = builder.create();
        newProductDialog.show();
    }

    private void noZero(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message;
        message = getResources().getString(R.string.no_zero);
        builder.setMessage(message)
                .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .setTitle(R.string.error);

        // Create the AlertDialog object and show it
        builder.create();
        AlertDialog newProductDialog = builder.create();
        newProductDialog.show();
    }




}
