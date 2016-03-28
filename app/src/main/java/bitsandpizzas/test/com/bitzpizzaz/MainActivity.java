package bitsandpizzas.test.com.bitzpizzaz;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

public class MainActivity extends AppCompatActivity {

    private ShareActionProvider _shareActionProvider;
    private String[] _titles;
    private ListView _drawerList;
    private DrawerLayout _drawerLayout;
    private ActionBarDrawerToggle _drawerToggle;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this._titles = getResources().getStringArray(R.array.titles);
        this._drawerList = (ListView)findViewById(R.id.drawer);
        this._drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        this._drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, this._titles));
        this._drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if(savedInstanceState == null) {
            selectItem(0);
        }

        this._drawerToggle = new ActionBarDrawerToggle(this, this._drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };
        this._drawerLayout.setDrawerListener(this._drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    protected void onPostCreate(Bundle savedInstance) {
        super.onPostCreate(savedInstance);
        this._drawerToggle.syncState();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        final boolean drawerOpen = this._drawerLayout.isDrawerOpen(this._drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private void share (Menu menu) {
        final MenuItem item = menu.findItem(R.id.action_share);
        this._shareActionProvider = (ShareActionProvider) item.getActionProvider();
        setIntent("Sample Text ....");
    }

    private void setIntent (String text) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
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
        if (this._drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

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

    private void selectItem(final int pos) {
        Fragment fragment;
        switch (pos) {
            case 1:
                fragment = new PizzaFragmnet();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoreFragment();
                break;
            default:
                fragment = new TopFragment();
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        setActionBarTile(pos);
        this._drawerLayout.closeDrawer(_drawerList);
    }

    private void setActionBarTile (int pos) {
        String title;
        if(pos == 0) {
            title = getResources().getString(R.string.app_name);
        }
        else {
            title = this._titles[pos];
        }
        getSupportActionBar().setTitle(title);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
