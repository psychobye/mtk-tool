package com.lamerman;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.karumi.dexter.BuildConfig;
import com.psych.mtktool.R;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class FileDialog extends ListActivity {
    private static final String p = Environment.getRootDirectory().getPath();
    private static String q;

    /* renamed from: c, reason: collision with root package name */
    private TextView f4069c;

    /* renamed from: d, reason: collision with root package name */
    private EditText f4070d;
    private ArrayList<HashMap<String, Object>> e;
    private Button f;
    private LinearLayout g;
    private LinearLayout h;
    private InputMethodManager i;
    private String j;

    /* renamed from: b, reason: collision with root package name */
    private List<String> f4068b = null;
    private String k = p;
    private int l = 0;
    private String[] m = null;
    private boolean n = false;
    private HashMap<String, Integer> o = new HashMap<>();

    class a implements View.OnClickListener {
        a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ArrayList arrayList = new ArrayList();
            int count = FileDialog.this.getListView().getCount();
            SparseBooleanArray checkedItemPositions = FileDialog.this.getListView().getCheckedItemPositions();
            for (int i = 0; i < count; i++) {
                if (checkedItemPositions.get(i)) {
                    arrayList.add(new File((String) FileDialog.this.f4068b.get(i)));
                }
            }
            if (arrayList.size() > 0) {
                ArrayList<String> arrayList2 = new ArrayList<>();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    arrayList2.add(((File) it.next()).getPath());
                }
                FileDialog.this.getIntent().putStringArrayListExtra("RESULT_PATH", arrayList2);
                FileDialog fileDialog = FileDialog.this;
                fileDialog.setResult(-1, fileDialog.getIntent());
                FileDialog.this.finish();
            }
        }
    }

    class b implements View.OnClickListener {
        b() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FileDialog.this.j(view);
            FileDialog.this.f4070d.setText(BuildConfig.FLAVOR);
            FileDialog.this.f4070d.requestFocus();
        }
    }

    class c implements View.OnClickListener {
        c() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FileDialog.this.k(view);
        }
    }

    class d implements View.OnClickListener {
        d() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            FileDialog.this.k(view);
        }
    }

    class e implements View.OnClickListener {
        e() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (FileDialog.this.f4070d.getText().length() > 0) {
                FileDialog.this.getIntent().putExtra("RESULT_PATH", FileDialog.this.k + "/" + ((Object) FileDialog.this.f4070d.getText()));
                FileDialog fileDialog = FileDialog.this;
                fileDialog.setResult(-1, fileDialog.getIntent());
                FileDialog.this.finish();
            }
        }
    }

    class f implements DialogInterface.OnClickListener {
        f(FileDialog fileDialog) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    class g implements DialogInterface.OnClickListener {
        g(FileDialog fileDialog) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
        }
    }

    private void f(String str, int i) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("key", str);
        hashMap.put("image", Integer.valueOf(i));
        this.e.add(hashMap);
    }

    private int g() {
        ListView listView = getListView();
        int count = listView.getCount();
        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
        int i = 0;
        for (int i2 = 0; i2 < count; i2++) {
            if (checkedItemPositions.get(i2)) {
                i++;
            }
        }
        return i;
    }

    private void h(String str) {
        File file = new File(str);
        if (!file.canRead()) {
            new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("[" + file.getName() + "] " + ((Object) getText(R.string.cant_read_folder))).setPositiveButton("OK", new f(this)).show();
            return;
        }
        boolean z = str.length() < this.k.length();
        Integer num = this.o.get(this.j);
        i(str);
        q = str;
        if (num == null || !z) {
            return;
        }
        getListView().setSelection(num.intValue());
    }

    private void i(String str) {
        boolean z;
        this.k = str;
        ArrayList arrayList = new ArrayList();
        this.f4068b = new ArrayList();
        this.e = new ArrayList<>();
        File file = new File(this.k);
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            this.k = p;
            file = new File(this.k);
            listFiles = file.listFiles();
        }
        this.f4069c.setText(((Object) getText(R.string.location)) + ": " + this.k);
        String str2 = this.k;
        String str3 = p;
        if (!str2.equals(str3)) {
            arrayList.add(str3);
            f(str3, R.drawable.folder);
            this.f4068b.add(str3);
            arrayList.add("../");
            f("../", R.drawable.folder);
            this.f4068b.add(file.getParent());
            this.j = file.getParent();
        }
        TreeMap treeMap = new TreeMap();
        TreeMap treeMap2 = new TreeMap();
        TreeMap treeMap3 = new TreeMap();
        TreeMap treeMap4 = new TreeMap();
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                String name = file2.getName();
                treeMap.put(name, name);
                treeMap2.put(name, file2.getPath());
            } else {
                String name2 = file2.getName();
                String lowerCase = name2.toLowerCase();
                String[] strArr = this.m;
                if (strArr != null) {
                    int length = strArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            z = false;
                            break;
                        } else {
                            if (lowerCase.endsWith(strArr[i].toLowerCase())) {
                                z = true;
                                break;
                            }
                            i++;
                        }
                    }
                    if (z) {
                        treeMap3.put(name2, name2);
                        treeMap4.put(name2, file2.getPath());
                    }
                } else {
                    treeMap3.put(name2, name2);
                    treeMap4.put(name2, file2.getPath());
                }
            }
        }
        arrayList.addAll(treeMap.tailMap(BuildConfig.FLAVOR).values());
        arrayList.addAll(treeMap3.tailMap(BuildConfig.FLAVOR).values());
        this.f4068b.addAll(treeMap2.tailMap(BuildConfig.FLAVOR).values());
        this.f4068b.addAll(treeMap4.tailMap(BuildConfig.FLAVOR).values());
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, this.e, R.layout.file_dialog_row, new String[]{"key", "image"}, new int[]{R.id.fdrowtext, R.id.fdrowimage});
        Iterator it = treeMap.tailMap(BuildConfig.FLAVOR).values().iterator();
        while (it.hasNext()) {
            f((String) it.next(), R.drawable.folder);
        }
        Iterator it2 = treeMap3.tailMap(BuildConfig.FLAVOR).values().iterator();
        while (it2.hasNext()) {
            f((String) it2.next(), R.drawable.file);
        }
        simpleAdapter.notifyDataSetChanged();
        setListAdapter(simpleAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j(View view) {
        this.h.setVisibility(View.VISIBLE);
        this.g.setVisibility(View.GONE);
        this.i.hideSoftInputFromWindow(view.getWindowToken(), 0);
        this.f.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k(View view) {
        this.h.setVisibility(View.GONE);
        this.g.setVisibility(View.VISIBLE);
        this.i.hideSoftInputFromWindow(view.getWindowToken(), 0);
        this.f.setEnabled(false);
    }

    public void onAllClick(View view) {
        ListAdapter listAdapter = getListAdapter();
        if (listAdapter != null) {
            int count = listAdapter.getCount();
            for (int i = 0; i < count; i++) {
                if (!new File(this.f4068b.get(i)).isDirectory()) {
                    getListView().setItemChecked(i, true);
                }
            }
            this.f.setEnabled(g() > 0);
        }
    }

    public void onCancelClick(View view) {
        finish();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(0, getIntent());
        setContentView(R.layout.file_dialog_main);
        this.f4069c = (TextView) findViewById(R.id.path);
        this.f4070d = (EditText) findViewById(R.id.fdEditTextFile);
        this.i = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Button button = (Button) findViewById(R.id.fdButtonSelect);
        this.f = button;
        button.setEnabled(false);
        this.f.setOnClickListener(new a());
        Button button2 = (Button) findViewById(R.id.fdButtonNew);
        button2.setOnClickListener(new b());
        this.l = getIntent().getIntExtra("SELECTION_MODE", 0);
        this.m = getIntent().getStringArrayExtra("FORMAT_FILTER");
        this.n = getIntent().getBooleanExtra("CAN_SELECT_DIR", false);
        View findViewById = findViewById(R.id.btnAll);
        int i = this.l;
        if (i == 1 || i == 2) {
            button2.setEnabled(false);
            button2.setVisibility(View.GONE);
        }
        if (this.l == 2) {
            getListView().setChoiceMode(2);
        } else {
            getListView().setChoiceMode(1);
            findViewById.setVisibility(View.GONE);
        }
        this.g = (LinearLayout) findViewById(R.id.fdLinearLayoutSelect);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fdLinearLayoutCreate);
        this.h = linearLayout;
        linearLayout.setVisibility(View.GONE);
        Button button3 = (Button) findViewById(R.id.fdButtonCancel);
        button3.setOnClickListener(new c());
        button3.setOnClickListener(new d());
        ((Button) findViewById(R.id.fdButtonCreate)).setOnClickListener(new e());
        if (this.n) {
            this.f.setEnabled(true);
        }
        String str = q;
        if (str != null) {
            h(str);
            return;
        }
        String stringExtra = getIntent().getStringExtra("START_PATH");
        if (stringExtra == null) {
            stringExtra = p;
        }
        h(stringExtra);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        this.f.setEnabled(false);
        if (this.h.getVisibility() == View.VISIBLE) {
            this.h.setVisibility(View.GONE);
            this.g.setVisibility(View.VISIBLE);
            return true;
        }
        if (this.k.equals(p)) {
            return super.onKeyDown(i, keyEvent);
        }
        h(this.j);
        return true;
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(ListView listView, View view, int i, long j) {
        File file = new File(this.f4068b.get(i));
        k(view);
        if (!file.isDirectory()) {
            this.f.setEnabled(g() > 0);
            return;
        }
        this.f.setEnabled(false);
        if (file.canRead()) {
            this.o.put(this.k, Integer.valueOf(i));
            h(this.f4068b.get(i));
            if (this.n) {
                view.setSelected(true);
                this.f.setEnabled(true);
                return;
            }
            return;
        }
        new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("[" + file.getName() + "] " + ((Object) getText(R.string.cant_read_folder))).setPositiveButton("OK", new g(this)).show();
    }
}