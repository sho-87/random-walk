package ca.simonho.randomwalk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton forwardButton;
    ImageButton backButton;
    ImageButton leftButton;
    ImageButton rightButton;
    ImageButton randomButton;
    TextView directionText;

    HashMap<ImageButton, Boolean> buttonStates;
    Boolean newButtonState;
    ArrayList<ImageButton> selectedButtons;
    ImageButton randomlyChosenButton;

    Integer directionShowDuration = 5000;
    Handler durationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get references
        forwardButton = (ImageButton) findViewById(R.id.forwardButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        leftButton = (ImageButton) findViewById(R.id.leftButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        randomButton = (ImageButton) findViewById(R.id.randomButton);
        directionText = (TextView) findViewById(R.id.directionText);

        //Initialize list of user selected buttons
        selectedButtons = new ArrayList<>();

        //Initialize the handler for chosen direction display duration
        durationHandler = new Handler();

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
        //Toggle the state of a button
        newButtonState = !buttonStates.get(button);
        buttonStates.put(button, newButtonState);
        setColour(button, newButtonState);
    }

    private void setColour(ImageButton button, Boolean newState){
        //Set colour of the buttons based on their state
        if (newState){
            button.setColorFilter(getResources().getColor(R.color.buttonSelected));
        } else {
            button.setColorFilter(getResources().getColor(R.color.buttonNotSelected));
        }
    }

    private void showRandomDirection(final ImageButton button){
        //Disable the random button for a while
        randomButton.setEnabled(false);
        setColour(randomButton, false);

        //Announce the randomly chosen direction
        directionText.setText(getString(R.string.directionText, button.getContentDescription()));

        //Highlight the randomly chosen direction button
        button.setColorFilter(getResources().getColor(R.color.buttonChosen));

        //Set button colour back to state based colour after a delay
        durationHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setColour(button, buttonStates.get(button));

                //Reset the direction announce text
                directionText.setText("");

                //Re-enable the random button
                randomButton.setEnabled(true);
                setColour(randomButton, true);
            }
        }, directionShowDuration);
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
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sho-87/random-walk"));
            startActivity(browserIntent);
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

                for (HashMap.Entry<ImageButton, Boolean> entry : buttonStates.entrySet()) {
                    //Reset button colour states each click
                    setColour(entry.getKey(), entry.getValue());

                    //Get all buttons that have been selected
                    if(entry.getKey() != randomButton & entry.getValue()){
                        selectedButtons.add(entry.getKey());
                    }
                }

                //Get randomly picked direction
                if (selectedButtons.size() > 0){
                    randomlyChosenButton = selectedButtons.get((new Random()).nextInt(selectedButtons.size()));

                    //Announce the randomly picked direction
                    showRandomDirection(randomlyChosenButton);
                }
                break;
        }
    }
}
