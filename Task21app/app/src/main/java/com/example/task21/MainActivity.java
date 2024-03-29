package com.example.task21;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner typeSpinner, sourceUnitSpinner, destinationUnitSpinner;
    private EditText valueInput;
    private Button convertButton;
    private TextView resultOutput;

    private static final double INCH_TO_CM = 2.54;
    private static final double FOOT_TO_CM = 30.48;
    private static final double YARD_TO_CM = 91.44;
    private static final double MILE_TO_KM = 1.60934;
    private static final double POUND_TO_KG = 0.453592;
    private static final double OUNCE_TO_G = 28.3495;
    private static final double TON_TO_KG = 907.185;

    private double convertLength(double inputValue, String sourceUnit, String destinationUnit) {
        double result = 0.0;
        switch (sourceUnit) {
            case "Inch (in)":
                result = inputValue * INCH_TO_CM;
                break;
            case "Foot (ft)":
                result = inputValue * FOOT_TO_CM;
                break;
            case "Yard (yd)":
                result = inputValue * YARD_TO_CM;
                break;
            case "Mile (mi)":
                result = inputValue * MILE_TO_KM * 1000;
                break;
            case "Centimeter (cm)":
                result = inputValue;
                break;
            case "Meter (m)":
                result = inputValue * 100;
                break;
            case "Kilometer (km)":
                result = inputValue * 100000;
                break;
        }
        switch (destinationUnit) {
            case "Inch (in)":
                result /= INCH_TO_CM;
                break;
            case "Foot (ft)":
                result /= FOOT_TO_CM;
                break;
            case "Yard (yd)":
                result /= YARD_TO_CM;
                break;
            case "Mile (mi)":
                result /= (MILE_TO_KM * 1000);
                break;
            case "Centimeter (cm)":
                break;
            case "Meter (m)":
                result /= 100;
                break;
            case "Kilometer (km)":
                result /= 100000;
                break;
        }
        return result;
    }


    private double convertWeight(double inputValue, String sourceUnit, String destinationUnit) {
        double result = 0.0;
        switch (sourceUnit) {
            case "Pound (lb)":
                result = inputValue * POUND_TO_KG;
                break;
            case "Ounce (oz)":
                result = inputValue * OUNCE_TO_G / 1000;
                break;
            case "Ton (t)":
                result = inputValue * TON_TO_KG;
                break;
            case "Kilogram (kg)":
                result = inputValue;
                break;
            case "Gram (g)":
                result = inputValue / 1000;
                break;
        }
        switch (destinationUnit) {
            case "Pound (lb)":
                result /= POUND_TO_KG;
                break;
            case "Ounce (oz)":
                result *= 1000 / OUNCE_TO_G;
                break;
            case "Ton (t)":
                result /= TON_TO_KG;
                break;
            case "Kilogram (kg)":
                break;
            case "Gram (g)":
                result *= 1000;
                break;
        }
        return result;
    }


    private double convertTemperature(double inputValue, String sourceUnit, String destinationUnit) {
        double result = 0.0;

        if (sourceUnit.equals(destinationUnit)) {
            return inputValue;
        }

        switch (sourceUnit) {
            case "Celsius (°C)":
                if (destinationUnit.equals("Fahrenheit (°F)")) {
                    result = (inputValue * 1.8) + 32;
                } else if (destinationUnit.equals("Kelvin (K)")) {
                    result = inputValue + 273.15;
                }
                break;
            case "Fahrenheit (°F)":
                if (destinationUnit.equals("Celsius (°C)")) {
                    result = (inputValue - 32) / 1.8;
                } else if (destinationUnit.equals("Kelvin (K)")) {
                    result = (inputValue - 32) / 1.8 + 273.15;
                }
                break;
            case "Kelvin (K)":
                if (destinationUnit.equals("Celsius (°C)")) {
                    result = inputValue - 273.15;
                } else if (destinationUnit.equals("Fahrenheit (°F)")) {
                    result = (inputValue - 273.15) * 1.8 + 32;
                }
                break;
        }
        return result;
    }


    private double convert(double inputValue, String sourceUnit, String destinationUnit, String conversionType) {
        switch (conversionType) {
            case "Length":
                return convertLength(inputValue, sourceUnit, destinationUnit);
            case "Weight":
                return convertWeight(inputValue, sourceUnit, destinationUnit);
            case "Temperature":
                return convertTemperature(inputValue, sourceUnit, destinationUnit);
            default:
                return 0.0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeSpinner = findViewById(R.id.type_spinner);
        sourceUnitSpinner = findViewById(R.id.source_unit_spinner);
        destinationUnitSpinner = findViewById(R.id.destination_unit_spinner);
        valueInput = findViewById(R.id.value_input);
        convertButton = findViewById(R.id.convert_button);
        resultOutput = findViewById(R.id.result_output);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.conversion_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.weight_options, android.R.layout.simple_spinner_item);
                        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sourceUnitSpinner.setAdapter(weightAdapter);
                        destinationUnitSpinner.setAdapter(weightAdapter);
                        break;
                    case 1:
                        ArrayAdapter<CharSequence> lengthAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.length_options, android.R.layout.simple_spinner_item);
                        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sourceUnitSpinner.setAdapter(lengthAdapter);
                        destinationUnitSpinner.setAdapter(lengthAdapter);
                        break;
                    case 2:
                        ArrayAdapter<CharSequence> temperatureAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.temperature_options, android.R.layout.simple_spinner_item);
                        temperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sourceUnitSpinner.setAdapter(temperatureAdapter);
                        destinationUnitSpinner.setAdapter(temperatureAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = typeSpinner.getSelectedItem().toString();
                String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
                String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();
                double inputValue = Double.parseDouble(valueInput.getText().toString());

                double result = convert(inputValue, sourceUnit, destinationUnit, type);

                String formattedResult = String.format("%.8f", result);

                resultOutput.setText(formattedResult);
            }
        });

    }
}
