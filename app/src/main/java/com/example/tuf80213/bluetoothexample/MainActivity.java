package com.example.tuf80213.bluetoothexample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get device's bluetooth adapter (might be null if non present)
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        ListView deviceListView = (ListView) findViewById(R.id.deviceListView);

        // Get previously paired bluetooth devices (might be disabled)
        Set<BluetoothDevice> devices = bta.getBondedDevices();

        if (!devices.isEmpty()){
            DeviceListAdapter dla = new DeviceListAdapter(new ArrayList<BluetoothDevice>(devices), this);
            deviceListView.setAdapter(dla);
        }

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, ((BluetoothDevice) parent.getItemAtPosition(position)).getAddress(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
