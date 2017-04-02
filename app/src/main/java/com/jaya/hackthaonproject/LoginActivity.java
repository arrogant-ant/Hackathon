package com.jaya.hackthaonproject;

import static android.Manifest.permission.READ_CONTACTS;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

  String result = "";
  String key;
  EditText et;
  EditText et2;
  TextView error_tx, progress_tx;
  boolean my_show;
  int my_shortAnimTime;
  String error_msg;
  static String emp_id, loc_id;
  String url1="http://wangle.website/update_fcm.php";
  /**
   * Id to identity READ_CONTACTS permission request.
   */
  private static final int REQUEST_READ_CONTACTS = 0;

  /**
   * A dummy authentication store containing known user names and passwords. TODO: remove after connecting to first real
   * authentication system.
   */
  private static final String[] DUMMY_CREDENTIALS = new String[]{
      "foo@example.com:hello", "bar@example.com:world"
  };
  /**
   * Keep track of the login task to ensure we can cancel it if requested.
   */
  private Getjason g = null;

  // UI references.
  private AutoCompleteTextView mEmailView;
  private EditText mPasswordView;
  private View mProgressView;
  private View mLoginFormView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    TextView tt = (TextView) findViewById(R.id.text_head);
    error_tx = (TextView) findViewById(R.id.error_login);
    Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Capture_it.ttf");
    tt.setTypeface(myCustomFont);
    //storing error
    error_msg = getIntent().getExtras().getString("error", "");
    error_tx.setText(error_msg);

    // Set up the login form.
    mEmailView = (AutoCompleteTextView) findViewById(R.id.editText);

    populateAutoComplete();

    mPasswordView = (EditText) findViewById(R.id.editText2);

    mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
          attemptLogin();
          return true;
        }
        return false;
      }
    });

    Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
    Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "fonts/Capture_it.ttf");
    mEmailSignInButton.setTypeface(myCustomFont2);
    mEmailSignInButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        attemptLogin();
      }
    });

    mLoginFormView = findViewById(R.id.login_form);
    mProgressView = findViewById(R.id.login_progress);
    progress_tx = (TextView) findViewById(R.id.progress_text);
  }

  private void populateAutoComplete() {
    if (!mayRequestContacts()) {
      return;
    }

    getLoaderManager().initLoader(0, null, this);
  }

  private boolean mayRequestContacts() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }
    if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
      return true;
    }
    if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
      Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
          .setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            @TargetApi(Build.VERSION_CODES.M)
            public void onClick(View v) {
              requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
            }
          });
    } else {
      requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
    }
    return false;
  }

  /**
   * Callback received when first permissions request has been completed.
   */
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == REQUEST_READ_CONTACTS) {
      if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        populateAutoComplete();
      }
    }
  }


  /**
   * Attempts to sign in or register the account specified by the login form. If there are form errors (invalid email,
   * missing fields, etc.), the errors are presented and no actual login attempt is made.
   */
  private void attemptLogin() {
    if (g != null) {
      return;
    }

    // Reset errors.
    mEmailView.setError(null);
    mPasswordView.setError(null);

    // Store values at the time of the login attempt.
    String email = mEmailView.getText().toString();
    String password = mPasswordView.getText().toString();

    boolean cancel = false;
    View focusView = null;

    // Check for first valid password, if the user entered one.
    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      mPasswordView.setError(getString(R.string.error_invalid_password));
      focusView = mPasswordView;
      cancel = true;
    }

    // Check for first valid email address.
    if (TextUtils.isEmpty(email)) {
      mEmailView.setError(getString(R.string.error_field_required));
      focusView = mEmailView;
      cancel = true;
    } else if (!isEmailValid(email)) {
      mEmailView.setError(getString(R.string.error_invalid_email));
      focusView = mEmailView;
      cancel = true;
    }

    if (cancel) {
      // There was an error; don't attempt login and focus the first
      // form field with an error.
      focusView.requestFocus();
    } else {
      // ShowSM first progress spinner, and kick off first background task to
      // perform the user login attempt.
      showProgress(true);
      et = (EditText) findViewById(R.id.editText);
      et2 = (EditText) findViewById(R.id.editText2);
      String s1 = et.getText().toString();
      String s2 = et2.getText().toString();
      g = new Getjason(this);
      g.execute(s1, s2);

    }
  }

  private boolean isEmailValid(String email) {
    //TODO: Replace this with your own logic
    return email.contains("@");
  }

  private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 4;
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  private void showProgress(final boolean show) {
    my_show = show;
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
      my_shortAnimTime = shortAnimTime;
      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
      mLoginFormView.animate().setDuration(shortAnimTime).alpha(
          show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
      });

      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
      mProgressView.animate().setDuration(shortAnimTime).alpha(
          show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
      });
      progress_tx.setVisibility(show ? View.VISIBLE : View.GONE);
      progress_tx.animate().setDuration(shortAnimTime).alpha(
          show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          progress_tx.setVisibility(show ? View.VISIBLE : View.GONE);
        }
      });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply ShowSM
      // and hide the relevant UI components.
      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
      progress_tx.setVisibility(show ? View.VISIBLE : View.GONE);
      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
  }


  @Override
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
    return new CursorLoader(this,
        // Retrieve data rows for the device user's 'profile' contact.
        Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
            ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

        // Select only email addresses.
        ContactsContract.Contacts.Data.MIMETYPE +
            " = ?", new String[]{ContactsContract.CommonDataKinds.Email
        .CONTENT_ITEM_TYPE},

        // ShowSM primary email addresses first. Note that there won't be
        // first primary email address if the user hasn't specified one.
        ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
  }

  @Override
  public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
    List<String> emails = new ArrayList<>();
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      emails.add(cursor.getString(ProfileQuery.ADDRESS));
      cursor.moveToNext();
    }

    addEmailsToAutoComplete(emails);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> cursorLoader) {

  }

  private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
    //Create adapter to tell the AutoCompleteTextView what to ShowSM in its dropdown list.
    ArrayAdapter<String> adapter =
        new ArrayAdapter<>(LoginActivity.this,
            android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

    mEmailView.setAdapter(adapter);
  }


  private interface ProfileQuery {

    String[] PROJECTION = {
        ContactsContract.CommonDataKinds.Email.ADDRESS,
        ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
    };

    int ADDRESS = 0;
    int IS_PRIMARY = 1;
  }

  /**
   * Represents an asynchronous login/registration task used to authenticate the user.
   */
  class Getjason extends AsyncTask<String, String, String> {

    String json_string;
    String json_url;
    Context ctx;
    String key;

    Getjason(Context ctx) {
      this.ctx = ctx;


    }


    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      json_url = "http://www.wangle.website/login.php";

    }

    @Override
    protected String doInBackground(String... params) {
      String first = params[0];
      String second = params[1];
      try {

        URL url = new URL(json_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();

        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String data = URLEncoder.encode("empID", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8") + "&" + URLEncoder
            .encode("pass", "UTF-8") + "=" + URLEncoder.encode(second, "UTF-8");
        bufferedWriter.write(data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();

        // reading from the server

        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuffer buffer = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
          buffer.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return buffer.toString().trim();

      } catch (MalformedURLException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
      super.onProgressUpdate(values);

    }

    protected void onPostExecute(String s) {
      super.onPostExecute(s);
      result = s;
      parse(ctx);


    }
  }

  void parse(Context ctx) {
    SharedPreferences sharedPreferences = getApplicationContext()
        .getSharedPreferences("fcmPreferences", Context.MODE_PRIVATE);

    try {
      JSONObject js = new JSONObject(result);

      String find;

      find = js.getString("status");
      emp_id = js.getString("emp_id");
      loc_id = js.getString("location_id");
      String fcmToken = null;
      if (js.has("fcm_token")) {
        fcmToken = js.getString("fcm_token");
      }

      if (find.equals("success")) {
        if (null == fcmToken && sharedPreferences.getString("fcm_token", null) != null) {
          postTokenToServer(emp_id, sharedPreferences.getString("fcm_token", null));
          key=sharedPreferences.getString("fcm_token", null);
          StringRequest stringRequest= new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
          },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
          }){
            @Override
            protected Map<String,String> getParams(){
              Map<String,String> params = new HashMap<String, String>();
              params.put("emp_id",emp_id);
              params.put("key",key);
              return params;
            }

          };

          VolleySingleton.getInstance(getApplicationContext()).addToReqQueue(stringRequest);

        }
        if (emp_id.contains("@ADMIN")) {
          Intent i = new Intent(ctx, PC.class);
          ctx.startActivity(i);

        } else if (emp_id.contains("@SM")) {
          Intent ip = new Intent(ctx, SM.class);
          ctx.startActivity(ip);
        } else {
          Intent ip = new Intent(ctx, Transport.class);
          ctx.startActivity(ip);
        }
      } else {
        Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG);

        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        intent.putExtra("error", "Invalid Credentials!! TRY AGAIN");
        finish();
        startActivity(intent);

      }


    } catch (JSONException e) {
      e.printStackTrace();
    }


  }

  private void postTokenToServer(String emp_id, String fcm_token) {

    //TODO write a call to server which will update user table for given empid update token
  }

  @Override
  protected void onPause() {
    super.onPause();
    finish();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    finish();
  }
}

