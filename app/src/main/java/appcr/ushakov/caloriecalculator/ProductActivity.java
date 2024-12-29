package appcr.ushakov.caloriecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import appcr.ushakov.caloriecalculator.R;

public class ProductActivity extends AppCompatActivity{

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private List<Product> listTemp;
    private DatabaseReference mDataBase;
    private String PRODUCT_KEY = "Product";
    private EditText searchEditText;
    private ProductFilter productFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_db);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Кнопка назад
        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                    startActivity(intent);finish();
                }catch (Exception ex){

                }
            }
        });

        init();
        getDataFromDB();
        setOnClickItem();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void init(){
        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
        mDataBase = FirebaseDatabase.getInstance().getReference(PRODUCT_KEY);

        productFilter = new ProductFilter();
        listView.setTextFilterEnabled(true);

        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private class ProductFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listData);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : listData) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listTemp.clear();
            listTemp.addAll((List<Product>) results.values);
            adapter.notifyDataSetChanged();
        }
    }


    private void getDataFromDB()
    {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(listData.size() > 0)listData.clear();
                if(listTemp.size() > 0)listTemp.clear();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    Product product = ds.getValue(Product.class);
                    assert product != null;
                    listData.add(product.name);
                    listTemp.add(product);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    private void setOnClickItem() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Product product = listTemp.get(position);
            Intent intent = new Intent(ProductActivity.this, ShowActivity.class);
            intent.putExtra(Constant.PRODUCT_NAME, product.name);
            intent.putExtra(Constant.PRODUCT_CAL, product.cal);
            startActivity(intent);
        });
    }

    //Тут код для системной кнопки назад
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(ProductActivity.this, MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception ex){

        }
    }
}