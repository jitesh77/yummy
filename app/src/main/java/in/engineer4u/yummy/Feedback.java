package in.engineer4u.yummy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Feedback extends ActionBarActivity implements View.OnClickListener {

    private Toolbar toolbar;
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    EditText reciep, msg;
    TextView sub;
    String rec, subject, textMessage;
    RatingBar ratingBar;
    String flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        toolbar= (Toolbar) findViewById(R.id.yummy_app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
        sub = (TextView) findViewById(R.id.textView4);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                 String ratedValue = String.valueOf(ratingBar.getRating());
                if (ratedValue.equals("1.0") || ratedValue.equals("0.5"))
                    flag = "Poor";
                if (ratedValue.equals("2.0") || ratedValue.equals("1.5"))
                    flag = "Fair";
                if (ratedValue.equals("3.0") || ratedValue.equals("2.5"))
                    flag = "Average";
                if (ratedValue.equals("4.0") || ratedValue.equals("3.5"))
                    flag = "Good";
                if (ratedValue.equals("5.0") || ratedValue.equals("4.5"))
                    flag = "Excellent";
                sub.setText("Your experience : "
                        + flag);
            }
        });


        context = this;

        Button login = (Button) findViewById(R.id.btn_submit);
        sub = (TextView) findViewById(R.id.et_sub);
        msg = (EditText) findViewById(R.id.et_text);

        login.setOnClickListener(this);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        if(id==android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        rec = "ranjanrupesh1@gmail.com";
        subject = "Feedback from Yummy :"+(sub.getText().toString());

        textMessage = msg.getText().toString();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("yummyibm@gmail.com", "yummy12345");
            }
        });

        pdialog = ProgressDialog.show(context, "", "Sending Mail...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("yummyibm@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pdialog.dismiss();
            reciep.setText("");
            msg.setText("");
            sub.setText("");
            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }
}
