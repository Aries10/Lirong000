package bwie.com.cartnum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bwie.com.cartnumlibrary.AmountView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AmountView a = (AmountView) findViewById(R.id.AmountView);
        a.setGoods_storage(5);
    }
}
