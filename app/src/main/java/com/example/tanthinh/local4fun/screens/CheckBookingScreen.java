package com.example.tanthinh.local4fun.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Post;
import com.google.gson.Gson;

import java.util.Calendar;

public class CheckBookingScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageButton back_arrow_btn;

    private DatePickerDialog picker;
    private EditText eText;
    private Spinner numPerson;
    private Spinner numTime;
    private TextView totalPriceTxtView;

    private Button book_now_btn;

    private double totalPrice = 0;
    private int numberPerson = 0;
    private String numberTime = "";
    private String bookingDate = "";

    private Post currentPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_booking_screen);

        init();
    }

    private void init()
    {
        Intent initIntent = getIntent();
        final String postString = (String)initIntent.getExtras().get("postObject");
        Gson gson = new Gson();
        currentPost = gson.fromJson(postString, Post.class);

        back_arrow_btn = findViewById(R.id.back_arrow_btn);
        back_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CheckBookingScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText((monthOfYear + 1) + "/" + dayOfMonth+ "/" + year);
                                bookingDate = eText.getText().toString();
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(currentPost.getMinDate());
                picker.getDatePicker().setMaxDate(currentPost.getMaxDate());
                picker.show();
            }
        });

        numTime = (Spinner)findViewById(R.id.time_spinner);
        numPerson = (Spinner)findViewById(R.id.num_person_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.num_person, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numPerson.setAdapter(adapter);
        numPerson.setOnItemSelectedListener(this);
        numPerson.setSelection(0);

        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numTime.setAdapter(adapterTime);
        numTime.setOnItemSelectedListener(this);
        numTime.setSelection(0);

        totalPriceTxtView = (TextView)findViewById(R.id.total_value);

        book_now_btn = findViewById(R.id.book_now_btn);
        book_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payIntent = new Intent(getApplicationContext(), PayDetailsScreen.class);
                payIntent.putExtra("postObject", postString);
                payIntent.putExtra("totalPrice", totalPrice);
                payIntent.putExtra("numberPerson", numberPerson);
                payIntent.putExtra("numberTime", numberTime);
                payIntent.putExtra("bookingDate", bookingDate);
                startActivity(payIntent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.num_person_spinner)
        {
            Log.w("selected", "numperson");
            int numPerson = Integer.parseInt((String)adapterView.getItemAtPosition(i));
            numberPerson = numPerson;
            totalPrice = numPerson * currentPost.getPricePerPerson();
            totalPriceTxtView.setText("$" + String.valueOf(totalPrice));
        }else
        {
            Log.w("selected", "numTime");
            numberTime = (String)adapterView.getItemAtPosition(i);
            Log.w("num time selected",numberTime);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
