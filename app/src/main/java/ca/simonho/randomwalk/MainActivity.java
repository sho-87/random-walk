package ca.simonho.randomwalk;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton forwardButton;
    ImageButton backButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ImageButton randomButton;
    Boolean newButtonState;
    ArrayList<ImageButton> selectedButtons;

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

        selectedButtons = new ArrayList<>();

        //Set initial button state
        buttonStates = new HashMap<>();
        buttonStates.put(forwardButton, false);
        buttonStates.put(backButton, false);
        buttonStates.put(leftButton, true);
        buttonStates.put(rightButton, true);
        buttonStates.put(randomButton, true);

        //Set initial state based colour and click listener for each button
        for (HashMap.Entry<ImageButton, Boolean> entry : buttonStates.entrySet()) {
            setColour(entry.getKey(), entry.getValue());

            entry.getKey().setOnClickListener(this);
        }
    }

    private void toggleButtonState(ImageButton button){
        newButtonState = !buttonStates.get(button);
        buttonStates.put(button, newButtonState);
        setColour(button, newButtonState);
    }

    private void setColour(ImageButton button, Boolean newState){
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.forwardButton:
                //Toggle the existing button state/colour
                toggleButtonState(forwardButton);
                break;
            case R.id.backButton:
                //Toggle the existing button state/colour
                toggleButtonState(backButton);
                break;
            case R.id.leftButton:
                //Toggle the existing button state/colour
                toggleButtonState(leftButton);
                break;
            case R.id.rightButton:
                //Toggle the existing button state/colour
                toggleButtonState(rightButton);
                break;
            case R.id.randomButton:
                selectedButtons.clear();

                //Get all buttons that have been selected
                for (HashMap.Entry<ImageButton, Boolean> entry : buttonStates.entrySet()) {
                    if(entry.getKey() != randomButton & entry.getValue()){
                        selectedButtons.add(entry.getKey());
                    }
                }

                for(ImageButton button:selectedButtons){
                    Log.d("Selected: ", String.valueOf(getResources().getResourceEntryName(button.getId())));
                }
                Log.d("---","---");
                break;
        }
    }
}
