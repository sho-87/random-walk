package ca.simonho.randomwalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ImageButton forwardButton;
    ImageButton backButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ImageButton randomButton;

    HashMap<ImageButton, Boolean> buttonStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get references to buttons
        forwardButton = (ImageButton) findViewById(R.id.forwardButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        leftButton = (ImageButton) findViewById(R.id.leftButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        randomButton = (ImageButton) findViewById(R.id.randomButton);

        //Set initial button state
        buttonStates = new HashMap<>();
        buttonStates.put(forwardButton, false);
        buttonStates.put(backButton, false);
        buttonStates.put(leftButton, true);
        buttonStates.put(rightButton, true);

        //Set state based colour of each button
        for (HashMap.Entry<ImageButton, Boolean> entry : buttonStates.entrySet()) {
            setColour(entry.getKey(), entry.getValue());
        }
    }

    public void setColour(ImageButton button, Boolean newState){
        if (newState){
            button.setColorFilter(getResources().getColor(R.color.buttonSelected));
        } else {
            button.setColorFilter(getResources().getColor(R.color.buttonNotSelected));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
