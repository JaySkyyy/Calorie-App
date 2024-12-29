package appcr.ushakov.caloriecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import appcr.ushakov.caloriecalculator.R;

public class CalcActivity extends AppCompatActivity{

    private EditText editTextAge, editTextWeight, editTextHeight;
    private RadioButton radioButtonMale, radioButtonFemale;
    private Spinner spinnerActivityLevel;
    private RadioButton radioButtonMifflinSanJeor, radioButtonHarrisBenedict;
    private Button buttonCalculate;
    private TextView textViewMaintenance, textViewLoss, textViewGain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Кнопка назад
        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(CalcActivity.this, MainActivity.class);
                    startActivity(intent);finish();
                }catch (Exception ex){

                }
            }
        });

        // Находим все элементы пользовательского интерфейса
        editTextAge = findViewById(R.id.editTextAge);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        spinnerActivityLevel = findViewById(R.id.spinnerActivityLevel);
        radioButtonMifflinSanJeor = findViewById(R.id.radioButtonMifflinSanJeor);
        radioButtonHarrisBenedict = findViewById(R.id.radioButtonHarrisBenedict);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewMaintenance = findViewById(R.id.textViewMaintenance);
        textViewLoss = findViewById(R.id.textViewLoss);
        textViewGain = findViewById(R.id.textViewGain);

        // Создаем адаптер для Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivityLevel.setAdapter(adapter);

        // Добавляем обработчик нажатия на кнопку Calculate
        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalories();
            }
        });
    }

    // Метод для расчета калорий
    private void calculateCalories() {
        // Получаем значения возраста, веса и роста из текстовых полей
        int age = Integer.parseInt(editTextAge.getText().toString());
        int weight = Integer.parseInt(editTextWeight.getText().toString());
        int height = Integer.parseInt(editTextHeight.getText().toString());

        // Определяем пол
        int gender = radioButtonMale.isChecked() ? 1 : 2;

        // Определяем уровень физической активности
        double activityLevel;
        switch (spinnerActivityLevel.getSelectedItemPosition()) {
            case 0:
                activityLevel = 1.2;
                break;
            case 1:
                activityLevel = 1.375;
                break;
            case 2:
                activityLevel = 1.55;
                break;
            case 3:
                activityLevel = 1.725;
                break;
            case 4:
                activityLevel = 1.9;
                break;
            default:
                activityLevel = 1.2;
        }

        // Определяем используемую формулу
        boolean isMifflin = radioButtonMifflinSanJeor.isChecked();

        // Рассчитываем базовый уровень обмена веществ (BMR)
        double bmr;
        if (isMifflin) {
            if (gender == 1) {
                bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
            } else {bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
            }
        } else {
            if (gender == 1) {
                bmr = (13.75 * weight) + (5 * height) - (6.76 * age) + 66;
            } else {
                bmr = (9.56 * weight) + (1.85 * height) - (4.68 * age) + 655;
            }
        }

        // Рассчитываем количество калорий для поддержания веса (tdee)
        double tdee = bmr * activityLevel;

        // Выводим результаты
        double deficit = 500;
        double surplus = 500;
        double maintenance = tdee;
        double loss = tdee - deficit;
        double gain = tdee + surplus;

        textViewMaintenance.setText(getString(R.string.result_maintenance, (int) Math.round(maintenance)));
        textViewLoss.setText(getString(R.string.result_loss, (int) Math.round(loss)));
        textViewGain.setText(getString(R.string.result_gain, (int) Math.round(gain)));

    }

    //Тут код для системной кнопки назад
    @Override
    public void onBackPressed(){
        try {
            Intent intent = new Intent(CalcActivity.this, MainActivity.class);
            startActivity(intent);finish();
        }catch (Exception ex){

        }
    }
}