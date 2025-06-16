package com.psych.mtktool;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.Manifest;

import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.psych.mtktool.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.lamerman.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import android.provider.Settings;


/* loaded from: classes.dex */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean t;
    private ListView u;
    private com.psych.mtktool.mama.a.a.a v;
    private String w;
    private ArrayList<String> x = new ArrayList<>();
    private String pendingFilePath;


    class a extends v {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f1443a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(String str) {
            super(MainActivity.this, null);
            this.f1443a = str;
        }

        @Override // by.lsdsl.gta.imgtool.MainActivity.v
        public void a() throws IOException {
            MainActivity.this.v.c(this.f1443a);
        }
    }

    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                MainActivity.this.d0();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class c extends v {
        c() {
            super(MainActivity.this, null);
        }

        @Override // by.lsdsl.gta.imgtool.MainActivity.v
        public void a() throws IOException {
            int count = MainActivity.this.u.getCount();
            SparseBooleanArray checkedItemPositions = MainActivity.this.u.getCheckedItemPositions();
            for (int i = 0; i < count; i++) {
                if (checkedItemPositions.get(i)) {
                    MainActivity.this.v.e((String) MainActivity.this.u.getAdapter().getItem(i));
                }
            }
        }
    }

    class d extends v {
        d() {
            super(MainActivity.this, null);
        }

        @Override // by.lsdsl.gta.imgtool.MainActivity.v
        public void a() throws IOException {
            int count = MainActivity.this.u.getCount();
            SparseBooleanArray checkedItemPositions = MainActivity.this.u.getCheckedItemPositions();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < count; i++) {
                if (checkedItemPositions.get(i)) {
                    arrayList.add((String) MainActivity.this.u.getAdapter().getItem(i));
                }
            }
            MainActivity.this.v.d((String[]) arrayList.toArray(new String[arrayList.size()]));
        }
    }

    class e implements Runnable {
        e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                MainActivity.this.d0();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class f implements DialogInterface.OnClickListener {

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ EditText f1449b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ String f1450c;

        class a extends v {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ String f1452a;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            a(String str) {
                super(MainActivity.this, null);
                this.f1452a = str;
            }

            @Override // by.lsdsl.gta.imgtool.MainActivity.v
            public void a() throws IOException {
                MainActivity.this.v.q(f.this.f1450c, this.f1452a);
            }
        }

        class b implements Runnable {
            b() {
            }

            @Override // java.lang.Runnable
            public void run() {
                try {
                    MainActivity.this.d0();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        f(EditText editText, String str) {
            this.f1449b = editText;
            this.f1450c = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            String trim = this.f1449b.getText().toString().trim();
            if (trim.length() > 0) {
                MainActivity.this.new u(new b()).execute(new a(trim));
            } else {
                Toast.makeText(MainActivity.this.getApplicationContext(), MainActivity.this.getString(R.string.dialog_empty), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class g implements DialogInterface.OnClickListener {
        g(MainActivity mainActivity) {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    class h implements TextWatcher {
        h() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            MainActivity.this.S(false);
            ArrayAdapter arrayAdapter = (ArrayAdapter) MainActivity.this.u.getAdapter();
            if (arrayAdapter != null) {
                arrayAdapter.getFilter().filter(charSequence);
            }
        }
    }

    class i implements DialogInterface.OnClickListener {
        i() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            MainActivity.this.X();
            dialogInterface.cancel();
        }
    }

    class k implements PermissionListener {
        k() {
        }

        @Override // com.karumi.dexter.listener.single.PermissionListener
        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
            MainActivity.this.b0();
        }

        @Override // com.karumi.dexter.listener.single.PermissionListener
        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
        }

        @Override // com.karumi.dexter.listener.single.PermissionListener
        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
            permissionToken.continuePermissionRequest();
        }
    }

    class l implements DialogInterface.OnClickListener {
        l() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            MainActivity.this.finish();
        }
    }

    class m implements DialogInterface.OnClickListener {
        m() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            com.psych.mtktool.a.a(MainActivity.this);
        }
    }

    class n extends v {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f1461a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        n(String str) {
            super(MainActivity.this, null);
            this.f1461a = str;
        }

        @Override // by.lsdsl.gta.imgtool.MainActivity.v
        public void a() throws IOException {
            MainActivity.this.v.r(MainActivity.this.w, this.f1461a);
        }
    }

    class o implements Runnable {
        o() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                MainActivity.this.d0();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class p extends v {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f1464a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        p(String str) {
            super(MainActivity.this, null);
            this.f1464a = str;
        }

        @Override // by.lsdsl.gta.imgtool.MainActivity.v
        public void a() throws IOException {
            MainActivity.this.v.e(this.f1464a);
        }
    }

    private class r extends AsyncTask<String, Void, Boolean> {
        private r() {
        }

        @Override
        protected Boolean doInBackground(String... strArr) {
            String str = strArr[0];
            try {
                str = URLDecoder.decode(strArr[0], "utf-8");
            } catch (UnsupportedEncodingException e) {
                com.psych.mtktool.b.a(e);
            }

            File file = new File(str);
            byte[] header = new byte[8];
            byte[] key = "q6WcyAP4".getBytes();

            try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                raf.readFully(header);

                if (!com.psych.mtktool.mama.a.a.a.m(str)) {
                    byte[] decryptedHeader = xorDecrypt(header, key);
                    raf.seek(0);
                    raf.write(decryptedHeader);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                if (com.psych.mtktool.mama.a.a.a.m(str)) {
                    MainActivity.this.v = new com.psych.mtktool.mama.a.a.a(str, null);
                    MainActivity.this.v.h();
                    return Boolean.TRUE;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String replace = str.replace(".mtk", ".dir");
            if (new File(replace).exists()) {
                MainActivity.this.v = new com.psych.mtktool.mama.a.a.a(str, replace);
                try {
                    MainActivity.this.v.h();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return Boolean.TRUE;
            }

            return Boolean.FALSE;
        }

        private byte[] xorDecrypt(byte[] data, byte[] key) {
            byte[] result = new byte[data.length];
            for (int i = 0; i < data.length; i++) {
                result[i] = (byte) (data[i] ^ key[i % key.length]);
            }
            return result;
        }

    /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            MainActivity.this.setProgressBarIndeterminateVisibility(false);
            if (!bool.booleanValue()) {
                MainActivity.this.findViewById(R.id.rlopen).setVisibility(View.VISIBLE);
                MainActivity mainActivity = MainActivity.this;
                Toast.makeText(mainActivity, mainActivity.getString(R.string.error_find), Toast.LENGTH_SHORT).show();
            } else {
                MainActivity mainActivity2 = MainActivity.this;
                mainActivity2.setTitle(mainActivity2.getString(R.string.tile, new Object[]{mainActivity2.v.l()}));
                MainActivity.this.findViewById(R.id.lllist).setVisibility(View.VISIBLE);
                try {
                    MainActivity.this.d0();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity.this.setProgressBarIndeterminateVisibility(true);
            MainActivity.this.findViewById(R.id.rlopen).setVisibility(View.GONE);
            MainActivity.this.findViewById(R.id.lllist).setVisibility(View.GONE);
        }

        /* synthetic */ r(MainActivity mainActivity, h hVar) {
            this();
        }
    }

    private class s extends AsyncTask<Void, Integer, Boolean> {

        /* renamed from: a, reason: collision with root package name */
        private ProgressDialog f1470a;

        class a implements com.psych.mtktool.mama.a.a.a.c {
            a() {
            }

            @Override // c.a.a.a.c
            public void a(int i, int i2) {
                s.this.publishProgress(Integer.valueOf(i), Integer.valueOf(i2));
            }
        }

        private s() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public Boolean doInBackground(Void... voidArr) {
            try {
                MainActivity.this.v.p(new a());
                return Boolean.TRUE;
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: c, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Boolean bool) {
            try {
                if (this.f1470a.isShowing() && !MainActivity.this.isFinishing()) {
                    this.f1470a.dismiss();
                }
            } catch (Exception unused) {
            }
            if (bool.booleanValue()) {
                return;
            }
            MainActivity mainActivity = MainActivity.this;
            Toast.makeText(mainActivity, mainActivity.getString(R.string.error_find), Toast.LENGTH_SHORT).show();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: d, reason: merged with bridge method [inline-methods] */
        public void onProgressUpdate(Integer... numArr) {
            this.f1470a.setProgress(numArr[0].intValue());
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            this.f1470a = progressDialog;
            progressDialog.setProgressStyle(1);
            this.f1470a.setCancelable(false);
            this.f1470a.setTitle(R.string.rebuilding);
            this.f1470a.show();
            this.f1470a.setMax(MainActivity.this.v.i());
        }

        /* synthetic */ s(MainActivity mainActivity, h hVar) {
            this();
        }
    }

    private class t extends AsyncTask<String, Void, Exception> {

        /* renamed from: a, reason: collision with root package name */
        private ProgressDialog f1473a;

        private t() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Exception doInBackground(String... strArr) {
            try {
                com.psych.mtktool.mama.a.a.b.a(strArr[0]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Exception exc) {
            this.f1473a.dismiss();
            new AlertDialog.Builder(MainActivity.this).setMessage(exc == null ? "IDX fixed successfully!" : exc.getMessage()).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
            /*if (exc == null && MainActivity.this.y.()) {
                MainActivity.this.y.g();
            }*/
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            this.f1473a = ProgressDialog.show(MainActivity.this, null, "Fixing idx....");
        }

        /* synthetic */ t(MainActivity mainActivity, h hVar) {
            this();
        }
    }

    private class u extends AsyncTask<v, Void, Void> {

        /* renamed from: a, reason: collision with root package name */
        private ProgressDialog f1475a;

        /* renamed from: b, reason: collision with root package name */
        private Exception f1476b;

        /* renamed from: c, reason: collision with root package name */
        private Runnable f1477c;

        public u() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void doInBackground(v... vVarArr) {
            for (v vVar : vVarArr) {
                try {
                    vVar.a();
                } catch (Exception e) {
                    e.printStackTrace();
                    this.f1476b = e;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Void r4) {
            try {
                ProgressDialog progressDialog = this.f1475a;
                if (progressDialog != null && progressDialog.isShowing() && !MainActivity.this.isFinishing()) {
                    this.f1475a.dismiss();
                }
            } catch (Exception unused) {
            }
            if (this.f1476b == null) {
                Runnable runnable = this.f1477c;
                if (runnable != null) {
                    runnable.run();
                    return;
                }
                return;
            }
            Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.error_do) + this.f1476b.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            this.f1475a = progressDialog;
            progressDialog.setCancelable(false);
            this.f1475a.setMessage(MainActivity.this.getString(R.string.dialogProcessing));
            this.f1475a.show();
        }

        public u(Runnable runnable) {
            this.f1477c = runnable;
        }
    }

    private abstract class v {
        private v(MainActivity mainActivity) {
        }

        public abstract void a() throws IOException;

        /* synthetic */ v(MainActivity mainActivity, h hVar) {
            this(mainActivity);
        }
    }

    private void Q(String str, String str2) {
        new q(this, null).execute(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void R() {
        if (this.x.size() > 0) {
            String remove = this.x.remove(0);
            String V = V(remove);
            if (V.length() <= 24) {
                Q(remove, null);
                return;
            }
            Intent intent = new Intent(getBaseContext(), (Class<?>) FixNameActivity.class);
            intent.putExtra("RESULT_PATH", remove);
            intent.putExtra("name_request", V);
            startActivityForResult(intent, 5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void S(boolean z) {
        int count = this.u.getCount();
        for (int i2 = 0; i2 < count; i2++) {
            this.u.setItemChecked(i2, z);
        }
    }

    private void T() {
        com.psych.mtktool.mama.a.a.a aVar = this.v;
        if (aVar != null) {
            int f2 = ((aVar.f() / 1024) / 1024) + 1;
            if (this.v.b()) {
                new s(this, null).execute(new Void[0]);
            } else {
                new AlertDialog.Builder(this).setTitle(R.string.no_space).setMessage(getString(R.string.no_space_text, new Object[]{Integer.valueOf(f2)})).show();
            }
        }
    }

    private Dialog U(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EditText editText = new EditText(this);
        editText.setSingleLine();
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(24)});
        editText.setText(str);
        builder.setTitle(getString(R.string.rename));
        builder.setView(editText);
        builder.setPositiveButton(getString(R.string.dialog_ok), new f(editText, str));
        builder.setNegativeButton(getString(R.string.dialog_cancel), new g(this));
        return builder.create();
    }

    private static String V(String str) {
        return new File(str).getName();
    }

    private void W(String str) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                new r(this, null).execute(str);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                new r(this, null).execute(str);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (pendingFilePath != null) {
                    new r(this, null).execute(pendingFilePath);
                    pendingFilePath = null;
                }
            } else {
                Toast.makeText(this, "нужны права для работы с файлами", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void X() {
        String packageName = getPackageName();
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName)));
        } catch (ActivityNotFoundException unused) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    private void Y() {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://goo.gl/hpyCSX")));
    }

    private void Z() {
        Intent intent = new Intent(getBaseContext(), (Class<?>) FileDialog.class);
        intent.putExtra("START_PATH", Environment.getExternalStorageDirectory().getAbsolutePath());
        intent.putExtra("CAN_SELECT_DIR", false);
        intent.putExtra("FORMAT_FILTER", new String[]{"osw", "OSW"});
        intent.putExtra("SELECTION_MODE", 1);
        startActivityForResult(intent, 7);
    }

    private void a0() {
        if (com.psych.mtktool.a.e(this)) {
            new AlertDialog.Builder(this).setMessage(R.string.android11).setCancelable(true).setNegativeButton(R.string.dontShowMore, new m()).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b0() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.cant_use_app);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new l()).show();
    }

    private void c0(String str) {
        this.w = str;
        Intent intent = new Intent(getBaseContext(), (Class<?>) FileDialog.class);
        intent.putExtra("START_PATH", Environment.getExternalStorageDirectory().getAbsolutePath());
        //intent.putExtra("CAN_SELECT_DIR", false);
        //intent.putExtra("SELECTION_MODE", 1);
        startActivityForResult(intent, 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d0() throws IOException {
        com.psych.mtktool.mama.a.a.a aVar = this.v;
        if (aVar != null) {
            this.u.setAdapter((ListAdapter) new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, aVar.h()));
            this.u.setChoiceMode(2);
        }
    }


    @Override // androidx.fragment.app.d, android.app.Activity
    public synchronized void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        com.psych.mtktool.b.a("onActivityResult=" + i2 + " " + i3);
        if (i3 == -1) {
            ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("RESULT_PATH");
            switch (i2) {
                case 2:
                    W(stringArrayListExtra.get(0));
                    break;
                case 3:
                    this.t = false;
                    if (this.v != null) {
                        this.x.addAll(stringArrayListExtra);
                        R();
                        break;
                    }
                    break;
                case 4:
                    if (this.v != null) {
                        new u(new o()).execute(new n(stringArrayListExtra.get(0)));
                        break;
                    }
                    break;
                case 5:
                    Q(intent.getStringExtra("RESULT_PATH"), intent.getStringExtra("name_result"));
                    break;
                case 6:
                    this.t = true;
                    if (this.v != null) {
                        this.x.addAll(stringArrayListExtra);
                        R();
                        break;
                    }
                    break;
                case 7:
                    new t(this, null).execute(stringArrayListExtra.get(0));
                    break;
            }
        }
    }

    private String getFilePathFromUri(Uri uri) {
        String path = null;

        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            String[] split = docId.split(":");
            String type = split[0];
            String fullPath = split[1];

            if ("primary".equalsIgnoreCase(type)) {
                path = Environment.getExternalStorageDirectory() + "/" + fullPath;
            } else {
                path = "/storage/" + type + "/" + fullPath;
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            path = getRealPathFromURI(uri);
        }

        return path;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        String path = null;

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(columnIndex);
            cursor.close();
        }
        return path;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Intent intent = new Intent(getBaseContext(), (Class<?>) FileDialog.class);
        intent.putExtra("START_PATH", Environment.getExternalStorageDirectory().getAbsolutePath());
        intent.putExtra("CAN_SELECT_DIR", false);
        intent.putExtra("FORMAT_FILTER", new String[]{"mtk", "MTK"});
        intent.putExtra("SELECTION_MODE", 1);
        startActivityForResult(intent, 2);
    }


    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem menuItem) {
        if (this.v != null && this.u.getAdapter() != null && this.u.getAdapter().getCount() > 0) {
            String str = (String) this.u.getAdapter().getItem(((AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo()).position);

            if (menuItem.getItemId() == R.id.delete) {
                new u(new b()).execute(new a(str));
            } else if (menuItem.getItemId() == R.id.delete_multi) {
                new u(new e()).execute(new d());
            } else if (menuItem.getItemId() == R.id.extract) {
                new u().execute(new p(str));
            } else if (menuItem.getItemId() == R.id.extract_multi) {
                new u().execute(new c());
            } else if (menuItem.getItemId() == R.id.rename) {
                U(str).show();
            } else if (menuItem.getItemId() == R.id.replace) {
                c0(str);
            } else {
                return super.onContextItemSelected(menuItem); // Это для обработки других случаев
            }

            return true;
        }
        return super.onContextItemSelected(menuItem);
    }

    @Override // androidx.appcompat.app.c, androidx.fragment.app.d, androidx.activity.ComponentActivity, androidx.core.app.e, android.app.Activity
    public void onCreate(Bundle bundle) {
        com.psych.mtktool.b.a("onCreate=" + bundle);
        super.onCreate(bundle);
        requestWindowFeature(5);
        setContentView(R.layout.main);
        ListView listView = (ListView) findViewById(R.id.list);
        this.u = listView;
        registerForContextMenu(listView);
        ((EditText) findViewById(R.id.search)).addTextChangedListener(new h());
        System.out.println(getIntent().getData());
        if (getIntent().getData() != null) {
            W(getIntent().getData().getEncodedPath());
        }
        int c2 = com.psych.mtktool.a.c(this);
        if (com.psych.mtktool.a.d(this)) {
            String[] stringArray = getResources().getStringArray(R.array.changes);
            int[] intArray = getResources().getIntArray(R.array.changes_version);
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < intArray.length; i2++) {
                if (intArray[i2] > c2) {
                    sb.append(stringArray[i2]);
                    sb.append('\n');
                }
            }
            if (sb.length() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(sb.toString()).setTitle(R.string.lastChangesTitle);
                builder.setPositiveButton("OK", (DialogInterface.OnClickListener) null);
                builder.setNegativeButton(R.string.comment_text, new i());
                builder.create().show();
            }
        }
        Dexter.withContext(this).withPermission("android.permission.WRITE_EXTERNAL_STORAGE").withListener(new k()).check();
    }

    @Override // android.app.Activity, android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        int length = this.u.getCheckItemIds().length;
        if (length <= 0) {
            getMenuInflater().inflate(R.menu.context, contextMenu);
            return;
        }
        getMenuInflater().inflate(R.menu.context_multi, contextMenu);
        contextMenu.findItem(R.id.extract_multi).setTitle(getString(R.string.extract_multi, new Object[]{Integer.valueOf(length)}));
        contextMenu.findItem(R.id.delete_multi).setTitle(getString(R.string.delete_multi, new Object[]{Integer.valueOf(length)}));
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override // androidx.appcompat.app.c, androidx.fragment.app.d, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        com.psych.mtktool.b.a("onDestroy!");
    }

    @Override // androidx.fragment.app.d, android.app.Activity
    protected void onNewIntent(Intent intent) {
        com.psych.mtktool.b.a("onNewIntent=" + intent);
        super.onNewIntent(intent);
        if (intent.getData() != null) {
            W(intent.getData().getEncodedPath());
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (this.v != null) {
            if (menuItem.getItemId() == R.id.about) {
                X();
            } else if (menuItem.getItemId() == R.id.add_file || menuItem.getItemId() == R.id.add_with_replace) {
                Intent intent = new Intent(getBaseContext(), (Class<?>) FileDialog.class);
                intent.putExtra("START_PATH", Environment.getExternalStorageDirectory().getAbsolutePath());
                //intent.putExtra("CAN_SELECT_DIR", false);
                //intent.putExtra("SELECTION_MODE", 2);
                startActivityForResult(intent, menuItem.getItemId() == R.id.add_with_replace ? 6 : 3);
            } else if (menuItem.getItemId() == R.id.open_img) {
                onClick(null);
            } else if (menuItem.getItemId() == R.id.rebuild) {
                T();
            } else if (menuItem.getItemId() == R.id.saaf) {
                Z();
            } else if (menuItem.getItemId() == R.id.selectAll) {
                S(true);
            } else if (menuItem.getItemId() == R.id.txd) {
                Y();
            } else if (menuItem.getItemId() == R.id.unselectAll) {
                S(false);
            }
        } else if (menuItem.getItemId() == R.id.about) {
            X();
        } else if (menuItem.getItemId() == R.id.open_img) {
            onClick(null);
        } else if (menuItem.getItemId() == R.id.txd) {
            Y();
        } else if (menuItem.getItemId() == R.id.saaf) {
            Z();
        } else {
            Toast.makeText(this, getString(R.string.firstOpenImg), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override // androidx.fragment.app.d, android.app.Activity
    protected void onPause() {
        super.onPause();
        com.psych.mtktool.b.a("onPause!");
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        com.psych.mtktool.b.a("onRestoreInstanceState!");
    }

    @Override // androidx.fragment.app.d, android.app.Activity
    protected void onResume() {
        super.onResume();
        com.psych.mtktool.b.a("onResume!");
    }

    @Override // androidx.appcompat.app.c, androidx.fragment.app.d, androidx.activity.ComponentActivity, androidx.core.app.e, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        com.psych.mtktool.b.a("onSaveInstanceState!");
    }

    @Override // androidx.appcompat.app.c, androidx.fragment.app.d, android.app.Activity
    protected void onStart() {
        super.onStart();
        a0();
    }

    @Override // androidx.appcompat.app.c, androidx.fragment.app.d, android.app.Activity
    protected void onStop() {
        super.onStop();
        com.psych.mtktool.b.a("onPause!");
    }

    private class q extends AsyncTask<String, Void, Boolean> {

        /* renamed from: a, reason: collision with root package name */
        private ProgressDialog f1466a;

        /* renamed from: b, reason: collision with root package name */
        private String f1467b;

        private q() {
            this.f1467b = "Unknown error";
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Boolean doInBackground(String... strArr) {
            try {
                MainActivity.this.v.a(strArr[0], strArr[1]);
                return Boolean.TRUE;
            } catch (IllegalArgumentException | IOException e2) {
                if (MainActivity.this.t) {
                    com.psych.mtktool.b.a("add with replace mode");
                    try {
                        MainActivity.this.v.r(e2.getMessage(), strArr[0]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return Boolean.TRUE;
                }
                this.f1467b = MainActivity.this.getString(R.string.error_add_exist) + e2.getMessage();
                return Boolean.FALSE;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void onPostExecute(Boolean bool) {
            try {
                if (this.f1466a.isShowing() && !MainActivity.this.isFinishing()) {
                    this.f1466a.dismiss();
                }
            } catch (Exception unused) {
            }
            if (bool.booleanValue()) {
                try {
                    MainActivity.this.d0();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(MainActivity.this, this.f1467b, Toast.LENGTH_LONG).show();
            }
            MainActivity.this.R();
        }

        @Override // android.os.AsyncTask
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            this.f1466a = progressDialog;
            progressDialog.setMessage(MainActivity.this.getString(R.string.dialogProcessing));
            this.f1466a.setCancelable(false);
            this.f1466a.show();
        }

        /* synthetic */ q(MainActivity mainActivity, h hVar) {
            this();
        }
    }
}
