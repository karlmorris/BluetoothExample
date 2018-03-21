package com.example.tuf80213.bluetoothexample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends Activity {

    ListView deviceListView;
    BluetoothAdapter bta;

    private final int REQUEST_BLUETOOTH = 111;


    // Enable bluetooth functionality in app when Bluetooth enabled on device
    BroadcastReceiver bluetoothEnabledReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction()))
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF) == BluetoothAdapter.STATE_ON)
                    setupBluetoothDeviceList();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(bluetoothEnabledReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        // Get device's bluetooth adapter (might be null if none present)
        bta = BluetoothAdapter.getDefaultAdapter();
        deviceListView = (ListView) findViewById(R.id.deviceListView);
        
        if (bta != null) {
            if (bta.isEnabled()) {
                setupBluetoothDeviceList();
            } else {
                // If Bluetooth not enabled, request that the user enable it
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_BLUETOOTH);
            }
        }

    }

    private void setupBluetoothDeviceList() {
        // Get previously paired bluetooth devices (might be disabled)
        if (bta != null) {
            Set<BluetoothDevice> devices = bta.getBondedDevices();

            if (!devices.isEmpty()) {
                DeviceListAdapter dla = new DeviceListAdapter(new ArrayList<BluetoothDevice>(devices), this);
                deviceListView.setAdapter(dla);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // If Bluetooth Enable request accepted
        if (requestCode == REQUEST_BLUETOOTH && resultCode == RESULT_OK) {
            setupBluetoothDeviceList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothEnabledReceiver);
    }
}
