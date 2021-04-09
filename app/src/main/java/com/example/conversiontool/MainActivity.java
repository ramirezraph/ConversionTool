package com.example.conversiontool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

enum ConversionMode {
    Mass,
    Length,
    Area,
    Time
}

enum MassUnit {
    tonne,
    Kilogram,
    Gram,
    Milligram,
    Microgram,
    Pound,
    Ounce
}

public class MainActivity extends AppCompatActivity {

    Spinner spInputUnit;
    Spinner spOutputUnit;
    EditText txtInput, txtOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mode
        Spinner spConversionMode = (Spinner) findViewById(R.id.spConversionMode);
        spConversionMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mode = ConversionMode.values()[position].name();
                Toast.makeText(getApplicationContext(), mode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter modeDataAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                ConversionMode.values());
        modeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spConversionMode.setAdapter(modeDataAdapter);

        // Input
        txtInput = (EditText) findViewById(R.id.txtInput);
        txtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    txtOutput.setText("");
                    return;
                }

                displayOutput();
            }
        });
        EditText txtInputUnit = (EditText) findViewById(R.id.txtInputUnit);
        spInputUnit = (Spinner) findViewById(R.id.spInputUnit);
        spInputUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MassUnit unit = MassUnit.values()[position];
                txtInputUnit.setText(getUnitSymbol(unit));

                if (txtInput.getText().toString().isEmpty()) return;

                displayOutput();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter inputUnitDataAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                MassUnit.values());
        inputUnitDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInputUnit.setAdapter(inputUnitDataAdapter);

        // Output
        txtOutput = (EditText) findViewById(R.id.txtOutput);
        EditText txtOutputUnit = (EditText) findViewById(R.id.txtOutputUnit);

        spOutputUnit = (Spinner) findViewById(R.id.spOutputUnit);
        spOutputUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MassUnit unit = MassUnit.values()[position];
                txtOutputUnit.setText(getUnitSymbol(unit));

                if (txtInput.getText().toString().isEmpty()) return;

                displayOutput();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spOutputUnit.setAdapter(inputUnitDataAdapter);

        // Reset

        Button btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spConversionMode.setSelection(0);

                txtInput.setText("");
                txtOutput.setText("");

                spInputUnit.setSelection(0);
                spOutputUnit.setSelection(0);
            }
        });
    }

    private String getUnitSymbol(MassUnit unit) {
        switch (unit) {
            case tonne:
                return "t";
            case Kilogram:
                return "kg";
            case Gram:
                return "g";
            case Milligram:
                return "mg";
            case Microgram:
                return "μg";
            case Pound:
                return "lbs";
            case Ounce:
                return "oz";
            default:
                return "";
        }
    }

    private void displayOutput() {
        MassUnit inputCurrentUnit = MassUnit.values()[spInputUnit.getSelectedItemPosition()];
        MassUnit outputCurrentUnit = MassUnit.values()[spOutputUnit.getSelectedItemPosition()];

        double input = Double.parseDouble(txtInput.getText().toString());
        double output = getAnswer(inputCurrentUnit, outputCurrentUnit, input);
        txtOutput.setText(String.valueOf(output));
    }

    private double getAnswer (MassUnit inputUnit, MassUnit outputUnit, double inputValue) {
        switch (inputUnit) {
            case tonne:
                switch (outputUnit) {
                    case tonne:
                        return inputValue;
                    case Kilogram:
                        return inputValue * 1000;
                    case Gram:
                        return inputValue * 1e+6;
                    case Milligram:
                        return inputValue * 1e+9;
                    case Microgram:
                        return inputValue * 1e+12;
                    case Pound:
                        return inputValue * 2204.62262185;
                    case Ounce:
                        return inputValue * 35274;
                }
                break;
            case Kilogram:
                switch (outputUnit) {
                    case tonne:
                        return inputValue / 1000;
                    case Kilogram:
                        return inputValue;
                    case Gram:
                        return inputValue * 1000;
                    case Milligram:
                        return inputValue * 1e+6;
                    case Microgram:
                        return inputValue * 1e+9;
                    case Pound:
                        return inputValue * 2.20462;
                    case Ounce:
                        return inputValue * 35.274;
                }
                break;
            case Gram:
                switch (outputUnit) {
                    case tonne:
                        return inputValue / 1e+6;
                    case Kilogram:
                        return inputValue / 1000;
                    case Gram:
                        return inputValue;
                    case Milligram:
                        return inputValue * 1000;
                    case Microgram:
                        return inputValue * 1e+6;
                    case Pound:
                        return inputValue / 454;
                    case Ounce:
                        return inputValue / 28.35;
                }
                break;
            case Milligram:
                switch (outputUnit) {
                    case tonne:
                        return inputValue / 1e+9;
                    case Kilogram:
                        return inputValue / 1e+6;
                    case Gram:
                        return inputValue / 1000;
                    case Milligram:
                        return inputValue;
                    case Microgram:
                        return inputValue * 1000;
                    case Pound:
                        return inputValue / 453592;
                    case Ounce:
                        return inputValue / 28350;
                }
                break;
            case Microgram:
                switch (outputUnit) {
                    case tonne:
                        return inputValue / 1e+12;
                    case Kilogram:
                        return inputValue / 1e+9;
                    case Gram:
                        return inputValue / 1e+6;
                    case Milligram:
                        return inputValue / 1000;
                    case Microgram:
                        return inputValue;
                    case Pound:
                        return inputValue / 4.536e+8;
                    case Ounce:
                        return inputValue * 3.527396194958E-8;
                }
                break;
            case Pound:
                switch (outputUnit) {
                    case tonne:
                        return inputValue / 2205;
                    case Kilogram:
                        return inputValue / 2.205;
                    case Gram:
                        return inputValue * 453.592;
                    case Milligram:
                        return inputValue * 453592;
                    case Microgram:
                        return inputValue * 4.536e+8;
                    case Pound:
                        return inputValue;
                    case Ounce:
                        return inputValue * 16;
                }
                break;
            case Ounce:
                switch (outputUnit) {
                    case tonne:
                        return inputValue / 35274;
                    case Kilogram:
                        return inputValue / 35.274;
                    case Gram:
                        return inputValue * 28.35;
                    case Milligram:
                        return inputValue * 28350;
                    case Microgram:
                        return inputValue * 2.835e+7;
                    case Pound:
                        return inputValue / 16;
                    case Ounce:
                        return inputValue;
                }
                break;
            default:
                return inputValue;
        }

        return inputValue;
    }
}