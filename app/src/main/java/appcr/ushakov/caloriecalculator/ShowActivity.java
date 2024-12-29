package appcr.ushakov.caloriecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ShowActivity extends AppCompatActivity{

    private TextView productName, productCal, productTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_show);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Кнопка назад
        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ShowActivity.this, ProductActivity.class);
                    startActivity(intent);finish();
                }catch (Exception ex){

                }
            }
        });

        init();
        getIntentMain();
    }

    private void init(){
        productName = findViewById(R.id.productName);
        productCal = findViewById(R.id.productCal);
        productTemp = findViewById(R.id.productTemp);
    }

    private void getIntentMain()
    {
        Intent intent = getIntent();
        if(intent != null)
        {
            productName.setText(intent.getStringExtra(Constant.PRODUCT_NAME));
            productCal.setText(intent.getStringExtra(Constant.PRODUCT_CAL));
        }
    }

    //Тут код для системной кнопки назад
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(ShowActivity.this, MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception ex){

        }
    }
}