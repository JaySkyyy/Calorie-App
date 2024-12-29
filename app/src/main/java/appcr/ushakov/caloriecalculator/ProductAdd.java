package appcr.ushakov.caloriecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import appcr.ushakov.caloriecalculator.R;

public class ProductAdd extends AppCompatActivity{

    private EditText nameProduct, calProduct;
    private DatabaseReference mDataBase;
    private String PRODUCT_KEY = "Product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Кнопка назад
        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ProductAdd.this, MainActivity.class);
                    startActivity(intent);finish();
                }catch (Exception ex){

                }
            }
        });

        init();

        Button button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mDataBase.getKey();
                String name = nameProduct.getText().toString();
                String cal = calProduct.getText().toString();
                Product newProduct = new Product(id, name, cal);
                mDataBase.push().setValue(newProduct);
            }
        });
    }

    private void init()
    {
        nameProduct = findViewById(R.id.nameProduct);
        calProduct = findViewById(R.id.calProduct);
        mDataBase = FirebaseDatabase.getInstance().getReference(PRODUCT_KEY);
    }





    //Тут код для системной кнопки назад
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(ProductAdd.this, MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception ex){

        }
    }
}