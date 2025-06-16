package com.psych.mtktool;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.psych.mtktool.R;

/* loaded from: classes.dex */
public class FixNameActivity extends Activity implements View.OnClickListener {

    /* renamed from: b, reason: collision with root package name */
    private TextView f1439b;

    /* renamed from: c, reason: collision with root package name */
    private EditText f1440c;

    /* renamed from: d, reason: collision with root package name */
    private Button f1441d;

    class a implements TextWatcher {
        a() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            int length = editable.length();
            FixNameActivity.this.f1441d.setEnabled(length < 25);
            FixNameActivity.this.f1439b.setText(FixNameActivity.this.getString(R.string.current_length, new Object[]{Integer.valueOf(length)}));
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.maxCancel) {
            setResult(0);
            finish();
        } else {
            if (id != R.id.maxOk) {
                return;
            }
            getIntent().putExtra("name_result", this.f1440c.getText().toString());
            setResult(-1, getIntent());
            finish();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.correct_name);
        this.f1439b = (TextView) findViewById(R.id.maxLabel);
        this.f1441d = (Button) findViewById(R.id.maxOk);
        EditText editText = (EditText) findViewById(R.id.maxName);
        this.f1440c = editText;
        editText.addTextChangedListener(new a());
        this.f1440c.setText(getIntent().getStringExtra("name_request"));
    }
}
