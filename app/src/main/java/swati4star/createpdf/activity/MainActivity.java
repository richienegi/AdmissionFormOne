package swati4star.createpdf.activity;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import swati4star.createpdf.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    String usrotp;
    static String otpnumber;

    private static final int CAMERA_REQUEST = 1888;


    private Uri imageuri;
    Button browse, msubmit, mdoucment;
    TextInputEditText mMobileno;
    CircleImageView mimage;

    TextInputEditText mform_no, mcourse, mmobile, mname, memail, mwhatsno, maltno, mresAddress, mcoraddress,
            mQualification, muniversity, mpercent, myear, mFathername, mFatherOccup, mFatherContact, mMothername, mMotherOccup, mMotherContact
            ,mdegisnation, mcompanyname, mwebsite;
    EditText mcolgname, mcolgaddress;
    TextView mselecdob,mpdfdata;
    RadioGroup mradiosex, clgstatus;
    Spinner mfinddata;

    TextView madmit;
    DatePickerDialog.OnDateSetListener mDateListener;


    String gender = "", mobile_no;

public static String name;
    public static String mob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mdoucment = findViewById(R.id.document);

        mform_no = findViewById(R.id.form_no);
        mcourse = findViewById(R.id.course);
        mmobile = findViewById(R.id.mobile_no);
        mname = findViewById(R.id.name);
        memail = findViewById(R.id.email);
        mwhatsno = findViewById(R.id.whatsapp);
        maltno = findViewById(R.id.alternative_mobile);
        mselecdob = findViewById(R.id.dateOfBirth);
        mradiosex = findViewById(R.id.radioSex);
        mresAddress = findViewById(R.id.ResidentialAddress);
        mcoraddress = findViewById(R.id.CorrespondingAddress);
        mQualification = findViewById(R.id.Qualification);
        muniversity = findViewById(R.id.university);
        mpercent = findViewById(R.id.percentage);
        myear = findViewById(R.id.Passing_Year);
        mFathername = findViewById(R.id.Father_Name);
        mFatherOccup = findViewById(R.id.Father_Occupation);
        mFatherContact = findViewById(R.id.Father_Contact_No);
        mMothername = findViewById(R.id.Mother_Name);
        mMotherOccup = findViewById(R.id.Mother_Occupation);
        mMotherContact = findViewById(R.id.Mother_Contact_No);
        mdegisnation = findViewById(R.id.Degisnaion);
        mcompanyname = findViewById(R.id.Company_Name);
        mwebsite = findViewById(R.id.webSite);
        mcolgname = findViewById(R.id.College_Name);
        mcolgaddress = findViewById(R.id.clgaddress);
        clgstatus = findViewById(R.id.clgstate);
        mfinddata = findViewById(R.id.findus);
        madmit = findViewById(R.id.admit);
        String data = getResources().getString(R.string.admit);
        madmit.setText(data);
        browse = findViewById(R.id.browse);
        mimage = findViewById(R.id.circleimage);
        mMobileno = findViewById(R.id.mobile_no);
        msubmit = findViewById(R.id.submit);
        imageuri = null;






mpdfdata=findViewById(R.id.pdfdata);


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Image selecting", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);*/
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        mselecdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                int Year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateListener, Year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + " / " + month + " / " + year;
                mselecdob.setText(date);
            }
        };


        msubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View view) {
                if (mradiosex.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(MainActivity.this, "Please select gender", Toast.LENGTH_SHORT);
                } else {
                    int selectedId = mradiosex.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    gender = radioButton.getText().toString();
                }

                mobile_no = mmobile.getText().toString();
                if (!mobile_no.isEmpty()) {


                    otpnumber = getRandomNumberString();

                    String message = "Your OTP is " + otpnumber;

                    sendOTP(mobile_no, message);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter valid no", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(MainActivity.this, otpnumber, Toast.LENGTH_SHORT).show();

                Otp(view);
                // Toast.makeText(MainActivity.this, "Gender "+gender, Toast.LENGTH_SHORT).show();


            }

        });


        mdoucment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  ImageToPdfFragment imge=new ImageToPdfFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(OPEN_SELECT_IMAGES, true);
                getSupportFragmentManager().beginTransaction().replace(R.id.cont,imge).addToBackStack(null).commit();
                imge.setArguments(bundle);
           */
                if (!TextUtils.isEmpty(mname.getText().toString()) || !TextUtils.isEmpty(mmobile.getText().toString())) {
                    Intent i = new Intent(MainActivity.this, MainActivity2.class);
                    String cont = mmobile.getText().toString();
                    String val = cont.substring(5);
                    name = mname.getText().toString();
                    mob = val;
                    startActivity(i);
                    mpdfdata.setText(name+"_"+mob);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter name and contact no", Toast.LENGTH_SHORT).show();
                }



              /* ImageToPdfFragment fragment = new ImageToPdfFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(OPEN_SELECT_IMAGES, true);

                fragment.setArguments(bundle);

                if (areImagesRecevied())
                    fragment = new ImageToPdfFragment();

                getSupportFragmentManager().beginTransaction().replace(R.id.cont,fragment).commit();
                handleReceivedImagesIntent(fragment);

*/

            }
        });
        getRuntimePermissions();
    }


    //for camera contant


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && requestCode==RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mimage.setImageBitmap(photo);
        }
        else
        {
            Toast.makeText(this, "No Image selected", Toast.LENGTH_SHORT).show();
        }
    }


    public void Otp(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Enter Your OTP here");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                usrotp = input.getText().toString();
                if (usrotp.equals(otpnumber)) {
                    thanku(view);
                } else {
                    verified(view);
                }

                //Toast.makeText(getApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
            }

        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    //Otp Code


    private void sendOTP(String number, String message) {
        Toast.makeText(this, "send otp hits", Toast.LENGTH_SHORT).show();

        String url = "http://203.129.225.69/API/WebSMS/Http/v1.0a/index.php?username=cbitss&password=123456&sender=CBitss&to=91" + number + "&message=" + message + "&reqid=1&format={json|text}&route_id=7";

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue r = Volley.newRequestQueue(this);
        r.add(sr);
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }


    public void verified(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.verify, null);
        builder.setView(alertLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                //Toast.makeText(getApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
            }

        });
        builder.show();
    }


    public void thanku(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.thanku, null);
        TextView name = alertLayout.findViewById(R.id.name);
        name.setText(mname.getText());

        builder.setView(alertLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                //Toast.makeText(getApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();
            }

        });
        builder.show();
    }







    private boolean getRuntimePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) ||
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        0);
                return false;
            }
        }
        return true;
    }

}






