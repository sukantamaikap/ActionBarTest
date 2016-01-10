package bitsandpizzas.test.com.bitzpizzaz;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class MainActivity extends AppCompatActivity {

    private ShareActionProvider _shareActionProvider;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void share (Menu menu) {
        MenuItem item = menu.findItem(R.id.action_share);
        this._shareActionProvider = (ShareActionProvider) item.getActionProvider();
        setIntent("Sample Text ....");
    }

    private void setIntent (String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        this._shareActionProvider.setShareIntent(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //share(menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent orderIntent = new Intent(this, OrderActivity.class);
                startActivity(orderIntent);
                return true;

            case R.id.action_setting:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
