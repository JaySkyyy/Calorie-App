package appcr.ushakov.caloriecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import appcr.ushakov.caloriecalculator.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button buttonCalc = (Button) findViewById(R.id.buttonCalc);
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this,CalcActivity.class);
                    startActivity(intent);finish();
                }catch(Exception ex){}
            }
        });

        Button buttonCalorie = (Button) findViewById(R.id.buttonCallorie);
        buttonCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this,ProductActivity.class);
                    startActivity(intent);finish();
                }catch(Exception ex){}
            }
        });

        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this,ProductAdd.class);
                    startActivity(intent);finish();
                }catch(Exception ex){}
            }
        });
    }
}