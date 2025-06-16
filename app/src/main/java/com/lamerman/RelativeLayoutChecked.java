package com.lamerman;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import com.karumi.dexter.R;

/* loaded from: classes.dex */
public class RelativeLayoutChecked extends RelativeLayout implements Checkable {

    /* renamed from: b, reason: collision with root package name */
    private boolean f4076b;

    public RelativeLayoutChecked(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void a() {
        if (this.f4076b) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        } else {
            setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.f4076b;
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        this.f4076b = z;
        a();
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.f4076b);
    }
}
