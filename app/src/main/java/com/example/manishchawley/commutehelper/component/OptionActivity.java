package com.example.manishchawley.commutehelper.component;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.manishchawley.commutehelper.MainActivity;
import com.example.manishchawley.commutehelper.R;
import com.example.manishchawley.commutehelper.model.User;
import com.example.manishchawley.commutehelper.render.TripListActivity;

/**
 * Created by manishchawley on 15/07/17.
 */

public class OptionActivity extends AppCompatActivity {

    private final String TAG = OptionActivity.class.getName();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: 15/07/17 Create menu option
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_option_menu, menu);

        final MenuItem userSettings = menu.findItem(R.id.main_option_menu_user_settings);
        userSettings.setTitle(User.getUser().getName());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: 15/07/17 Work based on item selected
        Intent intent;
        switch (item.getItemId()){
            case R.id.main_option_menu_trips:
                intent = new Intent(this, TripListActivity.class);
                startActivity(intent);
                return true;
            case R.id.main_option_menu_logout:
                User.doLogout();
                Log.e(TAG, "Facebook Login Status: " + String.valueOf(User.getUser().isLogin()));
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
