package com.example.nahushraichura.broadcasthelper;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.LoginFilter;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName, editTextEmail, editTextMobile,
            editTextDob, editTextAge;

    private Button buttonSubmit;
    private AwesomeValidation awesomeValidation;

    public String name,mobile,dob,age;
    public  String email;
    public String userdetails[]=new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupactivity);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextMobile = (EditText) findViewById(R.id.editTextMobile);
        editTextDob = (EditText) findViewById(R.id.editTextDob);
        editTextAge = (EditText) findViewById(R.id.editTextAge);

        name=editTextName.getText().toString();
        email=editTextEmail.getText().toString();
        mobile=editTextMobile.getText().toString();
        dob=editTextDob.getText().toString();
        age=editTextAge.getText().toString();

        userdetails[0]=editTextName.getText().toString();
        userdetails[1]=editTextEmail.getText().toString();
        userdetails[2]=editTextMobile.getText().toString();
        userdetails[3]=editTextDob.getText().toString();
        userdetails[4]=editTextAge.getText().toString();


        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        //buttonSubmit.setOnClickListener(this);
        /*awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.editTextName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextMobile, "^[2-9]{2}[0-9]{8}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextDob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);*/

        buttonSubmit.setOnClickListener(this);


    }

    private void submitForm() {
        //first validate the form then move ahead

            Toast.makeText(this, "Validation Successfull", Toast.LENGTH_LONG).show();

            final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                    R.style.myDialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            try {
                Log.d("name",name);
                Log.d("email",email);
                Log.d("mobile",mobile);
                Log.d("dob",dob);
                Log.d("age",age);
                String line=new RegisteringActivity(this, 0).execute(editTextName.getText().toString(),editTextEmail.getText().toString(),editTextMobile.getText().toString(),editTextDob.getText().toString(),editTextAge.getText().toString()).get();
                Log.d("line string ",line);
                line=line.trim();
                if(line.equalsIgnoreCase("registered"))
                {
                    //first time check
                    SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edt = pref.edit();
                    edt.putBoolean("activity_executed", true);
                    edt.commit();

                    //storing user data persistently
                    SharedPreferences prefuser = getSharedPreferences("ActivityPREFF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edtuser = prefuser.edit();
                   // SharedPreferences.Editor edit = prefs.edit();
                    edtuser.putInt("array_size", 5);
                    for(int i=0;i<4; i++)
                        edtuser.putString("array_" + i, userdetails[i]);
                    edtuser.commit();


                    FirebaseMessaging.getInstance().subscribeToTopic("test");
                    FirebaseInstanceId.getInstance().getToken();


                    Log.d("registration_successful", "true");
                    progressDialog.dismiss();

                    Intent in=new Intent(SignUpActivity.this,UserQr.class);
                    in.putExtra("email",editTextEmail.getText().toString());
                    SignUpActivity.this.startActivity(in);
                }
                else if(line.equalsIgnoreCase("email already registered")) {
                    Toast.makeText(this,"email id already registered",Toast.LENGTH_LONG).show();
                }
                else
                {


                    Log.d("registration_unccessful","false");
                    progressDialog.dismiss();
                    Toast.makeText(this,"registration failed ",Toast.LENGTH_LONG).show();


                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

    }



    public void onClick(View view) {
        if (view == buttonSubmit) {

            if (validate()) {
                //onSignupFailed();
                submitForm();
            }

        }
    }
    public boolean validate() {
        boolean valid = true;

        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String mobile = editTextMobile.getText().toString();
        String dob=editTextDob.getText().toString();
        String age=editTextAge.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            editTextName.setError("at least 3 characters");
            valid = false;
        }
        else {
            editTextName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 10 || mobile.length() > 10) {
            editTextMobile.setError("10 numeric characters");
            valid = false;
        } else {
            editTextMobile.setError(null);
        }
        if (dob.isEmpty()) {
            editTextMobile.setError("enter dob");
            valid = false;
        } else {
            editTextMobile.setError(null);
        }
        if (age.isEmpty() || Integer.parseInt(age) < 10 ||Integer.parseInt(age) > 80) {
            editTextMobile.setError("10-80 age");
            valid = false;
        } else {
            editTextMobile.setError(null);
        }

        return valid;
    }

}
