package com.hello.myapplicationex;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */

    SQLiteDatabase db1 = null;
    private static String DBNAME = "dailyExpenses.db";
    Button btn1, btn2, btn3, btn4,btn5;
    EditText edt1, edt2, tvw;
    Editable d1, d2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        btn1 = (Button) findViewById(R.id.btn1);
        //Database will be created through below method
        db1 = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
        //Create the table if it is not existing

        btn1.setOnClickListener(new OnClickListener() {
            //handling the click method on button
            @Override
            public void onClick(View view) {
                //For fetching data from edittext, use editable instead of string
                d1 = edt1.getText();
                d2 = edt2.getText();

                db1.execSQL("CREATE TABLE IF NOT EXISTS tabq34(AMOUNT VARCHAR, REASON VARCHAR); ");
                db1.execSQL("INSERT INTO tabq34 (AMOUNT,REASON)  VALUES ('" + d1 + "','" + d2 + "');");
                Toast toast = Toast.makeText(getApplicationContext(),"Information inserted", Toast.LENGTH_SHORT);
                toast.show();


            }
        });


        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                db1.execSQL("DELETE  FROM tabq34;");
                Toast toast = Toast.makeText(getApplicationContext(),"Information deleted", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                db1.execSQL("UPDATE tabq34 SET AMOUNT='0' WHERE REASON='NULL'");
            }
        });

        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                tvw = (EditText) findViewById(R.id.tvw2);
                try {
                    Cursor c = db1.rawQuery("SELECT * FROM tabq34", null);

                    if (c != null) {
                        if (c.moveToFirst()) {
                            do {
                                //whole data of column is fetched by getColumnIndex()
                                String amount = c.getString(c.getColumnIndex("AMOUNT"));
                                String reason = c.getString(c.getColumnIndex("REASON"));
                                System.out.println(amount);
                                System.out.println(reason);
                                tvw.append(amount + reason + "\n");
                            } while (c.moveToNext());
                        }
                        //count the total number of entries
                        Integer a = c.getCount();
                        System.out.println(a);
                        c.close();
                        //db1.close();
                        //if you close the database then illegal exception will be occured...
                    }
                } catch (Exception e) {
                    System.out.println(e);

                    // tvw.setText(d1+""+d2);
                    // String s = (String) tvw.getText();
                    // System.out.print(s);
                }
            }

            ;
        });

        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                edt1.setText(" ");
                edt2.setText(" ");
                tvw.setText(" ");
            }
        });

    }
}

